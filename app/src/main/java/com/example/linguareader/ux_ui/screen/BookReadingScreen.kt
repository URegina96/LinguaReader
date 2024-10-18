package com.example.linguareader.ux_ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextLayoutResult
import com.example.linguareader.ui.theme.HighlightColor
import com.example.linguareader.ui.theme.TextPrimaryDark

@Composable
fun BookReadingScreen(
    longText: String,
    onWordClicked: (String) -> Unit
) {
    var selectedWord by remember { mutableStateOf("") }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = buildAnnotatedString {
                    longText.split(" ").forEach { word ->
                        pushStyle(
                            SpanStyle(
                                fontSize = 18.sp,
                                fontWeight = if (word == selectedWord) FontWeight.Bold else FontWeight.Normal,
                                background = if (word == selectedWord) HighlightColor else Color.Transparent,
                                color = if (word == selectedWord) TextPrimaryDark else MaterialTheme.colorScheme.primary
                            )
                        )
                        append("$word ")
                        pop()
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                textLayoutResult?.let {
                                    val word = getWordAtOffset(it, offset)
                                    if (word.isNotEmpty()) {
                                        selectedWord = word
                                        onWordClicked(word)
                                    }
                                }
                            }
                        )
                    },
                lineHeight = 24.sp,
                onTextLayout = { textLayoutResult = it },
                textAlign = TextAlign.Start
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp)
                .background(if (selectedWord.isNotEmpty()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (selectedWord.isNotEmpty()) "Выбранное слово: $selectedWord" else "Нажмите на слово",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
private fun getWordAtOffset(
    textLayoutResult: TextLayoutResult,
    offset: androidx.compose.ui.geometry.Offset
): String {
    val layoutInput = textLayoutResult.layoutInput
    val text = layoutInput.text
    val start = textLayoutResult.getOffsetForPosition(offset)
    var currentIndex = 0

    text.split(" ").forEach { word ->
        if (currentIndex <= start && currentIndex + word.length >= start) {
            return word
        }
        currentIndex += word.length + 1 // Account for space
    }
    return ""
}


