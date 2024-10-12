package com.netcore.smarttechdemo

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.IntentFilter
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.netcore.android.Smartech
import com.netcore.android.logger.SMTDebugLevel
import com.netcore.android.smartechpush.SmartPush
import com.netcore.android.smartechpush.notification.SMTNotificationOptions
import java.lang.ref.WeakReference

class MainApplication:Application() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        //initialise the sdk
        Smartech.getInstance(WeakReference(applicationContext)).initializeSdk(this)
        //Enabling Smartech logs for testing.
        Smartech.getInstance(WeakReference(this)).setDebugLevel(9)
        //track app and install
        Smartech.getInstance(WeakReference(this)).trackAppInstallUpdateBySmartech()


        //firebase token fetch



        fetchFcmToken()

        val deeplinkReceiver = DeeplinkReceiver()
        val filter = IntentFilter("com.smartech.EVENT_PN_INBOX_CLICK")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            this.registerReceiver(deeplinkReceiver, filter, RECEIVER_EXPORTED)
        } else {
            this.registerReceiver(deeplinkReceiver, filter)
        }






        val options = SMTNotificationOptions(this)
        options.brandLogo = "logo"//e.g.logo is sample name for brand logo
        options.largeIcon = "icon_nofification"//e.g.ic_notification is sample name for large icon
        options.smallIcon = "ic_arrow_left"//e.g.ic_action_play is sample name for icon
        options.smallIconTransparent = "ic_arrow_left"//e.g.ic_action_play is sample name for transparent small icon
        options.transparentIconBgColor = "#FF0000"
        options.placeHolderIcon = "ic_notification"//e.g.ic_notification is sample name for placeholder icon
        SmartPush.getInstance(WeakReference(this)).setNotificationOptions(options)
    }

    private fun fetchFcmToken() {
        try {

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful&&!TextUtils.isEmpty(task.getResult())) {
                    val smartTech = SmartPush.getInstance(WeakReference(this))
                    var fcmToken = task.getResult()
                    var currentToken = smartTech.getDevicePushToken()
                    Log.i("TOKEN", "FCM Instance Id Token: $fcmToken")

                    Log.i("TOKEN", "Current FCM Token:$currentToken")

                    if (TextUtils.isEmpty(currentToken)){
                        smartTech.setDevicePushToken(currentToken)
                    }else if (currentToken.equals(fcmToken)){
                        smartTech.setDevicePushToken(fcmToken)
                        Log.i("TOKEN", "New token set: $fcmToken")
                    }else {
                        Log.i("TOKEN", "Both tokens are same.")

                    }

                }
                else{
                    Log.e("TOKEN", "Fetch FCM token failed: Task unsuccessful.")
                }
            })
        }
        catch (e: Exception) {
            Log.e(ContentValues.TAG, "Fetching FCM token failed.")
        }
    }
}