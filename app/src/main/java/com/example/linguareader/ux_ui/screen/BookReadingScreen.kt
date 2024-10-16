package com.example.linguareader.ux_ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextLayoutResult
import com.example.linguareader.R

@Composable
fun BookReadingScreen(
    longText: String,
    onWordClicked: (String) -> Unit
) {
    var selectedWord by remember { mutableStateOf("") }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var scrollState by remember { mutableStateOf(0) }

    // Основная структура экрана
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Верхняя часть с длинным текстом
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            // Текст
            Box(
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            textLayoutResult?.let { result ->
                                val word = getWordAtOffset(result, offset)
                                if (word.isNotEmpty()) {
                                    selectedWord = word
                                    onWordClicked(word)
                                }
                            }
                        }
                    }
            ) {
                Text(
                    text = buildAnnotatedString {
                        longText.split(" ").forEach { word ->
                            pushStyle(
                                SpanStyle(
                                    fontSize = 18.sp,
                                    fontWeight = if (word == selectedWord) FontWeight.Bold else FontWeight.Normal,
                                    background = if (word == selectedWord) MaterialTheme.colorScheme.background.copy(
                                        alpha = 0.2f
                                    ) else MaterialTheme.colorScheme.background
                                )
                            )
                            append("$word ")
                            pop()
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (-scrollState).dp),
                    lineHeight = 24.sp,
                    onTextLayout = { textLayoutResult = it },
                    textAlign = TextAlign.Start
                )
            }

            // Ползунок
            Column(
                modifier = Modifier
                    .width(70.dp) // Увеличиваем ширину колонки
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceEvenly // Увеличиваем расстояние между иконками
            ) {
                // Кнопка вверх
                IconButton(
                    onClick = {
                        scrollState = (scrollState - 10).coerceAtLeast(0) // Ограничиваем прокрутку
                        selectedWord = "" // Сбрасываем выделенное слово
                    },
                    modifier = Modifier.padding(bottom = 8.dp) // Расстояние между иконками
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_up),
                        contentDescription = "Вверх",
                        modifier = Modifier.size(32.dp) // Увеличиваем размер иконки
                    )
                }

                // Кнопка вниз
                IconButton(
                    onClick = {
                        scrollState += 10 // Увеличиваем прокрутку
                        selectedWord = "" // Сбрасываем выделенное слово
                    },
                    modifier = Modifier.padding(top = 8.dp) // Расстояние между иконками
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_down),
                        contentDescription = "Вниз",
                        modifier = Modifier.size(32.dp) // Увеличиваем размер иконки
                    )
                }
            }
        }

        // Нижняя часть с выбранным словом
        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (selectedWord.isNotEmpty()) "Выбранное слово: $selectedWord" else "Нажмите на слово",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

// Функция для получения слова по координатам нажатия
private fun getWordAtOffset(
    textLayoutResult: TextLayoutResult,
    offset: androidx.compose.ui.geometry.Offset
): String {
    val layoutInput = textLayoutResult.layoutInput
    val text = layoutInput.text
    val start = textLayoutResult.getOffsetForPosition(offset)
    val end = start
    // Получаем слово на основе начального и конечного индексов
    val words = text.split(" ")
    var currentIndex = 0
    for (word in words) {
        if (currentIndex <= start && currentIndex + word.length >= end) {
            return word
        }
        currentIndex += word.length + 1 // Учитываем пробел
    }
    return ""
}