package com.example.linguareader.ux_ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AddBookScreen(onBackClick: () -> Unit) {
    Column {
        Text("Добавить книгу")
        Button(onClick = onBackClick) {
            Text(
                text = "Продолжить чтение",
                color = Color.White
            )
        }
    }
}
