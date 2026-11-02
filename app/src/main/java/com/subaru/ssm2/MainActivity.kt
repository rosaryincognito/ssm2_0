package com.subaru.ssm2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.subaru.ssm2.ui.SubaruApp
import com.subaru.ssm2.ui.theme.SubaruTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SubaruTheme {
                SubaruApp()
            }
        }
    }
}