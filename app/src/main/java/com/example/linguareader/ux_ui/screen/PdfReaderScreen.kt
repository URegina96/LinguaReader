package com.example.linguareader.ux_ui.screen

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.graphics.toArgb
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File

@Composable
fun PdfReaderScreen(filePath: String, onBackClick: () -> Unit) {
    val context = LocalContext.current
    var currentPage by rememberSaveable { mutableStateOf(0) }
    var pageCount by remember { mutableStateOf(0) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    // Загрузка PDF
    LaunchedEffect(filePath) {
        try {
            val file = File(filePath)
            val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)

            pageCount = pdfRenderer.pageCount
            if (pageCount > 0) {
                val page = pdfRenderer.openPage(currentPage)
                val bitmapTemp = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                page.render(bitmapTemp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                bitmap = bitmapTemp.asImageBitmap()
                page.close()
            }
            pdfRenderer.close()
            parcelFileDescriptor.close()
        } catch (e: Exception) {
            Log.e("PdfReaderScreen", "Error loading PDF", e)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        bitmap?.let {
            Image(bitmap = it, contentDescription = null, modifier = Modifier.fillMaxSize())
        }

        // Кнопка назад
        Button(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart).padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Назад")
        }

        // Кнопки навигации по страницам (добавьте при необходимости)
    }
    Text(text = "Displaying PDF from path: $filePath")
}
