package com.programmersbox.thundercounter

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = ColorScheme(isSystemInDarkTheme())
    ) {
        var buttonState by remember { mutableStateOf(ButtonState.Idle) }
        val time by produceState(key1 = buttonState, initialValue = 0f) {
            if (buttonState == ButtonState.Pressed) {
                value = 0f
            }
            while (buttonState == ButtonState.Pressed) {
                delay(1000)
                value += 1f
            }
        }

        val animatable = remember(buttonState) { Animatable(0f) }

        LaunchedEffect(time) {
            animatable.snapTo(0f)
            animatable.animateTo(1f, animationSpec = tween(1000))
        }

        val progressColors by remember {
            derivedStateOf { animatable.value % 2f == 0f }
        }

        @Composable
        fun progressColor(progressColors: Boolean = true) = if (progressColors)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surfaceVariant

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Thunder Counter") },
                )
            }
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                Text("seconds / 5 == miles away")
                Text("${time.roundToInt()}s / 5")

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(130.dp)
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        buttonState == ButtonState.Pressed,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            progress = { animatable.value },
                            modifier = Modifier.size(120.dp),
                            color = progressColor(!progressColors),
                            trackColor = progressColor(progressColors)
                        )
                    }

                    Text(
                        roundToDecimals(
                            number = animateFloatAsState(
                                time / 5
                            ).value,
                            decimals = 1
                        ),
                        fontSize = 60.sp
                    )
                }

                Text("miles away")

                Text("Release the button when you hear the thunder")
                CustomButton(
                    modifier = Modifier.pointerInput(buttonState) {
                        awaitPointerEventScope {
                            buttonState = if (buttonState == ButtonState.Pressed) {
                                waitForUpOrCancellation()
                                ButtonState.Idle
                            } else {
                                awaitFirstDown(false)
                                ButtonState.Pressed
                            }
                        }
                    }
                ) {
                    Text("Press and hold when you see the flash")
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val containerColor = colors.containerColor
    val contentColor = colors.contentColor
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
    ) {
        ProvideTextStyle(MaterialTheme.typography.labelLarge) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

private enum class ButtonState { Pressed, Idle }
