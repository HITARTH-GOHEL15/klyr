package com.example.hito_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hito_3.navigation.NavGraph
import com.example.hito_3.ui.theme.HITO_3Theme
import com.example.hito_3.user_interface.Auth.LoginScreen
import com.example.hito_3.user_interface.Auth.SignUpScreen
import com.example.hito_3.user_interface.HomeScreen
import com.example.hito_3.user_interface.resumeAnalyze.ResumeAnalyzeScreen
import com.example.hito_3.user_interface.setting.EditProfileScreen
import com.example.hito_3.user_interface.setting.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HITO_3Theme {
                NavGraph(
                    context = this@MainActivity
                )
            }
        }
    }
}

