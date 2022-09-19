package com.netcore.smarttechdemo

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.IntentFilter
import android.text.TextUtils
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.netcore.android.Smartech
import com.netcore.android.logger.SMTDebugLevel
import com.netcore.android.smartechpush.SmartPush
import com.netcore.android.smartechpush.notification.SMTNotificationOptions
import java.lang.ref.WeakReference

class MainApplication:Application() {


    override fun onCreate() {
        super.onCreate()
        //initialise the sdk
        Smartech.getInstance(WeakReference(applicationContext)).initializeSdk(this)
        //Enabling Smartech logs for testing.
        Smartech.getInstance(WeakReference(this)).setDebugLevel(SMTDebugLevel.Level.VERBOSE)
        //track app and install
        Smartech.getInstance(WeakReference(this)).trackAppInstallUpdateBySmartech()
        Smartech.getInstance(WeakReference(this)).trackAppInstall()

        //firebase token fetch


          try {
              val smartPush = SmartPush.getInstance(WeakReference(applicationContext))
              smartPush.fetchAlreadyGeneratedTokenFromFCM()
          } catch (e: Exception) {
              Log.e(TAG, "Fetching FCM token failed.")
          }
        fetchFcmToken()

           val deeplinkReceiver = DeeplinkReceiver()
            val filter = IntentFilter("com.smartech.EVENT_PN_INBOX_CLICK")
            registerReceiver(deeplinkReceiver, filter)



        // change notification icon color
        val options = SMTNotificationOptions(this)
        SmartPush.getInstance(WeakReference(this)).setNotificationOptions(options)
        options.smallIconTransparentId = R.drawable.ic_arrow_left
        options.transparentIconBgColor = "#007AFF"
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