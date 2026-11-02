package com.subaru.ssm2.viewmodel

import androidx.lifecycle.ViewModel
import com.subaru.ssm2.bluetooth.BluetoothManager

class ConnectionViewModel : ViewModel() {
    private val bluetooth = BluetoothManager()
}