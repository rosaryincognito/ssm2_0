package com.subaru.ssm2.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(

    onConnection: () -> Unit,

    onLiveData: () -> Unit,

    onDtc: () -> Unit,

    onLogging: () -> Unit,

    onSettings: () -> Unit,

    onAbout: () -> Unit

) {

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("Subaru SSM II")
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            Text(
                text = "Главное меню",
                style = MaterialTheme.typography.headlineSmall
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onConnection
            ) { Text("Подключение") }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onLiveData
            ) { Text("Параметры") }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDtc
            ) { Text("Ошибки ECU") }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onLogging
            ) { Text("Логи") }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSettings
            ) { Text("Настройки") }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onAbout
            ) { Text("О программе") }
        }
    }
}