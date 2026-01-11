🚀 KLYR — AI Resume Intelligence Platform

KLYR is a full-stack AI-powered resume intelligence platform consisting of:

🧠 Gemini-AI powered FastAPI backend

📱 Modern Android app (Jetpack Compose + Ktor + Koin)

It helps users:

Analyze resumes like an ATS

Detect skill gaps

Match resumes with job descriptions

Rewrite resume bullets

Generate professional resume sections

📁 Repository Structure
klyr/
│
├── backend/               # FastAPI + Gemini AI backend
│   ├── main.py
│   ├── list_models.py
│   ├── requirements.txt
│   └── .env (ignored)
│
├── android/               # Android app (Jetpack Compose)
│   └── klyr/
│       ├── app/
│       ├── build.gradle.kts
│       └── settings.gradle.kts
│
└── README.md

🧠 FEATURES (TIER-1 COMPLETE)
✅ Resume Analysis (ATS Grade)

ATS score (0–100)

Strengths & weaknesses

Missing sections

Skill extraction

Improvement suggestions

✅ Skill Gap Analysis

Match percentage

Matched vs missing skills

Role readiness

Learning roadmap

Estimated time to be job-ready

✅ Job Description Match

Resume ↔ JD match score

Missing ATS keywords

ATS risk factors

Resume optimization tips

✅ Resume Bullet Rewriter

ATS-optimized bullet rewriting

Role & experience aware

Explains why rewritten bullet is better

✅ Resume Section Generator

Generate:

Summary

Experience

Skills

Projects

Education

Tailored to role + experience + skills

🧰 TECH STACK
🔹 Backend

Python 3.10+

FastAPI

Google Gemini AI

PyPDF2

Uvicorn

python-dotenv

🔹 Android

Kotlin

Jetpack Compose

Ktor Client

Koin (DI)

Kotlinx Serialization

Firebase

MVVM Architecture

🔹 Infra / DevOps

Ngrok (local tunneling)

Git + GitHub

⚙️ BACKEND SETUP (STEP-BY-STEP)
1️⃣ Clone Repository
git clone https://github.com/HITARTH-GOHEL15/klyr.git
cd klyr/backend

2️⃣ Create Virtual Environment
Windows
python -m venv venv
venv\Scripts\activate

macOS / Linux
python3 -m venv venv
source venv/bin/activate

3️⃣ Install Dependencies
pip install -r requirements.txt

4️⃣ Create .env File

Create backend/.env:

GOOGLE_API_KEY=your_gemini_api_key_here


⚠️ Never commit .env to GitHub

5️⃣ Run Backend Server
uvicorn main:app --reload


Server will run at:

http://127.0.0.1:8000


Swagger Docs:

http://127.0.0.1:8000/docs

🌍 EXPOSING BACKEND WITH NGROK
ngrok http 8000


Copy the HTTPS URL and use it in Android:

const val BASE_URL = "https://xxxx.ngrok-free.dev"

📡 BACKEND API ENDPOINTS
Endpoint	Method	Description
/analyze-resume	POST	Analyze resume text
/analyze-resume-pdf	POST	Analyze resume PDF
/skill-gap	POST	Skill gap analysis
/jd-match	POST	Resume vs JD match
/rewrite-bullet	POST	Rewrite resume bullet
/generate-section	POST	Generate resume section
🔹 Example: Bullet Rewrite Request
POST /rewrite-bullet
{
  "bullet_point": "Worked on backend APIs",
  "target_role": "Senior Backend Engineer",
  "experience_level": "Senior"
}

📱 ANDROID APP SETUP
1️⃣ Open Project

Open folder in Android Studio:

android/klyr

2️⃣ Add Firebase Config (Local Only)

Place google-services.json here:

android/klyr/app/google-services.json


⚠️ File is ignored in Git for security.

3️⃣ Update Backend URL
const val BASE_URL = "https://your-ngrok-url.ngrok-free.dev"

4️⃣ Run App

Select device/emulator

Click ▶️ Run

🧩 ARCHITECTURE (ANDROID)

MVVM

Repository pattern

Koin dependency injection

StateFlow for UI state

Clean separation:

UI

ViewModel

Data

Network

🤝 CONTRIBUTING GUIDE

We welcome contributions!

Steps:

Fork the repo

Create a feature branch

git checkout -b feature/my-feature


Commit changes

Push to your fork

Open a Pull Request

Guidelines:

Keep code clean & readable

Follow existing architecture

Do not commit secrets

Test before PR

🔐 SECURITY NOTES

.env is ignored

google-services.json is ignored

Rotate keys if accidentally committed

Never expose API keys in Android code

🧪 TESTING

Backend: Swagger /docs

Android: Emulator / real device

Network: Logcat + Ktor logs


👨‍💻 AUTHOR

Hitarth Gohel
GitHub: https://github.com/HITARTH-GOHEL15

⭐ SUPPORT

If you like this project:

⭐ Star the repo

🍴 Fork it

🧠 Learn from it

🤝 Contribute
