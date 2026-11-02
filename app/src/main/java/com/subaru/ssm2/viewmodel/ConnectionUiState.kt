package com.subaru.ssm2.viewmodel

import com.subaru.ssm2.bluetooth.ConnectionStatus
import com.subaru.ssm2.bluetooth.DeviceInfo


data class ConnectionUiState(

    val bluetoothEnabled: Boolean = false,

    val scanning: Boolean = false,

    val connected: Boolean = false,

    val devices: List<DeviceInfo> = emptyList(),

    val selectedDevice: DeviceInfo? = null,

    val status: ConnectionStatus = ConnectionStatus.DISCONNECTED,

    val message: String = "Bluetooth не проверен"
)