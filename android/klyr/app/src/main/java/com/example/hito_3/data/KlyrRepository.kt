package com.example.hito_3.data

import com.example.hito_3.data.AnalyzeModel.ResumeTextAnalysisModel
import com.example.hito_3.data.BulletRewriter.ResumeBulletRewriterModel
import com.example.hito_3.data.SkillGapModel.SkillGapModel
import com.example.hito_3.data.resumeMatch.Resume_JD_matchModel
import com.example.hito_3.data.resumeSectionGenerator.ResumeSectionGeneratorModel

class KlyrRepository(
    private val api: KlyrAPI
) {
    suspend fun analyzeText(text: String): ResumeTextAnalysisModel {
        return api.analyzeTextResume(text)
    }

    suspend fun analyzePdf(bytes: ByteArray): ResumeTextAnalysisModel {
        return api.analyzeResumePdf(bytes)
    }

    suspend fun skillGapAnalyze(text1: List<String> , text2: String): SkillGapModel {
        return api.skillGapAnalyze(text1 , text2)
    }

    suspend fun resume_JD_match(text1: String , text2: String): Resume_JD_matchModel {
        return api.resune_JD_match(text1 , text2)
    }

    suspend fun BulletRewriter(text1: String, text2: String, text3: String): ResumeBulletRewriterModel {
        return api.BulletRewriter(text1, text2, text3)
    }

    suspend fun resumeSectionGeneroter(text1: String, text2: String, text3: String, text4: List<String>): ResumeSectionGeneratorModel {
        return api.ResumeSectionGenerator(text1, text2, text3, text4)
    }
}