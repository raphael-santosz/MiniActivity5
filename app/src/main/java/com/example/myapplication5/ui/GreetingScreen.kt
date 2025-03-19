package com.example.myapplication5.ui

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication5.R
import androidx.compose.ui.graphics.asImageBitmap
import com.example.myapplication5.ui.theme.MyApplication5Theme

@Composable
fun GreetingScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navigationToastMessage = stringResource(R.string.navigation_toast)

    var byeMessage by rememberSaveable { mutableStateOf("") }
    var repetitions by rememberSaveable { mutableStateOf("") }
    var repetitionsError by remember { mutableStateOf(false) }

    var finalMessage by remember { mutableStateOf("") }

    LaunchedEffect(navController.currentBackStackEntry) {
        val returnedMessage = navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.get<String>("finalMessage")

        finalMessage = returnedMessage ?: context.getString(R.string.hello_world)
    }

    // Para a imagem da galeria
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        selectedBitmap = uri?.let { loadBitmapFromUri(context, it) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = finalMessage, style = MaterialTheme.typography.headlineMedium)

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
                if (byeMessage.isNotBlank() && repetitions.isNotBlank() && !repetitionsError) {
                    finalMessage = byeMessage.repeat(repetitions.toInt())
                    navController.navigate("bye_screen/$byeMessage/$repetitions")
                } else {
                    Toast.makeText(context, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = true
        ) {
            Text(text = stringResource(R.string.go_to_bye_screen))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão para selecionar imagem e exibição da imagem
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text(text = "Select Image from Gallery")
            }

            selectedBitmap?.let { bitmap ->
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}

// Função para carregar Bitmap da URI
fun loadBitmapFromUri(context: android.content.Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MyApplication5Theme {
        GreetingScreen(navController = NavHostController(LocalContext.current))
    }
}
