package com.example.hito_3.data.resumeSectionGenerator


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResumeSectionGeneratorModel(
    @SerialName("analysis_method")
    val analysisMethod: String = "",
    @SerialName("generated_section")
    val generatedSection: String = ""
)