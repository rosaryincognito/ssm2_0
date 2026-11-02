package com.subaru.ssm2.bluetooth

sealed class BluetoothState {

    object Idle : BluetoothState()

    object Scanning : BluetoothState()

    object Connecting : BluetoothState()

    object Connected : BluetoothState()

    data class Error(
        val message: String
    ) : BluetoothState()
}