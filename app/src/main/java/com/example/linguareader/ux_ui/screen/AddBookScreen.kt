package com.example.linguareader.ux_ui.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext // Импортируем LocalContext
import androidx.core.content.FileProvider
import com.example.linguareader.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun AddBookScreen(onBackClick: () -> Unit, onOpenPdfClick: () -> Unit) {
    val context = LocalContext.current // Получаем контекст

    Column {
        Text("Добавить книгу")

        // Кнопка для открытия PDF-файла с устройства пользователя
        Button(onClick = onOpenPdfClick) {
            Text("Добавить с PDF с уст. поль", color = Color.White)
        }

        // Кнопка для открытия PDF-файла из ресурсов
        Button(onClick = {
            openPdfFromResources(context) // Передаем контекст в функцию
        }) {
            Text("Добавить с PDF из директории", color = Color.White)
        }

        Button(onClick = onBackClick) {
            Text("Назад", color = Color.White)
        }
    }
}

private fun openPdfFromResources(context: Context) {
    val pdfFileName = "devops.pdf" // Имя PDF-файла в res/raw
    val inputStream: InputStream = context.resources.openRawResource(R.raw.devops) // Открытие ресурса
    val file = File(context.cacheDir, pdfFileName) // Создание временного файла в кэше приложения

    // Запись содержимого InputStream в файл
    FileOutputStream(file).use { outputStream ->
        inputStream.copyTo(outputStream)
    }

    // Получение URI через FileProvider
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

    // Открытие PDF-файла
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Предоставление разрешения на чтение
    }
    context.startActivity(intent) // Запуск Intent
}