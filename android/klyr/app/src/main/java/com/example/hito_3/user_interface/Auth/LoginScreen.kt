package com.example.hito_3.user_interface.Auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hito_3.navigation.Screens
import com.example.hito_3.ui.theme.HITO_3Theme
import com.example.hito_3.ui.theme.NightBlack
import com.example.hito_3.ui.theme.NightCharcoal
import com.example.hito_3.ui.theme.NightSurface
import com.example.hito_3.ui.theme.RedPrimary
import com.example.hito_3.ui.theme.RedSecondary
import com.example.hito_3.ui.theme.TextLightGrey
import com.example.hito_3.ui.theme.TextMediumGrey
import com.example.hito_3.ui.theme.TextWhite
import com.example.hito_3.ui.theme.poppins_bold
import com.example.hito_3.ui.theme.poppins_medium
import com.example.hito_3.ui.theme.poppins_regular
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val viewModel = koinViewModel<LoginViewModel>()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value) {
            LoginState.Error -> {
                Toast.makeText(context , "Sign In failed", Toast.LENGTH_SHORT).show()
            }
            LoginState.Success -> {
                navController.navigate(Screens.HomeScreenRoute)
            }
            else -> {}
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NightBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Illustration/Logo Section
            Card(
                modifier = Modifier.size(100.dp),
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

            Spacer(modifier = Modifier.height(48.dp))

            // Welcome Title
            Text(
                text = "Welcome to Klyr",
                fontSize = 32.sp,
                fontFamily = poppins_regular,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to continue",
                fontFamily = poppins_regular,
                fontSize = 16.sp,
                color = TextLightGrey
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Email Field
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Email",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = "Enter your email",
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Password Field
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Password",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "Enter your password",
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
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Default.Done
                                else
                                    Icons.Default.Clear,
                                contentDescription = if (passwordVisible)
                                    "Hide password"
                                else
                                    "Show password",
                                tint = TextMediumGrey
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Forgot Password
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(Screens.ForgotPasswordScreenRoute)
                    }
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = RedSecondary,
                        fontFamily = poppins_regular,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Continue Button
            Button(
                onClick = {
                    viewModel.logIn(email , password)
                },
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
                    text = "Continue",
                    fontSize = 18.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // OR Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    color = TextMediumGrey.copy(alpha = 0.3f)
                )
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = TextMediumGrey,
                    fontSize = 14.sp
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = TextMediumGrey.copy(alpha = 0.3f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Already have account
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "don't have an account?",
                    fontSize = 14.sp,
                    fontFamily = poppins_regular,
                    color = TextMediumGrey
                )
                TextButton(
                    onClick = {
                        navController.navigate(Screens.SignupScreenRoute)
                    }
                ) {
                    Text(
                        text = "Sign Up",
                        color = RedSecondary,
                        fontFamily = poppins_regular,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Terms and Privacy
            Text(
                text = "By continuing, you agree to our Terms of Service & Privacy Policy.",
                fontSize = 13.sp,
                fontFamily = poppins_regular,
                color = TextMediumGrey,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    HITO_3Theme(

    ) {

    }
}