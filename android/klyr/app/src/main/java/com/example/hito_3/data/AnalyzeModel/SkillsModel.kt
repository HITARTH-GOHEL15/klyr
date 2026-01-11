package com.example.hito_3.data.AnalyzeModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkillsModel(
    @SerialName("soft")
    val soft: List<String> = listOf(),
    @SerialName("technical")
    val technical: List<String> = listOf()
)