package com.example.hito_3.user_interface.Auth

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hito_3.navigation.Screens
import com.example.hito_3.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val auth = remember { Firebase.auth }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = NightBlack,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = NightCharcoal
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // You can replace this with your actual illustration
                        // For now, using a placeholder icon representation
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_edit),
                            contentDescription = "Resume Illustration",
                            tint = RedSecondary,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Title
                Text(
                    text = "Renew your password",
                    fontFamily = poppins_bold,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = "Please enter your Email address to renew password",
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = TextLightGrey,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = "Enter your Email address",
                            fontFamily = poppins_regular,
                            fontWeight = FontWeight.Medium,
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
                        cursorColor = RedSecondary,
                        focusedLabelColor = RedSecondary,
                        unfocusedLabelColor = TextMediumGrey
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Send Request Button
                Button(
                    onClick = {
                        if (email.isNotBlank()) {
                            isLoading = true
                            scope.launch {
                                try {
                                    auth.sendPasswordResetEmail(email)
                                        .addOnSuccessListener {
                                            isLoading = false
                                            Toast.makeText(
                                                context,
                                                "Password reset email sent! Check your inbox.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        .addOnFailureListener { e ->
                                            isLoading = false
                                            Toast.makeText(
                                                context,
                                                "Error: ${e.message}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                } catch (e: Exception) {
                                    isLoading = false
                                    Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please enter your email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RedPrimary,
                        contentColor = TextWhite
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = TextWhite
                        )
                    } else {
                        Text(
                            text = "Send Request",
                            fontFamily = poppins_regular,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Info Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = NightSurface.copy(0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = RedSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Check your spam folder if you don't receive the email",
                            fontSize = 12.sp,
                            fontFamily = poppins_regular,
                            color = TextLightGrey,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Back to Login
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Remember your password?",
                        fontSize = 14.sp,
                        fontFamily = poppins_regular,
                        color = TextMediumGrey
                    )
                    TextButton(
                        onClick = {
                            navController.navigate(Screens.LoginScreenRoute) {
                                popUpTo(Screens.LoginScreenRoute) { inclusive = true }
                            }
                        }
                    ) {
                        Text(
                            text = "Sign In",
                            color = RedSecondary,
                            fontFamily = poppins_regular,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    HITO_3Theme {
        // Preview
    }
}