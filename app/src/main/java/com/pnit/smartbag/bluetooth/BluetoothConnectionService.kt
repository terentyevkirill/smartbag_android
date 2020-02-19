package com.pnit.smartbag.bluetooth

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log.v
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.pnit.smartbag.R


class BluetoothConnectionService : Service() {

    companion object {
        const val ACTION_DEVICE_CONNECTED = "action_device_connected"
        const val ACTION_DEVICE_DISCONNECTED = "action_device_disconnected"
        const val ACTION_STEPS_SENT = "action_steps_sent"
        const val ACTION_SERVICE_CREATED = "action_service_created"
        const val ACTION_SERVICE_DESTROYED = "action_service_destroyed"

        private const val FOREGROUND_ID = 6
        private const val NOTIFICATION_CHANNEL_NAME = "Bluetooth Connection Service"
        private const val NOTIFICATION_CHANNEL_ID = "bt_connection_service"

        var isDeviceConnected = false
            private set
    }

    // Broadcast steps?:
    private val dataListener = object : DataListener {
        override fun onData(data: String) {
//            broadcastCurrentCubeFace(face)
            v("BTConnectionService", "onData: $data")
        }
    }

    private val connectionStateListener = object : ConnectionListener {

        override fun onConnected() {
            broadcastEvent(ACTION_DEVICE_CONNECTED)
            isDeviceConnected = true
            v("BTConnectionService", "onConnected")
        }

        override fun onDisconnected() {
            broadcastEvent(ACTION_DEVICE_DISCONNECTED)
            isDeviceConnected = false
            v("BTConnectionService", "onDisconnected")
        }
    }

    private val bluetoothManager = BluetoothManager(dataListener, connectionStateListener)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        v("BTConnectionService", "onCreate()")
        broadcastEvent(ACTION_SERVICE_CREATED)
        try {
            val remoteDevice = bluetoothManager.getRemoteDevice(BluetoothManager.PROTOTYPE_ADDRESS)
            bluetoothManager.connectTo(remoteDevice, this)
        } catch (e: RuntimeException) {
        } finally {
            startForeground(FOREGROUND_ID, createNotification())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        broadcastEvent(ACTION_SERVICE_DESTROYED)
        if (bluetoothManager.isConnected) {
            bluetoothManager.disconnect()
        }
        bluetoothManager.onDestroy()
    }

    private fun broadcastEvent(event: String) {
        val intent = Intent(event)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

//    private fun broadcastCurrentCubeFace(face: Int) {
//        val intent = Intent(ACTION_CUBE_FACE_DATA_SENT)
//        intent.putExtra(EXTRA_CUBE_FACE, face)
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//    }



    private fun createNotification(): Notification {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME)
        } else NOTIFICATION_CHANNEL_ID

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.connection_notification_title))
            .setSmallIcon(R.drawable.ic_bluetooth_black_24dp)
            .build()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, channelName: String): String {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel =
            NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationChannel.setSound(null, null)
        notificationChannel.enableVibration(false)
        notificationManager.createNotificationChannel(notificationChannel)
        return id
    }

    override fun onBind(intent: Intent?): IBinder? = null
}