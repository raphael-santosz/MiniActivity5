package com.example.myapplication5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication5.ui.theme.MyApplication5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navigationToastMessage = stringResource(R.string.navigation_toast) // 🔥 Correção aqui

    var byeMessage by rememberSaveable { mutableStateOf("") }
    var repetitions by rememberSaveable { mutableStateOf("") }
    var repetitionsError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.hello_world), style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = stringResource(R.string.introduce_bye_message))
        TextField(
            value = byeMessage,
            onValueChange = { byeMessage = it },
            placeholder = { Text(stringResource(R.string.enter_bye_greeting)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = repetitions,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    repetitions = it
                    repetitionsError = it.isEmpty() || it.toIntOrNull() == null || it.toInt() <= 0
                }
            },
            placeholder = { Text(stringResource(R.string.enter_repetitions)) },
            isError = repetitionsError,
            modifier = Modifier.fillMaxWidth()
        )

        if (repetitionsError) {
            Text(
                text = stringResource(R.string.error_repetitions),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Toast.makeText(context, navigationToastMessage, Toast.LENGTH_SHORT).show()  // 🔥 Correção aqui
            },
            enabled = !repetitionsError
        ) {
            Text(text = stringResource(R.string.go_to_bye_screen))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MyApplication5Theme {
        GreetingScreen()
    }
}
