package com.nolovr.core.hfp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHeadset
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


class MainActivity() : AppCompatActivity() {

    companion object {
        const val TAG = "_MainActivity"
    }

    private var context: Context = this;

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var hfpProfile: BluetoothHeadset? = null


    var audioManager: AudioManager? = null


    private val listener: BluetoothProfile.ServiceListener =
        object : BluetoothProfile.ServiceListener {
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                if (profile == BluetoothProfile.HEADSET) {
                    // HFP连接
                    val bluetoothDevices = if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.e(TAG, "onServiceConnected: ")
                        return
                    } else {
                        bluetoothAdapter?.bondedDevices
                    }


//                    val pairedDevice =
//                        bluetoothDevices?.firstOrNull { device -> device.name.contains("Headset") }
//                    hfpProfile?.connect(pairedDevice)
                }
            }

            override fun onServiceDisconnected(profile: Int) {
                if (profile == BluetoothProfile.HEADSET) {
                    // HFP断开
//                    hfpProfile.disconnect()
                }
            }
        }


    private val phoneStateListener: PhoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, incomingNumber: String?) {
            when (state) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    // 电话响铃
                }

                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    // 电话接听
                }

                TelephonyManager.CALL_STATE_IDLE -> {
                    // 电话挂断
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 蓝牙连接
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


//        val hfp: Boolean =
//            bluetoothAdapter.getProfileProxy(context, listener, BluetoothProfile.HEADSET);

//        hfpProfile = BluetoothHeadset(context, listener);


        // 监听通话状态
        val telephonyManager: TelephonyManager? =
            getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager?.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);


        // 声音切换
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // 设置音频路由为蓝牙耳机
        audioManager?.mode = AudioManager.MODE_IN_CALL
        audioManager?.startBluetoothSco()

        // 停止音频传输
        audioManager?.stopBluetoothSco()


    }

    override fun onDestroy() {
        super.onDestroy()
    }


    // 系统api
//    fun getHFPProxy(context: Context?) {
//        BluetoothAdapter.getDefaultAdapter()
//            .getProfileProxy(context, object : BluetoothProfile.ServiceListener {
//                override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
//                    val bluetoothHeadsetClient: BluetoothHeadsetClient =
//                        proxy as BluetoothHeadsetClient
//                    //此处拿到的代理实例为BluetoothHeadsetClient。
//                }
//
//                override fun onServiceDisconnected(profile: Int) {}
//            }, BluetoothProfile.HEADSET_CLIENT)
//    }


    fun connectHfp(hfp: BluetoothHeadset?, device: BluetoothDevice?): Boolean {
        var ret = false
        var connect: Method? = null
        try {
            connect =
                BluetoothHeadset::class.java.getDeclaredMethod(
                    "connect",
                    BluetoothDevice::class.java
                )
            connect.isAccessible = true
            ret = connect.invoke(hfp, device) as Boolean
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return ret
    }


}