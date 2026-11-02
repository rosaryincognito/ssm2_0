package com.subaru.ssm2.bluetooth

import android.content.Context

object BluetoothRepository {

    private var manager: BluetoothManager? = null

    fun get(context: Context): BluetoothManager {

        if (manager == null) {
            manager = BluetoothManager(context.applicationContext)
        }

        return manager!!
    }
}