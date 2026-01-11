# 🚀 KLYR — AI Resume Intelligence Platform

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Python](https://img.shields.io/badge/python-3.10+-blue.svg)
![Kotlin](https://img.shields.io/badge/kotlin-1.9+-purple.svg)
![FastAPI](https://img.shields.io/badge/FastAPI-0.100+-green.svg)

KLYR is a full-stack AI-powered resume intelligence platform that helps job seekers optimize their resumes, identify skill gaps, and match their profiles with job descriptions using advanced AI technology.

## 🌟 Overview

**KLYR** combines the power of Google's Gemini AI with modern mobile and backend technologies to provide:

- 🧠 **Gemini-AI powered FastAPI backend**
- 📱 **Modern Android app** (Jetpack Compose + Ktor + Koin)

### What KLYR Does

- ✅ Analyze resumes like an Applicant Tracking System (ATS)
- 🎯 Detect skill gaps for target roles
- 🔍 Match resumes with job descriptions
- ✍️ Rewrite resume bullets with ATS optimization
- 📝 Generate professional resume sections

---

## 📁 Repository Structure

```
klyr/
│
├── backend/               # FastAPI + Gemini AI backend
│   ├── main.py
│   ├── list_models.py
│   ├── requirements.txt
│   └── .env              # Environment variables (gitignored)
│
├── android/              # Android app (Jetpack Compose)
│   └── klyr/
│       ├── app/
│       ├── build.gradle.kts
│       └── settings.gradle.kts
│
└── README.md
```

---

## 🧠 Features (Tier-1 Complete)

### ✅ Resume Analysis (ATS Grade)
- **ATS score** (0–100 rating)
- **Strengths & weaknesses** identification
- **Missing sections** detection
- **Skill extraction** from resume content
- **Improvement suggestions** for better ATS performance

### ✅ Skill Gap Analysis
- **Match percentage** with target role
- **Matched vs missing skills** breakdown
- **Role readiness** assessment
- **Learning roadmap** generation
- **Estimated time** to become job-ready

### ✅ Job Description Match
- **Resume ↔ JD match score**
- **Missing ATS keywords** identification
- **ATS risk factors** analysis
- **Resume optimization tips**

### ✅ Resume Bullet Rewriter
- **ATS-optimized** bullet point rewriting
- **Role & experience aware** suggestions
- **Explanation** of why rewritten bullets are better

### ✅ Resume Section Generator
Generate professional content for:
- Summary
- Experience
- Skills
- Projects
- Education

All tailored to your target role, experience level, and skills.

---

## 🧰 Tech Stack

### 🔹 Backend
- **Python 3.10+**
- **FastAPI** - Modern web framework
- **Google Gemini AI** - AI-powered analysis
- **PyPDF2** - PDF processing
- **Uvicorn** - ASGI server
- **python-dotenv** - Environment management

### 🔹 Android
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Ktor Client** - HTTP networking
- **Koin** - Dependency injection
- **Kotlinx Serialization** - JSON parsing
- **MVVM Architecture** - Clean code structure

### 🔹 Infrastructure / DevOps
- **Ngrok** - Local tunneling for development
- **Git + GitHub** - Version control

---

## ⚙️ Backend Setup (Step-by-Step)

### 1️⃣ Clone Repository

```bash
git clone https://github.com/HITARTH-GOHEL15/klyr.git
cd klyr/backend
```

### 2️⃣ Create Virtual Environment

**Windows:**
```bash
python -m venv venv
venv\Scripts\activate
```

**macOS / Linux:**
```bash
python3 -m venv venv
source venv/bin/activate
```

### 3️⃣ Install Dependencies

```bash
pip install -r requirements.txt
```

### 4️⃣ Create `.env` File

Create `backend/.env` with your API key:

```env
GOOGLE_API_KEY=your_gemini_api_key_here
```

> ⚠️ **Important:** Never commit `.env` to GitHub. It's already in `.gitignore`.

### 5️⃣ Run Backend Server

```bash
uvicorn main:app --reload
```

Server will run at:
- **API:** http://127.0.0.1:8000
- **Swagger Docs:** http://127.0.0.1:8000/docs

---

## 🌍 Exposing Backend with Ngrok

For Android app development, expose your local backend:

```bash
ngrok http 8000
```

Copy the HTTPS URL (e.g., `https://xxxx.ngrok-free.dev`) and update in your Android app:

```kotlin
const val BASE_URL = "https://xxxx.ngrok-free.dev"
```

---

## 📡 Backend API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/analyze-resume` | POST | Analyze resume text |
| `/analyze-resume-pdf` | POST | Analyze resume PDF file |
| `/skill-gap` | POST | Perform skill gap analysis |
| `/jd-match` | POST | Match resume with job description |
| `/rewrite-bullet` | POST | Rewrite a resume bullet point |
| `/generate-section` | POST | Generate resume section content |

### 🔹 Example: Bullet Rewrite Request

```http
POST /rewrite-bullet
Content-Type: application/json

{
  "bullet_point": "Worked on backend APIs",
  "target_role": "Senior Backend Engineer",
  "experience_level": "Senior"
}
```

---

## 📱 Android App Setup

### 1️⃣ Open Project

Open the following folder in Android Studio:

```
android/klyr
```

### 2️⃣ Add Firebase Config (Local Only)

Place your `google-services.json` file here:

```
android/klyr/app/google-services.json
```

> ⚠️ **Note:** This file is gitignored for security. You need to add your own.

### 3️⃣ Update Backend URL

In your network configuration file, update:

```kotlin
const val BASE_URL = "https://your-ngrok-url.ngrok-free.dev"
```

### 4️⃣ Run App

1. Select your device or emulator
2. Click **▶️ Run**

---

## 🧩 Architecture (Android)

The Android app follows clean architecture principles:

- **MVVM** (Model-View-ViewModel) pattern
- **Repository pattern** for data management
- **Koin** for dependency injection
- **StateFlow** for reactive UI state
- **Clean separation** of concerns:
  - UI Layer (Composables)
  - ViewModel Layer (Business logic)
  - Data Layer (Repository)
  - Network Layer (API client)

---

## 🤝 Contributing Guide

We welcome contributions! Here's how you can help:

### Steps to Contribute

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/my-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m "Add amazing feature"
   ```
4. **Push to your fork**
   ```bash
   git push origin feature/my-feature
   ```
5. **Open a Pull Request**

### Guidelines

- ✅ Keep code clean and readable
- ✅ Follow existing architecture patterns
- ✅ Do **not** commit secrets or API keys
- ✅ Test your changes before submitting PR
- ✅ Write meaningful commit messages
- ✅ Update documentation if needed

---

## 🔐 Security Notes

- `.env` file is gitignored - never commit API keys
- `google-services.json` is gitignored - handle with care
- **Rotate keys immediately** if accidentally committed
- Never expose API keys in Android source code
- Use environment variables for sensitive data

---

## 🧪 Testing

### Backend Testing
- Use **Swagger UI** at `/docs` endpoint
- Test endpoints with sample data
- Check logs for debugging

### Android Testing
- Test on **emulator** or **real device**
- Monitor network calls via **Logcat**
- Use **Ktor logging** for request/response inspection

---

## 📌 Roadmap (Next Features)

- [ ] **User resume storage** with cloud sync
- [ ] **AI interview preparation** module
- [ ] **Career roadmap generator**
- [ ] **Multi-language support**
- [ ] **Web dashboard** for desktop users
- [ ] **Resume templates** library
- [ ] **LinkedIn integration**
- [ ] **Job application tracker**

---

## 👨‍💻 Author

**Hitarth Gohel**

- GitHub: [@HITARTH-GOHEL15](https://github.com/HITARTH-GOHEL15)

---

## ⭐ Support This Project

If you find KLYR helpful:

- ⭐ **Star the repository**
- 🍴 **Fork it** for your own projects
- 🧠 **Learn from it** and build something amazing
- 🤝 **Contribute** to make it better
- 📢 **Share it** with others who might benefit

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **Google Gemini AI** for powerful AI capabilities
- **FastAPI** community for excellent documentation
- **Jetpack Compose** team for modern Android UI
- All contributors and supporters

---

<div align="center">

**Built with ❤️ by developers, for developers**

[Report Bug](https://github.com/HITARTH-GOHEL15/klyr/issues) · [Request Feature](https://github.com/HITARTH-GOHEL15/klyr/issues)

</div>
