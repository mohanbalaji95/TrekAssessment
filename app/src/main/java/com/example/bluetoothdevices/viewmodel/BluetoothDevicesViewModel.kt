package com.example.bluetoothdevices.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.bluetoothdevices.model.Device

class BluetoothDevicesViewModel : ViewModel() {

    val data = MutableLiveData<Device>()
}