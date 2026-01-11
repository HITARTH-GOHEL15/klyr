package com.example.hito_3.data.resumeMatch

import kotlinx.serialization.Serializable

@Serializable
data class Resume_JD_matchDto(
    val resume_text: String,
    val job_description: String
)