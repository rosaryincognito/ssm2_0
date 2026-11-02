package com.subaru.ssm2.bluetooth


import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BluetoothManager(
    private val context: Context
) {

    private val adapter: BluetoothAdapter? =
        BluetoothAdapter.getDefaultAdapter()

    private var socket: BluetoothSocket? = null

    private val _connectionStatus =
        MutableStateFlow(ConnectionStatus.DISCONNECTED)

    val connectionStatus: StateFlow<ConnectionStatus> =
        _connectionStatus.asStateFlow()

    companion object {

        private val SPP_UUID =
            UUID.fromString(
                "00001101-0000-1000-8000-00805F9B34FB"
            )
    }

    fun isBluetoothEnabled(): Boolean {

        return adapter?.isEnabled == true
    }

    @SuppressLint("MissingPermission")
    suspend fun scan(): List<DeviceInfo> =
        withContext(Dispatchers.IO) {

            _connectionStatus.value = ConnectionStatus.SCANNING

            val bluetoothAdapter =
                adapter
                    ?: return@withContext emptyList()

            val devices =
                bluetoothAdapter
                    .bondedDevices
                    .map {

                        DeviceInfo(
                            name = it.name ?: "Unknown",
                            address = it.address
                        )
                    }

            _connectionStatus.value = ConnectionStatus.DISCONNECTED

            devices
        }

    @SuppressLint("MissingPermission")
    suspend fun connect(
        device: DeviceInfo
    ) =
        withContext(Dispatchers.IO) {


            val bluetoothDevice: BluetoothDevice =
                adapter
                    ?.getRemoteDevice(device.address)
                    ?: throw Exception(
                        "Device not found"
                    )

            socket =
                bluetoothDevice
                    .createRfcommSocketToServiceRecord(
                        SPP_UUID
                    )

            try {

                _connectionStatus.value = ConnectionStatus.CONNECTING

                socket?.connect()

                _connectionStatus.value = ConnectionStatus.CONNECTED

            } catch (e: Exception) {

                socket?.close()
                socket = null

                _connectionStatus.value = ConnectionStatus.ERROR

                throw e
            }
        }

    suspend fun disconnect() =
        withContext(Dispatchers.IO) {

            socket?.close()

            socket = null

            _connectionStatus.value =
                ConnectionStatus.DISCONNECTED
        }

    fun isConnected(): Boolean {

        return socket?.isConnected == true
    }
}