package com.sejun2.shared.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.SubcomposeAsyncImage
import com.example.pokemons.util.capitalizeFirstLowercaseRest
import com.example.pokemons.util.toPokedexIndex
import com.sejun2.shared.data.repository.PokemonRepository
import com.sejun2.shared.domain.model.PokemonDetail
import com.sejun2.shared.domain.model.Stats
import com.sejun2.shared.domain.usecase.GetPokemonDetailUseCase
import com.sejun2.shared.presentation.viewmodel.PokemonDetailUiState
import com.sejun2.shared.presentation.viewmodel.PokemonDetailViewModel
import com.sejun2.shared.presentation.widget.HeadTitleView
import com.sejun2.shared.presentation.widget.PokemonDescriptionView
import com.sejun2.shared.presentation.widget.PokemonNavigationView
import com.sejun2.shared.presentation.widget.PokemonPhysicsView
import com.sejun2.shared.presentation.widget.PokemonType
import com.sejun2.shared.presentation.widget.PokemonTypeView
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.sejun2.project.data.network.api.PokemonApiService
import pokedexkmp.composeapp.generated.resources.Res
import pokedexkmp.composeapp.generated.resources.ic_pokedex_logo

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    onNavigateUp: () -> Boolean,
    pokemonDetailViewModel: PokemonDetailViewModel =
        PokemonDetailViewModel(
            getPokemonDetailUseCase = GetPokemonDetailUseCase(
                pokemonRepository = PokemonRepository(
                    pokemonApiService = PokemonApiService()
                )
            )
        ),
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pokemonDetailViewModel.setPokemonIndex(pokemonIndex = pokemonId)
        pokemonDetailViewModel.errorEvent.collect { errorMsg ->
            scope.launch {
                SnackbarHostState().showSnackbar(
                    message = errorMsg,
                    duration = SnackbarDuration.Short,
                )
            }
        }
    }

    PokemonDetailView(onNavigateUp, pokemonDetailViewModel = pokemonDetailViewModel)
}

@Composable
fun PokemonDetailView(
    onNavigateUp: () -> Boolean,
    pokemonDetailViewModel: PokemonDetailViewModel,
) {
    val uiState = pokemonDetailViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize().navigationBarsPadding(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val scope = rememberCoroutineScope()

            when (val state = uiState.value) {
                is PokemonDetailUiState.Success -> {
                    val backgroundColor =
                        state.data.first { it.index == pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value }.types[0].color

                    BoxWithConstraints {
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxSize()
                                .animatedBackgroundColor(color = backgroundColor)
                        ) {
                            val guideline =
                                createGuidelineFromTop(((this@BoxWithConstraints.maxHeight).value * 0.3).dp)

                            val (backgroundFieldViewRef, navViewRef, contentViewRef) = createRefs()

                            BackgroundFieldView(
                                Modifier.constrainAs(backgroundFieldViewRef) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(guideline)

                                    height = Dimension.fillToConstraints
                                },
                                state.data.first { it.index == pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value }.types[0]
                            )

                            PokemonNavigationView(
                                Modifier
                                    .constrainAs(navViewRef) {
                                        top.linkTo(guideline)
                                        bottom.linkTo(guideline)

                                        width = Dimension.matchParent
                                        height = Dimension.wrapContent
                                    }
                                    .padding(bottom = 16.dp)
                                    .zIndex(2f),
                                pokemonList = state.data,
                                selectedPokemonIndex = pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value,
                                onPageMoved = {
                                    scope.launch {
                                        pokemonDetailViewModel.setPokemonIndex(it)
                                    }
                                }
                            )
                            ContentCardView(
                                Modifier
                                    .constrainAs(contentViewRef) {
                                        top.linkTo(guideline)
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)

                                        width = Dimension.fillToConstraints
                                        height = Dimension.fillToConstraints
                                    }, navHeight = 180.dp,
                                pokemonDetail = state.data.first { it.index == pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value }
                            )
                        }
                    }

                }

                is PokemonDetailUiState.Loading ->
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                is PokemonDetailUiState.Initial -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                is PokemonDetailUiState.Error ->
                    Text(state.msg)
            }

            Header(
                onNavigateUp = onNavigateUp,
                uiState = pokemonDetailViewModel.uiState.collectAsState().value,
                selectedPokemonIndex = pokemonDetailViewModel.selectedPokemonIndex.collectAsState().value
            )
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Boolean,
    uiState: PokemonDetailUiState,
    selectedPokemonIndex: Int,
) {
    Row(
        modifier = modifier.padding(end = 12.dp, start = 4.dp, top = 8.dp, bottom = 8.dp).statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onNavigateUp() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = White,
                contentDescription = "back_button",
                modifier = Modifier
                    .padding(12.dp)

            )
        }
        when (uiState) {
            is PokemonDetailUiState.Success -> {
                Text(
                    uiState.data.first { it.index == selectedPokemonIndex }.name.capitalizeFirstLowercaseRest(),
                    style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.W900,
                        fontSize = 18.sp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "#${
                        uiState.data.first { it.index == selectedPokemonIndex }.index.toString()
                            .toPokedexIndex()
                    }", style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.W900,
                        fontSize = 12.sp
                    )
                )
            }

            else -> {
            }
        }
    }

}

