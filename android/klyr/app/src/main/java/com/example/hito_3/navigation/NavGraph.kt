package com.example.hito_3.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hito_3.MainActivity
import com.example.hito_3.user_interface.Auth.ForgotPasswordScreen
import com.example.hito_3.user_interface.Auth.LogUpViewModel
import com.example.hito_3.user_interface.Auth.LoginScreen
import com.example.hito_3.user_interface.Auth.LoginViewModel
import com.example.hito_3.user_interface.Auth.SignUpScreen
import com.example.hito_3.user_interface.HomeScreen
import com.example.hito_3.user_interface.bullet.BulletRewriterScreen
import com.example.hito_3.user_interface.dashboard.DashboardScreen
import com.example.hito_3.user_interface.resumeAnalyze.ResumeAnalyzeScreen
import com.example.hito_3.user_interface.resume_JD_match.ResumeJDMatchScreen
import com.example.hito_3.user_interface.sectionGenerator.ResumeSectionGeneratorScreen
import com.example.hito_3.user_interface.setting.EditProfileScreen
import com.example.hito_3.user_interface.skillGap.SkillGapAnalyzeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.androidx.compose.koinViewModel


@Composable
fun NavGraph(
    context: MainActivity
) {
    val navController = rememberNavController()
    val currentUser = Firebase.auth.currentUser
    val start = if (currentUser != null) Screens.HomeScreenRoute else Screens.LoginScreenRoute


    NavHost(
        navController = navController,
        startDestination = start,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(100)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(100)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(100)
            )
        }
    ) {
        //Auth
        composable<Screens.LoginScreenRoute> {
            LoginScreen(
                navController = navController
            )
        }

        composable<Screens.SignupScreenRoute> {
            SignUpScreen(
                navController
            )
        }

        composable<Screens.HomeScreenRoute> {
            HomeScreen(
                navController
            )
        }

        composable<Screens.EditProfileScreenRoute> {
            EditProfileScreen(
               navController
            )
        }


       composable<Screens.ResumeAnalyzeScreenRoute> {
           ResumeAnalyzeScreen(
               onBackClick = {
                   navController.popBackStack()
               }
           )
       }

        composable<Screens.SkillGapAnalyzeScreenRoute> {
            SkillGapAnalyzeScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screens.ResumeSectionGeneratorRoute> {
            ResumeSectionGeneratorScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screens.ResumeJDmatchRoute> {
            ResumeJDMatchScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screens.ResumeBulletRewriteScreenRoute> {
            BulletRewriterScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screens.ForgotPasswordScreenRoute> {
            ForgotPasswordScreen(
                navController
            )
        }
    }
}