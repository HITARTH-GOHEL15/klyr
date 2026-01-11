package com.example.hito_3.data.resumeSectionGenerator

import kotlinx.serialization.Serializable

@Serializable
data class ResumeSectionGeneratorDto(
    val section_type: String,
    val role: String,
    val experience_level: String,
    val skills: List<String>
)
