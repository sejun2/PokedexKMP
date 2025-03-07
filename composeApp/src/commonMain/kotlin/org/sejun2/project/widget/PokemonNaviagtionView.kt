package com.sejun2.shared.presentation.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.SubcomposeAsyncImage
import com.sejun2.shared.domain.model.PokemonDetail
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@Composable
fun PokemonNavigationView(
    modifier: Modifier,
    pokemonList: List<PokemonDetail>,
    selectedPokemonIndex: Int,
    onPageMoved: (Int) -> Unit,
) {
    println("PokemonNavigationView  $pokemonList $selectedPokemonIndex")

    val pagerState = rememberPagerState(
        initialPage = if (selectedPokemonIndex == 1) 0 else 1,
        initialPageOffsetFraction = 0.0f,
        pageCount = {
            pokemonList.size
        },
    )

    LaunchedEffect(pagerState.currentPage) {
        onPageMoved(pokemonList.get(pagerState.currentPage).index)
    }
    val scope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = ((450 - 24) / 3).dp),
        key = { page -> pokemonList[page].index },
        verticalAlignment = Alignment.CenterVertically,
    ) { page ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp)
                .graphicsLayer {
                    // 현재 페이지와의 거리 계산
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // 선택되지 않은 아이템은 작게, 흐리게 표시
                    scaleX = 1f - (pageOffset * 0.5f)
                    scaleY = 1f - (pageOffset * 0.5f)
                    alpha = 1f - (pageOffset * 0.6f)
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    scope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                }
        ) {
            SubcomposeAsyncImage(
                model = pokemonList.get(page).imageSrc,
                contentDescription = "image",
                alignment = Alignment.Center,
                success = {
                    Image(
                        painter = it.painter,
                        contentDescription = "Loading",
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .zIndex(1f)
                    )
                },
            )
        }
    }
}