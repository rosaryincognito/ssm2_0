package com.subaru.ssm2

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.subaru.ssm2.ui.SubaruApp
import com.subaru.ssm2.ui.theme.SubaruTheme

class MainActivity : ComponentActivity() {


    private val bluetoothPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            startApplication()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestBluetoothPermissions()

    }


    private fun requestBluetoothPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            bluetoothPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )

        } else {

            startApplication()

        }

    }


    private fun startApplication() {

        setContent {

            SubaruTheme {

                SubaruApp()

            }
        }
    }
}