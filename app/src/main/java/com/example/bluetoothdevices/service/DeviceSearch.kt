package com.example.bluetoothdevices.service

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.provider.ContactsContract
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log

class DeviceSearch : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action.equals("StartForeground")) {
            startForegroundService()
            val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val r = Runnable {
                val bReciever = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        val action = intent.action
                        if (BluetoothDevice.ACTION_FOUND == action) {
                            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                            Log.e("MainActivity","Found")
                            Log.e("MainActivity",device.name.toString())
                            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE)
                            Log.e("MainActivity RSSI", rssi.toString())
                        }
                    }
                }
                val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                applicationContext.registerReceiver(bReciever, filter)
                //startService()
                bluetoothAdapter.startDiscovery()
                //bluetoothAdapter!!.startDiscovery()
                stopForeground(true)
                stopSelf()
            }
            val queryThread = Thread(r)
            queryThread.start()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    fun startForegroundService() {
        Log.d("ContactBDay", "Start foreground service.")

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentText("App is running")
            .setPriority(Notification.PRIORITY_HIGH)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}