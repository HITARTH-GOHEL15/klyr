from fastapi import FastAPI, File, UploadFile
from pydantic import BaseModel
from PyPDF2 import PdfReader
import google.generativeai as genai
import io
import os
import json
import re
from dotenv import load_dotenv

# -------------------- SETUP --------------------

load_dotenv()

app = FastAPI(title="klyr AI Resume Analyzer (Gemini AI)")

genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))

model = genai.GenerativeModel(
    model_name="models/gemini-2.5-flash",
    generation_config={
        "temperature": 0.15,
        "max_output_tokens": 1800
    }
)

# -------------------- MODELS --------------------


class ResumeRequest(BaseModel):
    resume_text: str


class SkillGapRequest(BaseModel):
    resume_skills: list[str]
    target_role: str


class JdMatchRequest(BaseModel):
    resume_text: str
    job_description: str


class RewriteBulletRequest(BaseModel):
    bullet_point: str
    target_role: str
    experience_level: str


class GenerateSectionRequest(BaseModel):
    section_type: str
    role: str
    experience_level: str
    skills: list[str]


# -------------------- UTILITIES --------------------


def extract_text_from_pdf(file_bytes: bytes) -> str:
    reader = PdfReader(io.BytesIO(file_bytes))
    text = ""
    for page in reader.pages:
        page_text = page.extract_text()
        if page_text:
            text += page_text + "\n"
    return text.strip()


def clean_text(text: str, max_chars: int = 7000) -> str:
    text = re.sub(r"\s+", " ", text)
    return text[:max_chars]


def parse_json_strict(text: str) -> dict | None:
    text = text.strip()

    if text.startswith("```"):
        text = text.replace("```json", "").replace("```", "").strip()

    try:
        return json.loads(text)
    except json.JSONDecodeError:
        pass

    start, end = text.find("{"), text.rfind("}")
    if start != -1 and end != -1:
        try:
            return json.loads(text[start:end + 1])
        except json.JSONDecodeError:
            return None

    return None


def ask_gemini_json(prompt: str) -> dict:
    response = model.generate_content(
        prompt,
        generation_config={
            "temperature": 0.1,
            "max_output_tokens": 1200,
            "top_p": 0.9
        }
    )

    if not response.candidates:
        raise RuntimeError("Gemini returned no candidates")

    text = "".join(
        p.text for p in response.candidates[0].content.parts
        if hasattr(p, "text")
    )

    parsed = parse_json_strict(text)
    if parsed:
        return parsed

    # 🔁 STRONG AI SELF-REPAIR
    repair_prompt = f"""
You must fix the output below so it becomes VALID JSON
that matches this schema EXACTLY:

{{
  "match_percentage": 0,
  "matched_skills": ["string"],
  "missing_skills": ["string"],
  "skill_gap_summary": "string",
  "learning_recommendations": [
    {{
      "skill": "string",
      "why_needed": "string",
      "how_to_learn": "string"
    }}
  ]
}}

BROKEN OUTPUT:
{text}

Return ONLY the corrected JSON.
"""

    retry = model.generate_content(
        repair_prompt,
        generation_config={"temperature": 0}
    )

    if retry.candidates:
        retry_text = "".join(
            p.text for p in retry.candidates[0].content.parts
            if hasattr(p, "text")
        )
        parsed_retry = parse_json_strict(retry_text)
        if parsed_retry:
            return parsed_retry

    raise RuntimeError("Gemini returned invalid JSON")


def ask_gemini_bullet(prompt: str) -> dict:
    response = model.generate_content(prompt)

    text = "".join(
        p.text for p in response.candidates[0].content.parts
        if hasattr(p, "text")
    )

    parsed = parse_json_strict(text)
    if parsed:
        return parsed

    repair_prompt = f"""
Fix the output below to valid JSON EXACTLY in this format:

{{
  "rewritten_bullet": "string",
  "why_this_is_better": "string"
}}

BROKEN OUTPUT:
{text}

Return ONLY JSON.
"""

    retry = model.generate_content(repair_prompt)
    retry_text = "".join(
        p.text for p in retry.candidates[0].content.parts
        if hasattr(p, "text")
    )

    parsed_retry = parse_json_strict(retry_text)
    if parsed_retry:
        return parsed_retry

    raise RuntimeError("Bullet rewrite JSON invalid")


# -------------------- GEMINI CORE --------------------
SKILL_GAP_PROMPT = """
You are a senior technical recruiter and hiring manager.

TASK:
Evaluate how well the candidate fits the target role based on skills.

RULES (MANDATORY):
- Output ONLY valid JSON
- No markdown
- No explanations
- No comments
- Be realistic and recruiter-grade

RETURN JSON IN THIS EXACT STRUCTURE:

{{
  "match_percentage": 0,
  "role_readiness": {{
    "status": "NOT_READY | PARTIALLY_READY | READY",
    "summary": "string"
  }},
  "matched_skills": ["string"],
  "missing_skills": ["string"],
  "skill_gap_summary": "string",
  "estimated_time_to_ready": {{
    "months": 0,
    "assumptions": "string"
  }},
  "learning_recommendations": [
    {{
      "skill": "string",
      "priority": "HIGH | MEDIUM | LOW",
      "why_needed": "string",
      "how_to_learn": "string"
    }}
  ]
}}

Candidate Skills:
{resume_skills}

Target Role:
{target_role}
"""

# -------------------- EXTRA PROMPTS (TIER 1) --------------------

JD_MATCH_PROMPT = """
You are an ATS engine.

Return ONLY valid JSON.

{{
  "match_percentage": 0,
  "matched_keywords": ["string"],
  "missing_keywords": ["string"],
  "ats_risks": ["string"],
  "resume_improvement_tips": ["string"]
}}

Resume:
{resume_text}

Job Description:
{job_description}
"""


