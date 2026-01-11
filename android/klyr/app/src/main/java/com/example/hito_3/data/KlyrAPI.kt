package com.example.hito_3.data

import com.example.hito_3.data.AnalyzeModel.ResumeTextAnalysisModel
import com.example.hito_3.data.AnalyzeModel.SkillsModel
import com.example.hito_3.data.BulletRewriter.BulletRewriterDto
import com.example.hito_3.data.BulletRewriter.ResumeBulletRewriterModel
import com.example.hito_3.data.SkillGapModel.SkillGapModel
import com.example.hito_3.data.SkillGapModel.SkillGapRequestDto
import com.example.hito_3.data.resumeMatch.Resume_JD_matchDto
import com.example.hito_3.data.resumeMatch.Resume_JD_matchModel
import com.example.hito_3.data.resumeSectionGenerator.ResumeSectionGeneratorDto
import com.example.hito_3.data.resumeSectionGenerator.ResumeSectionGeneratorModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

interface KlyrAPI {
    suspend fun analyzeTextResume(text: String): ResumeTextAnalysisModel
    suspend fun analyzeResumePdf(bytes: ByteArray): ResumeTextAnalysisModel
    suspend fun skillGapAnalyze( text1: List<String> , text2: String) : SkillGapModel
    suspend fun resune_JD_match(text1: String , text2: String): Resume_JD_matchModel
    suspend fun ResumeSectionGenerator(text1: String, text2: String, text3: String, text4: List<String>): ResumeSectionGeneratorModel
    suspend fun BulletRewriter(text1: String , text2: String, text3: String): ResumeBulletRewriterModel
}

val BASE_URL = "https://marcelino-bibliopolical-ellis.ngrok-free.dev"

class KtorKlyrApi(private val client: HttpClient) : KlyrAPI {
    override suspend fun analyzeTextResume(text: String): ResumeTextAnalysisModel {
        return client.post("$BASE_URL/analyze-resume") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("resume_text" to text))
        }.body()
    }

    override suspend fun analyzeResumePdf(bytes: ByteArray): ResumeTextAnalysisModel {
        return client.submitFormWithBinaryData(
            url = "$BASE_URL/analyze-resume-pdf",
            formData = formData {
                append(
                    "file",
                    bytes,
                    Headers.build {
                        append(HttpHeaders.ContentType, "application/pdf")
                        append(HttpHeaders.ContentDisposition, "filename=resume.pdf")
                    }
                )
            }
        ).body()

    }

    override suspend fun skillGapAnalyze(
        text1: List<String>,
        text2: String
    ): SkillGapModel {
        return client.post("$BASE_URL/skill-gap") {
            contentType(ContentType.Application.Json)
            setBody(
                SkillGapRequestDto(
                    resume_skills = text1,
                    target_role = text2
                )
            )
        }.body()
    }

    override suspend fun resune_JD_match(
        text1: String,
        text2: String
    ): Resume_JD_matchModel {
        return client.post("$BASE_URL/jd-match") {
            contentType(ContentType.Application.Json)
            setBody(
                Resume_JD_matchDto(
                    resume_text = text1,
                    job_description = text2
                )
            )
        }.body()
    }

    override suspend fun ResumeSectionGenerator(
        text1: String,
        text2: String,
        text3: String,
        text4: List<String>
    ): ResumeSectionGeneratorModel {
        return client.post("$BASE_URL/generate-section") {
            contentType(ContentType.Application.Json)
            setBody(
                ResumeSectionGeneratorDto(
                    section_type = text1,
                    role = text2,
                    experience_level = text3,
                    skills = text4
                )
            )
        }.body()
    }

    override suspend fun BulletRewriter(
        text1: String,
        text2: String,
        text3: String
    ): ResumeBulletRewriterModel {
        return client.post("$BASE_URL/rewrite-bullet") {
            contentType(ContentType.Application.Json)
            setBody(
                BulletRewriterDto(
                    bullet_point = text1,
                    target_role = text2,
                    experience_level = text3
                )
            )
        }.body()
    }


}