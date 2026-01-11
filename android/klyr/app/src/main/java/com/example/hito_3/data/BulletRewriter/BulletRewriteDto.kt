package com.example.hito_3.data.BulletRewriter

import kotlinx.serialization.Serializable

@Serializable
data class BulletRewriterDto(
   val bullet_point: String,
   val target_role: String,
   val experience_level: String
)
