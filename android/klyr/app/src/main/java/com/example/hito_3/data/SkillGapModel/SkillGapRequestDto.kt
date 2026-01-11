package com.example.hito_3.data.SkillGapModel

import kotlinx.serialization.Serializable

@Serializable
data class SkillGapRequestDto(
    val resume_skills: List<String>,
    val target_role: String
)
