package com.example.linguareader.ux_ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.example.linguareader.ui.theme.CustomButton

@Composable
fun HomeScreen(
    onContinueReadingClick: () -> Unit,
    onBookListClick: () -> Unit,
    onAddBookClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(text = "Продолжить чтение", onClick = onContinueReadingClick)
        CustomButton(text = "Мои книги", onClick = onBookListClick)
        CustomButton(text = "Добавить книгу", onClick = onAddBookClick)
        CustomButton(text = "Настройки", onClick = onSettingsClick)
    }
}
