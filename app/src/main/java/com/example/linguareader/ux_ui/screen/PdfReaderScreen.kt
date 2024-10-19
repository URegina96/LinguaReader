package com.example.linguareader.ux_ui.screen

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PdfReaderScreen(uri: Uri, onBackClick: () -> Unit) {
    val context = LocalContext.current
    var pdfText by remember { mutableStateOf("") }

    // Извлечение текста из PDF в фоновом потоке
    LaunchedEffect(uri) {
        withContext(Dispatchers.IO) {
            pdfText = PdfUtils.extractTextFromPdf(context, uri)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = if (pdfText.isNotEmpty()) pdfText else "Загрузка...",
                modifier = Modifier.weight(1f)
            )

            // Кнопка назад
            Button(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.Start).padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Назад")
            }
        }
    }
}
