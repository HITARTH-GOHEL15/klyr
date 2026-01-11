import google.generativeai as genai
import os
from dotenv import load_dotenv

load_dotenv()

# Configure Gemini with your Google API key
genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))

# List all models
models = genai.list_models()

for m in models:
    print(m.name, "→", m.supported_generation_methods)
