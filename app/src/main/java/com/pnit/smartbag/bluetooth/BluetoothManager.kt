package com.pnit.smartbag.bluetooth

import android.bluetooth.*
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT32
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8
import android.content.Context
import android.util.Log.v

class BluetoothManager(var dataListener: DataListener? = null,
                       var connectionListener: ConnectionListener? = null) {

    companion object {
        // SmartBag v1
        const val PROTOTYPE_ADDRESS = "78:DB:2F:13:EC:B4"

        private const val TAG = "BluetoothManager"
    }

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private var gatt: BluetoothGatt? = null

    var isConnected = false
        private set

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                v(TAG, "onConnected")
                isConnected = true
                connectionListener?.onConnected()
                gatt?.discoverServices()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            v(TAG, "onServicesDiscovered")
            gatt?.services?.forEach { service ->
                service.characteristics.forEach { characteristic ->
                    gatt.setCharacteristicNotification(characteristic, true)
                }
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            super.onCharacteristicChanged(gatt, characteristic)
            val value = characteristic?.getStringValue(0)
            v(TAG, "onCharacteristicChanged, payload = $value")
            if (value != null) {
                dataListener?.onData(value)
            }
        }
    }

    fun isBluetoothSupported() = bluetoothAdapter != null

    fun isBluetoothEnabled() = bluetoothAdapter.isEnabled

    fun getRemoteDevice(address: String): BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)

    fun connectTo(device: BluetoothDevice, context: Context) {
        this.gatt = device.connectGatt(context, true, gattCallback)
    }

    fun disconnect() {
        this.gatt?.disconnect()
        connectionListener?.onDisconnected()
        isConnected = false
    }

    fun onDestroy() {
        this.gatt?.close()
    }

}