package com.subaru.ssm2.ui.connection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.subaru.ssm2.bluetooth.ConnectionStatus
import com.subaru.ssm2.bluetooth.DeviceInfo
import com.subaru.ssm2.viewmodel.ConnectionViewModel
import androidx.compose.runtime.LaunchedEffect
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionScreen(
    viewModel: ConnectionViewModel,
    onConnectClick: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.connected) {

        if (state.connected) {
            onConnectClick()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkBluetooth()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Подключение")
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
                text = when (state.status) {
                    ConnectionStatus.DISCONNECTED -> "Статус: отключено"
                    ConnectionStatus.SCANNING -> "Статус: поиск устройств..."
                    ConnectionStatus.CONNECTING -> "Статус: подключение..."
                    ConnectionStatus.CONNECTED -> "Статус: подключено"
                    ConnectionStatus.ERROR -> "Статус: ошибка"
                },

                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = state.message,
                style = MaterialTheme.typography.bodyMedium
            )
            state.selectedDevice?.let { device ->

                Text(
                    text = "Устройство:\n${device.name}\n${device.address}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.scan()
                }
            ) {
                Text("Поиск устройств")
            }
            if (state.connected) {

                Button(
                    modifier = Modifier.fillMaxWidth(),

                    onClick = {
                        viewModel.disconnect()
                    }

                ) {

                    Text("Отключиться")
                }
            }

            LazyColumn {

                items(state.devices) { device ->

                    DeviceCard(
                        device = device,
                        onClick = {
                            viewModel.connect(device)
                            }
                    )

                }

            }

        }

    }

}

@Composable
private fun DeviceCard(
    device: DeviceInfo,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            },

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = device.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = device.address,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}