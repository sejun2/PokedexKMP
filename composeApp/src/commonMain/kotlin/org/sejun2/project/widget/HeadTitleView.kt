package com.sejun2.shared.presentation.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HeadTitleView(title: String, color: Color) {
    Text(
        title,
        style = MaterialTheme.typography.body1.copy(
            color = color,
            fontWeight = FontWeight.W800,
            fontSize = 16.sp
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