fun Modifier.animatedBackgroundColor(color: Color): Modifier = composed() {
    val targetColor = remember { mutableStateOf(White) }
    val backgroundColor by animateColorAsState(
        targetValue = targetColor.value,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing,
            delayMillis = 100,
        ),
        label = "background_color"
    )

    LaunchedEffect(color) {
        targetColor.value = color
    }

    this.background(color = backgroundColor)
}

@Composable
fun BackgroundFieldView(modifier: Modifier, type: PokemonType) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse,
        ), label = ""
    )

    Box(
        modifier = modifier
            .animatedBackgroundColor(type.color)
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Image(
            painter = painterResource(
                resource = Res.drawable.ic_pokedex_logo
            ),
            contentDescription = "logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(240.dp)
                .scale(scale),
            alpha = 0.2f
        )
    }
}

@Composable
fun PokemonImageView(modifier: Modifier = Modifier, pokemonDetail: PokemonDetail) {
    var completed by remember {
        mutableStateOf<Boolean>(false)
    }

    val animWidth by animateFloatAsState(
        targetValue = if (completed) 200f else 350f, label = "anim_val_image_width",
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutBack),
            repeatMode = RepeatMode.Restart,
        ), label = "anim_val_image_rotate_angle"
    )

    SubcomposeAsyncImage(
        modifier = modifier,
        model = pokemonDetail.imageSrc,
        contentDescription = "image",
        alignment = Alignment.Center,
        onSuccess = {
            completed = true
        },
        success = {
            Image(
                painter = it.painter,
                contentDescription = "Loading",
                modifier = Modifier
                    .width(animWidth.dp)
                    .height(animWidth.dp)
                    .zIndex(1f)
            )
        },
    )

}


@Composable
fun ContentCardView(modifier: Modifier, navHeight: Dp, pokemonDetail: PokemonDetail) {
    val verticalScrollState = rememberScrollState()

    Box(
        modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = MaterialTheme.colors.background)
            .padding(top = navHeight / 2)
            .verticalScroll(state = verticalScrollState)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PokemonTypeView(types = pokemonDetail.types)
            HeadTitleView("About", color = pokemonDetail.types.first().color)
            PokemonPhysicsView(pokemonDetail = pokemonDetail)
            PokemonDescriptionView(pokemonDetail.description)
            HeadTitleView("Base Stats", color = pokemonDetail.types.first().color)
            PokemonStatsView(pokemonDetail = pokemonDetail)
        }
    }
}

@Composable
fun PokemonStatsView(pokemonDetail: PokemonDetail) {
    val color = pokemonDetail.types.get(0).color
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        for (stat in pokemonDetail.statsList) {
            key(pokemonDetail.index) {
                PokemonStatItem(stat, color)
            }
        }
    }
}

@Stable
@Composable
fun PokemonStatItem(stats: Stats, color: Color, maxStat: Int = 250) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(stats) {
        animatedProgress.snapTo(0f)  // 즉시 0으로 설정
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 700,
                easing = EaseInOut,
                delayMillis = 350
            )
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            stats.toPrettyName(),
            modifier = Modifier
                .width(40.dp)
                .padding(end = 8.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.subtitle1.copy(
                color = color, fontWeight = FontWeight.Bold
            )
        )
        Box(
            modifier = Modifier
                .height(22.dp)
                .width(1.dp)
                .background(color = Color.Gray)
                .padding(12.dp)
        )
        Text(
            stats.value.toString().toPokedexIndex(),
            modifier = Modifier.width(36.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.Normal
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(
                    color = color.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(percent = 100)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((stats.value.div(maxStat.toFloat())) * animatedProgress.value)
                    .height(4.dp)
                    .background(color = color, shape = RoundedCornerShape(percent = 100))
            )
        }
    }
}
