package rl.com.deviceinfo

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.hardware.usb.UsbDevice.getDeviceId
import android.content.Context.TELEPHONY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.telephony.TelephonyManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import kotlin.text.Typography.greater
import android.bluetooth.BluetoothAdapter




class MainActivity : AppCompatActivity() {
    private lateinit var imeiText: TextView
    private lateinit var androididText: TextView
    private lateinit var wifiText: TextView
    private lateinit var bluettohText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imeiText = findViewById(R.id.text_imei3)
        imeiText.setText(getUniqueTelephony())

        androididText = findViewById(R.id.text_android_id)
        androididText.setText(getAndroidID())

        wifiText = findViewById(R.id.text_wifi_id)
        wifiText.setText(getWIFIid())

        bluettohText = findViewById(R.id.text_bluetooth_id)
        bluettohText.setText(getbluetoothID())

    }

    private fun getAndroidID(): String {
        var id: String
        try {
            id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
        } catch (e: Exception) {
            id = "Error while getting Android ID"
        }
        return id
    }

    @SuppressLint("MissingPermission")
    private fun getUniqueTelephony(): String {
        val telephonyManager: TelephonyManager
        var id: String
        try {
            telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            //  getDeviceId() returns the unique device ID.
            // For example,the IMEI for GSM and the MEID or ESN for CDMA phones.
            id = telephonyManager.getDeviceId()
            //  getSubscriberId() returns the unique subscriber ID,
            //  For example, the IMSI for a GSM phone.
            if (id == null) {
                id = telephonyManager.getSubscriberId()
            }
        } catch (e: Exception) {
            id = "Error while getting Unique Telephony Number"
        }
        return id
    }

    @SuppressLint("MissingPermission")
    private fun getWIFIid(): String {
        var id: String
        try {
            var manager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            var info = manager.connectionInfo
            id = info.macAddress
        } catch (e: Exception) {
            id = "Error while getting WiFi macAddress"
        }
        return id;

        /*
        Please refer to Android 6.0 Changes.

        To provide users with greater data protection, starting in this release, Android removes programmatic access
        to the device’s local hardware identifier for apps using the Wi-Fi and Bluetooth APIs.
        The WifiInfo.getMacAddress() and the BluetoothAdapter.getAddress() methods now return a
        constant value of 02:00:00:00:00:00.

        To access the hardware identifiers of nearby external devices via Bluetooth and Wi-Fi scans,
        your app must now have the ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION permissions.
        */
    }

    private fun getbluetoothID(): String {
        var id: String
        try {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            id = adapter.address;
        } catch (e: Exception) {
            id = "Error while getting Bluetooth ID"
        }
        return id
    }


}