package com.example.linguareader.ux_ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    Column {
        Text("Настройки")
        Button(onClick = onBackClick) {
            Text(
                text = "Продолжить чтение",
                color = Color.White
            )
        }
    }
}