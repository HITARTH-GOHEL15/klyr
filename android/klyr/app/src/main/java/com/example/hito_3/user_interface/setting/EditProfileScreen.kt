package com.example.hito_3.user_interface.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hito_3.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileScreen(
    navController: NavController
) {
    val viewModel: SettingsViewModel = koinViewModel()
    val userProfile by viewModel.userProfile.collectAsState()

    EditProfileScreenContent(
        initialName = userProfile?.fullName ?: "",
        initialEmail = userProfile?.email ?: "",
        initialPhone = userProfile?.phoneNumber ?: "",
        initialLocation = userProfile?.location ?: "",
        initialBio = userProfile?.bio ?: "",
        onSaveClick = { name, email, phone, location, bio ->
            viewModel.updateProfile(name, email, phone, location, bio)
            navController.navigateUp()
        },
        onBackClick = {
            navController.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreenContent(
    initialName: String = "Alex Taylor",
    initialEmail: String = "alex.taylor@example.com",
    initialPhone: String = "+1 234 567 8900",
    initialLocation: String = "San Francisco, CA",
    initialBio: String = "Passionate software developer with 5 years of experience",
    onSaveClick: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    onBackClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var phone by remember { mutableStateOf(initialPhone) }
    var location by remember { mutableStateOf(initialLocation) }
    var bio by remember { mutableStateOf(initialBio) }

    Scaffold(
        containerColor = NightBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        fontSize = 22.sp,
                        fontFamily = poppins_regular,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
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
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Picture Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(NightCharcoal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = TextWhite,
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = { /* TODO: Change photo */ }) {
                    Text(
                        text = "Change Photo",
                        color = RedSecondary,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Full Name Field
            ProfileTextField(
                label = "Full Name",
                value = name,
                onValueChange = { name = it },
                placeholder = "Enter your full name"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email Field
            ProfileTextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                placeholder = "Enter your email",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Phone Field
            ProfileTextField(
                label = "Phone Number",
                value = phone,
                onValueChange = { phone = it },
                placeholder = "Enter your phone number",
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Location Field
            ProfileTextField(
                label = "Location",
                value = location,
                onValueChange = { location = it },
                placeholder = "Enter your location"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Bio Field
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Bio",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    placeholder = {
                        Text(
                            text = "Tell us about yourself",
                            color = TextMediumGrey
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NightSurface,
                        unfocusedContainerColor = NightSurface,
                        focusedBorderColor = RedSecondary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        cursorColor = RedSecondary
                    ),
                    maxLines = 4
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Save Changes Button
            Button(
                onClick = { onSaveClick(name, email, phone, location, bio) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedPrimary,
                    contentColor = TextWhite
                )
            ) {
                Text(
                    text = "Save Changes",
                    fontSize = 18.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontFamily = poppins_regular,
            fontWeight = FontWeight.SemiBold,
            color = TextWhite,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextMediumGrey
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = NightSurface,
                unfocusedContainerColor = NightSurface,
                focusedBorderColor = RedSecondary,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                cursorColor = RedSecondary
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    HITO_3Theme {

    }
}