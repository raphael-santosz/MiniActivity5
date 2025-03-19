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
        Text(text = "Hello World!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Introduce the Bye Message:")
        TextField(
            value = byeMessage,
            onValueChange = { byeMessage = it },
            placeholder = { Text("Enter text for the Bye greeting here") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = repetitions,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {  // Garante que apenas números sejam aceitos
                    repetitions = it
                    repetitionsError = it.isEmpty() || it.toIntOrNull() == null || it.toInt() <= 0
                }
            },
            placeholder = { Text("Enter number of repetitions here") },
            isError = repetitionsError,
            modifier = Modifier.fillMaxWidth()
        )


        if (repetitionsError) {
            Text(
                text = "Enter a valid number of repetitions",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Navegação ainda não implementada", Toast.LENGTH_SHORT).show()
            },
            enabled = !repetitionsError
        ) {
            Text(text = "Go to bye screen")
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
