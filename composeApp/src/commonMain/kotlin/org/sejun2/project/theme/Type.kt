package com.example.pokemons.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import pokedexkmp.composeapp.generated.resources.Res
import pokedexkmp.composeapp.generated.resources.poppins_bold
import pokedexkmp.composeapp.generated.resources.poppins_medium
import pokedexkmp.composeapp.generated.resources.poppins_regular
import pokedexkmp.composeapp.generated.resources.poppins_semi_bold


val Typography
    @Composable
    get() = Typography().let {
        val poppins = FontFamily(
            Font(
                Res.font.poppins_bold,
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            ),
            Font(
                Res.font.poppins_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            ),
            Font(
                Res.font.poppins_medium,
                weight = FontWeight.Medium,
                style = FontStyle.Normal
            ),
            Font(
                Res.font.poppins_semi_bold,
                weight = FontWeight.SemiBold,
                style = FontStyle.Normal
            )
        )
        it.copy(
            body1 = it.body1.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            body2 = it.body2.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            h1 = it.h1.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            h2 = it.h2.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            h3 = it.h3.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            h4 = it.h4.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            h5 = it.h5.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            h6 = it.h6.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            ),
            subtitle1 = it.subtitle1.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.15.sp
            ),
            subtitle2 = it.subtitle2.copy(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.15.sp
            ),
        )
    }
