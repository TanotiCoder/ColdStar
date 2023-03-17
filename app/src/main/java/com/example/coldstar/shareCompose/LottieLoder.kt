package com.example.coldstar.shareCompose

import androidx.annotation.RawRes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun LoopReverseLottieLoading(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    @RawRes lottieFile: Int,
    enablePath: Boolean = true
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottieFile))
    val reverse = remember { mutableStateOf(false) }
    val anim = rememberLottieAnimatable()

    if (reverse.value.not()) {
        LaunchedEffect(key1 = composition) {
            anim.animate(composition = composition, speed = 1f)
            reverse.value = true
        }
    }

    if (reverse.value) {
        LaunchedEffect(key1 = composition) {
            anim.animate(composition = composition, speed = 1f)
            reverse.value = false
        }
    }

    LottieAnimation(
        composition = composition,
        anim.value,
        modifier,
        enableMergePaths = remember { enablePath },
        alignment = alignment
    )
}
