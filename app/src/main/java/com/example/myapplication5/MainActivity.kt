package com.example.myapplication5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication5.ui.GreetingScreen
import com.example.myapplication5.ui.ByeScreen
import com.example.myapplication5.ui.theme.MyApplication5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication5Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "greeting_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("greeting_screen") { GreetingScreen(navController) }
                        composable("bye_screen/{byeMessage}/{repetitions}") { backStackEntry ->
                            val byeMessage = backStackEntry.arguments?.getString("byeMessage") ?: ""
                            val repetitions = backStackEntry.arguments?.getString("repetitions")?.toIntOrNull() ?: 1
                            ByeScreen(navController, byeMessage, repetitions)
                        }
                    }
                }
            }
        }
    }
}
