package com.subaru.ssm2.bluetooth

data class BluetoothState(
    val enabled: Boolean = false,
    val scanning: Boolean = false,
    val connected: Boolean = false,
    val devices: List<DeviceInfo> = emptyList(),
    val selectedDevice: DeviceInfo? = null
)