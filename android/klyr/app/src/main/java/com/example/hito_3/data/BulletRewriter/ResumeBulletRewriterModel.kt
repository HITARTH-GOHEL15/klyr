package com.example.hito_3.data.BulletRewriter


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResumeBulletRewriterModel(
    @SerialName("analysis_method")
    val analysisMethod: String = "",
    @SerialName("rewritten_bullet")
    val rewrittenBullet: String = "",
    @SerialName("why_this_is_better")
    val whyThisIsBetter: String = ""
)