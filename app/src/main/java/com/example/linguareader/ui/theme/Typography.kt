package com.example.linguareader.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight


val CustomTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = HighlightColor
    )
)