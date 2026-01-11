package com.example.hito_3.user_interface.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hito_3.navigation.Screens
import com.example.hito_3.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val viewModel: SettingsViewModel = koinViewModel()
    val userProfile by viewModel.userProfile.collectAsState()

    SettingsScreenContent(
        userName = userProfile?.fullName ?: "User",
        userEmail = userProfile?.email ?: "",
        onEditProfileClick = {
            navController.navigate(Screens.EditProfileScreenRoute)
        },
        onLogoutClick = {
            viewModel.logout()
            navController.navigate(Screens.LoginScreenRoute) {
                popUpTo(Screens.HomeScreenRoute) { inclusive = true }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    userName: String = "Alex Taylor",
    userEmail: String = "alex.taylor@example.com",
    onEditProfileClick: () -> Unit = {},
    onUpdateResumeClick: () -> Unit = {},
    onSavedJobsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    var isDarkTheme by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = NightBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontSize = 28.sp,
                        fontFamily = poppins_regular,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NightBlack
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Section
            ProfileSection(
                userName = userName,
                userEmail = userEmail
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Account Section
            SectionHeader(title = "ACCOUNT")

            Spacer(modifier = Modifier.height(12.dp))

            SettingsCard {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Edit profile",
                    onClick = onEditProfileClick
                )

                Divider(
                    color = TextMediumGrey.copy(alpha = 0.2f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                SettingsItem(
                    icon = Icons.Default.Refresh,
                    title = "Update resume history",
                    onClick = onUpdateResumeClick
                )

                Divider(
                    color = TextMediumGrey.copy(alpha = 0.2f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                SettingsItem(
                    icon = Icons.Default.Star,
                    title = "Saved job roles",
                    onClick = onSavedJobsClick
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Logout Button
            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Logout",
                    fontSize = 18.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = RedPrimary
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileSection(
    userName: String,
    userEmail: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Avatar
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(NightCharcoal),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = TextWhite,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = userName,
                fontSize = 22.sp,
                fontFamily = poppins_regular,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = userEmail,
                fontSize = 14.sp,
                fontFamily = poppins_regular,
                color = TextMediumGrey
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontFamily = poppins_regular,
        fontWeight = FontWeight.SemiBold,
        color = TextMediumGrey,
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
fun SettingsCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        )
    ) {
        content()
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(RedSecondary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = RedSecondary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = poppins_regular,
            color = TextWhite,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Navigate",
            tint = TextMediumGrey,
            modifier = Modifier.size(24.dp)
        )
    }
}


@Preview
@Composable
fun SettingsScreenPreview() {
    HITO_3Theme {

    }
}