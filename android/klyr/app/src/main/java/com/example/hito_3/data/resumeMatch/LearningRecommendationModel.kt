package com.example.hito_3.data.resumeMatch


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LearningRecommendationModel(
    @SerialName("how_to_learn")
    val howToLearn: String = "",
    @SerialName("skill")
    val skill: String = "",
    @SerialName("why_needed")
    val whyNeeded: String = ""
)