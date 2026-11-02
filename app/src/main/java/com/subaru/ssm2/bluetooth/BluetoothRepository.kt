package com.subaru.ssm2.bluetooth

interface BluetoothRepository {
    suspend fun scan()
    suspend fun connect(device: DeviceInfo)
    suspend fun disconnect()
}