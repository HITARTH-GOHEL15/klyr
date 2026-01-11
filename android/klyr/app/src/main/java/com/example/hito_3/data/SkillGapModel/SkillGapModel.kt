package com.example.hito_3.data.SkillGapModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkillGapModel(
    @SerialName("analysis_method")
    val analysisMethod: String = "",
    @SerialName("learning_recommendations")
    val learningRecommendations: List<LearningRecommendationModel> = listOf(),
    @SerialName("match_percentage")
    val matchPercentage: Int = 0,
    @SerialName("matched_skills")
    val matchedSkills: List<String> = listOf(),
    @SerialName("missing_skills")
    val missingSkills: List<String> = listOf(),
    @SerialName("skill_gap_summary")
    val skillGapSummary: String = ""
)