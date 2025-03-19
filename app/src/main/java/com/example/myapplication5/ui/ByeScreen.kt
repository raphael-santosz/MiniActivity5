package com.example.myapplication5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication5.R

@Composable
fun ByeScreen(navController: NavController, byeMessage: String, repetitions: Int) {
    val finalMessage = byeMessage.repeat(repetitions)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = finalMessage)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("finalMessage", finalMessage) // 🔥 Correção aqui
                navController.popBackStack()
            }
        ) {
            Text(text = stringResource(R.string.go_back))
        }
    }
}
