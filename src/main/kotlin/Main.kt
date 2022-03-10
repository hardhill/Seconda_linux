// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay

fun main() = application {

    Window(onCloseRequest = ::exitApplication,
        title = "Seconda",
        resizable = false,
        state = rememberWindowState(position = WindowPosition(alignment = Alignment.Center), height = 400.dp, width = 640.dp)
    ) {
        App()
    }
}

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        LEDMatrixTimeCounter()
    }
}

@Composable
fun LEDMatrixTimeCounter() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var ledStyle by remember { mutableStateOf(LedMatrixStyle()) }

        Text(
            text = "LED Matrix Time Counter",
            modifier = Modifier.padding(bottom = 32.dp),
            style = TextStyle(
                color = Color.Gray,
                fontSize = 20.sp
            )
        )

        LedCounterDisplay(
            style = ledStyle,
            onReset = {
                ledStyle = LedMatrixStyle()
            }
        )

        Spacer(modifier = Modifier.padding(top = 32.dp))

        Text(
            text = "Current Time Display",
            modifier = Modifier.padding(bottom = 16.dp),
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 18.sp
            )
        )

        ClockDisplay()
    }
}

@Composable
fun LedCounterDisplay(
    style: LedMatrixStyle = LedMatrixStyle(),
    onReset: (() -> Unit)? = null
) {
    var number by remember { mutableStateOf(0) }
    var started by remember { mutableStateOf(false) }

    LaunchedEffect(started) {
        while (started) {
            number += 1
            if (number >= 1000) {
                number = 0
            }
            delay(1000)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LedMatrixDisplay(number.div(100), style)
        Spacer(modifier = Modifier.width(16.dp))

        LedMatrixDisplay(number.mod(100).div(10), style)
        Spacer(modifier = Modifier.width(16.dp))

        LedMatrixDisplay(number % 10, style)
        Spacer(modifier = Modifier.width(16.dp))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                started = !started
            },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = if (started) "Stop Counter" else "Start Counter")
        }

        Button(
            onClick = {
                number = (0..999).random()
            },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = "Randomize")
        }

        Button(
            onClick = {
                started = false
                number = 0
                onReset?.invoke()
            },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(text = "Reset")
        }
    }
}
