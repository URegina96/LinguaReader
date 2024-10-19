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

@Composable
fun BookListScreen(books: List<Book>, onBookClick: (Book) -> Unit, onOpenPdfClick: () -> Unit) {
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