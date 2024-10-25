package com.example.linguareader.ux_ui.screen

import androidx.lifecycle.viewmodel.compose.viewModel
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.linguareader.ux_ui.viewmodel.PdfViewModel
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@Composable
fun PdfReaderScreen(uri: Uri, onBackClick: () -> Unit, viewModel: PdfViewModel = viewModel()) {
    val context = LocalContext.current
    val pdfText by viewModel.pdfText
    val isLoading by viewModel.isLoading
    val progress by viewModel.progress

    LaunchedEffect(uri) {
        viewModel.loadPdfText(context, uri)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(progress = progress)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${(progress * 100).toInt()}%")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = pdfText,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Назад")
                }
            }
        }
    }
}
