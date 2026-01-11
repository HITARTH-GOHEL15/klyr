package com.example.hito_3.data.AnalyzeModel

import com.example.hito_3.data.AnalyzeModel.SkillsModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResumeTextAnalysisModel(
    @SerialName("analysis_method")
    val analysisMethod: String = "",
    @SerialName("ats_score")
    val atsScore: Int = 0,
    @SerialName("improvement_suggestions")
    val improvementSuggestions: List<String> = listOf(),
    @SerialName("missing_sections")
    val missingSections: List<String> = listOf(),
    @SerialName("skills")
    val skills: SkillsModel = SkillsModel(),
    @SerialName("strengths")
    val strengths: List<String> = listOf(),
    @SerialName("weaknesses")
    val weaknesses: List<String> = listOf()
)