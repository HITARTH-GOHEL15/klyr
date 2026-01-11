package com.example.hito_3.navigation

import kotlinx.serialization.Serializable


sealed class Screens(val route: String) {
    @Serializable
    object HomeScreenRoute

    @Serializable
    object LoginScreenRoute

    @Serializable
    object SignupScreenRoute

    @Serializable
    object DashboardScreenRoute

    @Serializable
    object SettingScreenRoute

    @Serializable
    object EditProfileScreenRoute

    @Serializable
    object ResumeAnalyzeScreenRoute

    @Serializable
    object SkillGapAnalyzeScreenRoute

    @Serializable
    object ResumeSectionGeneratorRoute

    @Serializable
    object ResumeJDmatchRoute

    @Serializable
    object ResumeBulletRewriteScreenRoute

    @Serializable
    object ForgotPasswordScreenRoute
}