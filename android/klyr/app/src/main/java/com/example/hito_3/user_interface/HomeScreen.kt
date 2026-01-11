package com.example.hito_3.user_interface

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hito_3.navigation.Screens
import com.example.hito_3.ui.theme.NightBlack
import com.example.hito_3.ui.theme.NightCharcoal
import com.example.hito_3.ui.theme.NightSurface
import com.example.hito_3.ui.theme.RedSecondary
import com.example.hito_3.ui.theme.SuccessGreen
import com.example.hito_3.ui.theme.TextLightGrey
import com.example.hito_3.ui.theme.TextMediumGrey
import com.example.hito_3.ui.theme.TextWhite
import com.example.hito_3.user_interface.dashboard.DashboardScreen
import com.example.hito_3.user_interface.setting.SettingsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
) {
    var bottomNavigationScreens by rememberSaveable {
        mutableStateOf(BottomNavigationScreens.HomeScreenView)
    }

    var isHomeScreen by remember {
        mutableStateOf(true)
    }


    Scaffold(
        containerColor = NightBlack,
        bottomBar = {
            NavigationBar(
                containerColor = NightCharcoal,
                contentColor = TextWhite
            ) {
                NavigationBarItem(
                    selected = bottomNavigationScreens == BottomNavigationScreens.HomeScreenView,
                    onClick = {
                        bottomNavigationScreens = BottomNavigationScreens.HomeScreenView
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = RedSecondary,
                        selectedTextColor = RedSecondary,
                        unselectedIconColor = TextMediumGrey,
                        unselectedTextColor = TextMediumGrey,
                        indicatorColor = NightSurface
                    )
                )

                NavigationBarItem(
                    selected = bottomNavigationScreens == BottomNavigationScreens.HomeScreenView,
                    onClick = {
                        bottomNavigationScreens = BottomNavigationScreens.SettingScreenView
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    },
                    label = { Text("Settings") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = RedSecondary,
                        selectedTextColor = RedSecondary,
                        unselectedIconColor = TextMediumGrey,
                        unselectedTextColor = TextMediumGrey,
                        indicatorColor = NightSurface
                    )
                )
            }
        }
    ) { paddingValues ->
       AnimatedContent(
           targetState = bottomNavigationScreens,
           label = "",
           transitionSpec = {
               when(this.targetState) {
                   BottomNavigationScreens.HomeScreenView -> slideInHorizontally(){it}.togetherWith(slideOutHorizontally(){-it})
                   BottomNavigationScreens.SettingScreenView -> slideInHorizontally(){it}.togetherWith(slideOutHorizontally(){-it})
               }
           },
           modifier = Modifier
               .background(color = MaterialTheme.colorScheme.background)
       ) { navScreen ->
           when(navScreen) {
               BottomNavigationScreens.HomeScreenView -> {
                   isHomeScreen = true
                   DashboardScreen(
                       padding = paddingValues,
                       onAnalyzeClick = {
                           navController.navigate(Screens.ResumeAnalyzeScreenRoute)
                       },
                       onSkillGapClick = {
                           navController.navigate(Screens.SkillGapAnalyzeScreenRoute)
                       },
                       onSectionGenClick = {
                           navController.navigate(Screens.ResumeSectionGeneratorRoute)
                       },
                       onResumeJDMatchClick = {
                           navController.navigate(Screens.ResumeJDmatchRoute)
                       },
                       onBulletRewriterClick = {
                           navController.navigate(Screens.ResumeBulletRewriteScreenRoute)
                       }
                   )
               }

               BottomNavigationScreens.SettingScreenView -> {
                   isHomeScreen = false
                   SettingsScreen(
                       navController
                   )
               }
           }
       }
    }
}

private enum class BottomNavigationScreens{
    HomeScreenView,
    SettingScreenView
}