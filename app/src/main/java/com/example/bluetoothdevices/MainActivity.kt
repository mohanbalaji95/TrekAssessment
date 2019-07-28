package com.example.bluetoothdevices

import android.app.PendingIntent.getActivity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.content.IntentFilter
import com.example.bluetoothdevices.service.DeviceSearch


class MainActivity : AppCompatActivity() {

    val REQUEST_ENABLE_BT = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter == null)
        {
            // Bluetooth Doesn't support
        }
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
        else
        {

        }
        val bReciever = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (BluetoothDevice.ACTION_FOUND == action) {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    Log.e("MainActivity","Found")
                    if(device.name == null)
                    {
                        Log.e("MainActivity", "No device name")
                    }
                    else
                    Log.e("MainActivity", device.name)
                    Log.e("MainActivity", device.address)
                    val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE)
                    Log.e("MainActivity RSSI", rssi.toString())
                }
            }
        }
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        applicationContext.registerReceiver(bReciever, filter)
        //startService()
        bluetoothAdapter!!.startDiscovery()

    }

    fun startService()
    {
        val intent = Intent(this, DeviceSearch::class.java)
        intent.setAction("StartForeground")
        startService(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ENABLE_BT)
        {
            if(requestCode == 0)
            {

            }
            else
            {

            }
        }
    }
}
