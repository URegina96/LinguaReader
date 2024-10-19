package com.example.linguareader.ux_ui.screen

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.linguareader.data.model.Book
import com.example.linguareader.ui.theme.CustomTypography
import com.example.linguareader.ui.theme.TextPrimaryDark
import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.linguareader.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun BookListScreen(books: List<Book>, onBookClick: (Book) -> Unit, onOpenPdfClick: () -> Unit) {
    val context = LocalContext.current // Получаем контекст

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "📚 Список книг",
            style = CustomTypography.displayLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Кнопка для открытия PDF-файла с устройства пользователя
        Button(onClick = onOpenPdfClick) {
            Text("Добавить с PDF с уст. поль", color = Color.White)
        }

        // Кнопка для открытия PDF-файла из ресурсов
        Button(onClick = {
            openPdfFromResources(context) // Передаем контекст в функцию
        }) {
            Text("Открыть PDF из директории", color = Color.White)
        }
        // Кнопка для открытия его списка PDF-файла на устройстве пользователя, что бы он мог там дальше читать сам
        Button(onClick = {
            openReadPdfFromResourcesUser(context) // Передаем контекст в функцию
        }) {
            Text("Посмотреть свои PDF на устроистве", color = Color.White)
        }

        // Отображение списка книг
        books.forEach { book ->
            Text(
                text = book.title, // title для отображения названия книги
                style = CustomTypography.bodyLarge.copy(color = TextPrimaryDark),
                modifier = Modifier
                    .clickable { onBookClick(book) }
                    .padding(8.dp)
            )
        }
    }
}

// Функция открытия PDF из ресурсов
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
// Функция открытия PDF из ресурсов для простого просмотра ресурсов
private fun openReadPdfFromResourcesUser (context: Context) {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "application/pdf" // Указываем тип содержимого
        addCategory(Intent.CATEGORY_OPENABLE) // Позволяем открывать
    }
    context.startActivity(Intent.createChooser(intent, "Выберите PDF-файл"))
}