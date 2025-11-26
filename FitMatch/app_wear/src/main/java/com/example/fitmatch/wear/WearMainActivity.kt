package com.example.fitmatch.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices.SMALL_ROUND
import com.example.fitmatch.wear.presentation.screens.WearMainScreen
import com.example.fitmatch.wear.presentation.theme.FitMatchTheme

class WearMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    FitMatchTheme {
        WearMainScreen()
    }
}

@Preview(device = SMALL_ROUND, showSystemUi = true)
@Composable
fun PreviewWearApp() {
    WearApp()
}