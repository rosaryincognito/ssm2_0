package com.subaru.ssm2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.subaru.ssm2.bluetooth.BluetoothManager
import com.subaru.ssm2.bluetooth.BluetoothRepository
import com.subaru.ssm2.bluetooth.ConnectionStatus
import com.subaru.ssm2.bluetooth.DeviceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
class ConnectionViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val bluetooth by lazy {

        BluetoothRepository.get(getApplication())

    }

    private val _state =
        MutableStateFlow(ConnectionUiState())

    val state: StateFlow<ConnectionUiState> =
        _state.asStateFlow()

    init {
        observeConnectionStatus()
        checkBluetooth()
    }
    fun checkBluetooth() {

        val enabled =
            bluetooth.isBluetoothEnabled()

        _state.value =
            _state.value.copy(

                bluetoothEnabled = enabled,

                status =
                    if (enabled)
                        ConnectionStatus.DISCONNECTED
                    else
                        ConnectionStatus.ERROR,

                message =
                    if (enabled)
                        "Bluetooth готов"
                    else
                        "Bluetooth выключен"
            )
    }
    fun scan() {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(

                        scanning = true,

                        status = ConnectionStatus.SCANNING,
                        message = "Поиск устройств..."
                    )

                val devices =
                    bluetooth.scan()



                _state.value =
                    _state.value.copy(

                        devices = devices,

                        scanning = false,

                        status = ConnectionStatus.DISCONNECTED,
                        message = "Найдено устройств: ${devices.size}"
                    )


            } catch (e: Exception) {


                _state.value =
                    _state.value.copy(

                        scanning = false,

                        status =
                            ConnectionStatus.ERROR
                    )
            }
        }
    }

    fun connect(device: DeviceInfo) {

        viewModelScope.launch {

            try {
                _state.value =
                    _state.value.copy(

                        status = ConnectionStatus.CONNECTING,

                        message =
                            "Подключение к ${device.name}"
                    )

                bluetooth.connect(device)

                _state.value =
                    _state.value.copy(

                        connected = true,

                        selectedDevice = device,

                        status = ConnectionStatus.CONNECTED,

                        message =
                            "Подключено: ${device.name}"
                    )

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        status = ConnectionStatus.ERROR,
                        message = e.message ?: "Ошибка подключения"
                    )
            }
        }
    }
    fun disconnect() {

        viewModelScope.launch {

            try {

                bluetooth.disconnect()

                _state.value =
                    _state.value.copy(

                        connected = false,

                        selectedDevice = null,

                        status = ConnectionStatus.DISCONNECTED,

                        message = "Устройство отключено"
                    )


            } catch (e: Exception) {


                _state.value =
                    _state.value.copy(

                        status = ConnectionStatus.ERROR,

                        message =
                            e.message ?: "Ошибка отключения"
                    )
            }
        }
    }
    private fun observeConnectionStatus() {

        viewModelScope.launch {

            bluetooth.connectionStatus.collect { status ->

                _state.value = _state.value.copy(
                    status = status,
                    connected = status == ConnectionStatus.CONNECTED
                )
            }
        }
    }
}