BULLET_REWRITE_PROMPT = """
Rewrite the resume bullet to be ATS optimized.

Return ONLY valid JSON.

{{
  "rewritten_bullet": "string",
  "why_this_is_better": "string"
}}

Original Bullet:
{bullet}

Target Role:
{role}

Experience Level:
{level}
"""


SECTION_GENERATOR_PROMPT = """
Generate a resume section.

Return ONLY valid JSON.

{{
  "generated_section": "string"
}}

Section Type:
{section}

Target Role:
{role}

Experience Level:
{level}

Skills:
{skills}
"""


def gemini_analyze_resume(resume_text: str) -> dict:
    prompt = f"""
You are an advanced ATS engine combined with a senior technical recruiter.

Analyze the resume EXACTLY as a real ATS + recruiter would.

RULES (MANDATORY):
- Output ONLY valid JSON
- No markdown
- No explanations
- No commentary
- Be critical and realistic
- Do NOT give perfect scores unless clearly exceptional

Return JSON in this EXACT structure:

{{
  "ats_score": 0-100,
  "strengths": [
    "Evidence-based strengths from the resume"
  ],
  "weaknesses": [
    "Clear, specific weaknesses"
  ],
  "skills": {{
    "technical": ["Explicit technical skills found"],
    "soft": ["Soft skills inferred from experience"]
  }},
  "missing_sections": [
    "Sections that are missing or weak"
  ],
  "improvement_suggestions": [
    "Concrete, actionable resume improvements"
  ]
}}

Evaluation criteria:
- Bullet quality and clarity
- Action verbs
- Quantified impact
- ATS keyword depth
- Resume structure and readability
- Seniority signals
- Skill relevance

Resume text:
{resume_text}
"""

    response = model.generate_content(prompt)

    if not response.candidates:
        raise RuntimeError("Gemini returned no candidates")

    parts = response.candidates[0].content.parts
    text = "".join(p.text for p in parts if hasattr(p, "text"))

    parsed = parse_json_strict(text)
    if parsed:
        return parsed

    # 🔁 ONE AI RETRY TO FIX JSON (still AI-based)
    repair_prompt = f"""
The following output should be valid JSON but has formatting issues.
Return ONLY the corrected JSON object.

{text}
"""

    retry = model.generate_content(repair_prompt)

    if retry.candidates:
        retry_text = "".join(
            p.text for p in retry.candidates[0].content.parts if hasattr(p, "text")
        )
        parsed_retry = parse_json_strict(retry_text)
        if parsed_retry:
            return parsed_retry

    raise RuntimeError("Gemini returned invalid JSON")

# -------------------- API --------------------


@app.get("/")
def health():
    return {"status": "Gemini AI resume analyzer running"}


@app.post("/analyze-resume")
def analyze_text_resume(data: ResumeRequest):
    if not data.resume_text or len(data.resume_text.strip()) < 50:
        return {"error": "Resume text too short"}

    try:
        cleaned = clean_text(data.resume_text)
        result = gemini_analyze_resume(cleaned)
        result["analysis_method"] = "gemini_ai"
        return result
    except Exception as e:
        return {
            "error": "AI analysis failed",
            "details": str(e),
            "analysis_method": "gemini_ai"
        }


@app.post("/analyze-resume-pdf")
async def analyze_pdf_resume(file: UploadFile = File(...)):
    if not file.filename.lower().endswith(".pdf"):
        return {"error": "Only PDF files supported"}

    pdf_bytes = await file.read()
    text = extract_text_from_pdf(pdf_bytes)

    if not text or len(text.strip()) < 50:
        return {"error": "Unable to extract text from PDF"}

    try:
        cleaned = clean_text(text)
        result = gemini_analyze_resume(cleaned)
        result["analysis_method"] = "gemini_ai"
        return result
    except Exception as e:
        return {
            "error": "AI analysis failed",
            "details": str(e),
            "analysis_method": "gemini_ai"
        }


@app.post("/skill-gap")
def skill_gap_analysis(data: SkillGapRequest):

    if not data.resume_skills or not data.target_role:
        return {"error": "resume_skills and target_role are required"}

    resume_skills = ", ".join(data.resume_skills)

    prompt = SKILL_GAP_PROMPT.format(
        resume_skills=resume_skills,
        target_role=data.target_role
    )

    try:
        result = ask_gemini_json(prompt)
        result["analysis_method"] = "gemini_ai"
        return result
    except Exception as e:
        return {
            "error": "Skill gap analysis failed",
            "details": str(e),
            "analysis_method": "gemini_ai"
        }


# -------------------- EXTRA API ENDPOINTS (TIER 1) --------------------

@app.post("/jd-match")
def jd_match(data: JdMatchRequest):
    prompt = JD_MATCH_PROMPT.format(
        resume_text=clean_text(data.resume_text),
        job_description=clean_text(data.job_description)
    )

    result = ask_gemini_json(prompt)
    result["analysis_method"] = "gemini_ai"
    return result


@app.post("/rewrite-bullet")
def rewrite_bullet(data: RewriteBulletRequest):
    prompt = BULLET_REWRITE_PROMPT.format(
        bullet=data.bullet_point,
        role=data.target_role,
        level=data.experience_level
    )

    result = ask_gemini_bullet(prompt)
    result["analysis_method"] = "gemini_ai"
    return result


@app.post("/generate-section")
def generate_section(data: GenerateSectionRequest):
    prompt = SECTION_GENERATOR_PROMPT.format(
        section=data.section_type,
        role=data.role,
        level=data.experience_level,
        skills=", ".join(data.skills)
    )

    result = ask_gemini_json(prompt)
    result["analysis_method"] = "gemini_ai"
    return result
