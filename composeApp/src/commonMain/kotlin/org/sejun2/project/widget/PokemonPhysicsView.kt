package com.sejun2.shared.presentation.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.sejun2.shared.domain.model.PokemonDetail
import org.jetbrains.compose.resources.painterResource
import pokedexkmp.composeapp.generated.resources.Res
import pokedexkmp.composeapp.generated.resources.compose_multiplatform
import pokedexkmp.composeapp.generated.resources.ic_straighten
import pokedexkmp.composeapp.generated.resources.ic_weight

@Composable
fun PokemonPhysicsView(modifier: Modifier = Modifier, pokemonDetail: PokemonDetail) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_weight),
                    contentDescription = "image_weight",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "${pokemonDetail.weight / 10} kg",
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Weight",
                style = MaterialTheme.typography.subtitle1.copy(color = Gray)
            )
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(50.dp)
                .background(color = Color.Gray.copy(alpha = 0.5f))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_straighten),
                    contentDescription = "image_height",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "${(pokemonDetail.height / 10)} m",
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Height",
                style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
            )
        }
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(50.dp)
                .background(color = Color.Gray.copy(alpha = 0.5f))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.weight(1f)
        ) {
            Text(pokemonDetail.toPrettyMoves(), style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Moves",
                style = MaterialTheme.typography.subtitle1.copy(color = Gray)
            )
        }
    }
}
