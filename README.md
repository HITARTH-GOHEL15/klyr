# 🚀 KLYR — AI Resume Intelligence Platform

KLYR is a **full-stack AI-powered resume intelligence platform** built with:

- 🧠 **FastAPI + Google Gemini AI (Backend)**
- 📱 **Android (Jetpack Compose + Ktor + Koin)**

It helps users analyze, optimize, and tailor resumes to specific job roles using real ATS-style AI analysis.

---

## 📁 Repository Structure

klyr/
│
├── backend/ # FastAPI + Gemini AI backend
│ ├── main.py
│ ├── list_models.py
│ ├── requirements.txt
│ └── .env (ignored)
│
├── android/ # Android app (Jetpack Compose)
│ └── klyr/
│ ├── app/
│ ├── build.gradle.kts
│ └── settings.gradle.kts
│
└── README.md

markdown
Copy code

---

## ✨ Features (Tier-1 Complete)

### 🧠 Resume ATS Analysis
- ATS score (0–100)
- Strengths & weaknesses
- Technical & soft skills extraction
- Missing resume sections
- Actionable improvement suggestions

### 📊 Skill Gap Analysis
- Match percentage
- Matched vs missing skills
- Role readiness status
- Learning recommendations
- Estimated time to become job-ready

### 📄 Resume ↔ Job Description Match
- JD match percentage
- Matched keywords
- Missing ATS keywords
- ATS risk flags
- Resume optimization tips

### ✍️ Resume Bullet Rewriter
- ATS-optimized bullet rewriting
- Role & experience aware
- Explains why the rewrite is better

### 🧩 Resume Section Generator
- Generates:
  - Summary
  - Experience
  - Skills
  - Projects
  - Education
  - Certifications
- Tailored to role, experience & skills

---

## 🧰 Tech Stack

### Backend
- Python 3.10+
- FastAPI
- Google Gemini AI
- PyPDF2
- Uvicorn
- python-dotenv

### Android
- Kotlin
- Jetpack Compose
- Ktor Client
- Koin (Dependency Injection)
- Kotlinx Serialization
- MVVM Architecture

### DevOps / Tools
- Git & GitHub
- Ngrok (local backend tunneling)

---

# ⚙️ Backend Setup (Step-by-Step)

## 1️⃣ Clone Repository

```bash
git clone https://github.com/HITARTH-GOHEL15/klyr.git
cd klyr/backend
2️⃣ Create Virtual Environment
Windows
bash
Copy code
python -m venv venv
venv\Scripts\activate
macOS / Linux
bash
Copy code
python3 -m venv venv
source venv/bin/activate
3️⃣ Install Dependencies
bash
Copy code
pip install -r requirements.txt
4️⃣ Create .env File
Create backend/.env:

env
Copy code
GOOGLE_API_KEY=your_gemini_api_key_here
⚠️ Never commit .env to GitHub.

5️⃣ Run Backend Server
bash
Copy code
uvicorn main:app --reload
Backend will be live at:

cpp
Copy code
http://127.0.0.1:8000
Swagger API Docs:

arduino
Copy code
http://127.0.0.1:8000/docs
🌍 Expose Backend with Ngrok
bash
Copy code
ngrok http 8000
Copy the HTTPS URL and use it in the Android app.

📡 API Endpoints
Endpoint	Method	Description
/analyze-resume	POST	Analyze resume text
/analyze-resume-pdf	POST	Analyze resume PDF
/skill-gap	POST	Skill gap analysis
/jd-match	POST	Resume vs JD match
/rewrite-bullet	POST	Rewrite resume bullet
/generate-section	POST	Generate resume section

Example: Rewrite Bullet API
json
Copy code
POST /rewrite-bullet
{
  "bullet_point": "Worked on backend APIs",
  "target_role": "Senior Backend Engineer",
  "experience_level": "Senior"
}
📱 Android App Setup
1️⃣ Open Project
Open this folder in Android Studio:

bash
Copy code
android/klyr
2️⃣ Firebase Config (Local Only)
Add your Firebase config file:

bash
Copy code
android/klyr/app/google-services.json
⚠️ This file is intentionally ignored from GitHub.

3️⃣ Update Backend URL
In API config:

kotlin
Copy code
const val BASE_URL = "https://your-ngrok-url.ngrok-free.dev"
4️⃣ Run the App
Select emulator or physical device

Click ▶️ Run

🧩 Android Architecture
MVVM

Repository pattern

Koin for dependency injection

StateFlow for UI state

Clean separation:

UI

ViewModel

Data

Network

🤝 Contributing
We welcome contributions!

Steps
Fork the repository

Create a feature branch

bash
Copy code
git checkout -b feature/my-feature
Commit changes

Push to your fork

Open a Pull Request

Guidelines
Follow existing architecture

Write clean, readable code

Do not commit secrets

Test before submitting PRs

🔐 Security Notes
.env is ignored

google-services.json is ignored

Never expose API keys in Android code

Rotate keys if leaked accidentally

🛣️ Roadmap
Resume version history

AI interview preparation

Career roadmap generator

Web dashboard

Multi-language support

👨‍💻 Author
Hitarth Gohel
GitHub: https://github.com/HITARTH-GOHEL15

⭐ Support
If you like this project:

⭐ Star the repo

🍴 Fork it

🧠 Learn from it

🤝 Contribute
