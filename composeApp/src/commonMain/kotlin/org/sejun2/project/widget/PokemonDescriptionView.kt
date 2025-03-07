package com.sejun2.shared.presentation.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PokemonDescriptionView(desc: String) {
    Text(
        desc,
        modifier = Modifier.padding(horizontal = 12.dp),
        style = MaterialTheme.typography.body2.copy(
            fontSize = 13.sp
        )
    )
}
