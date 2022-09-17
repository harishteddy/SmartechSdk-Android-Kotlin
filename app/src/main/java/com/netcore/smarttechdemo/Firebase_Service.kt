package com.netcore.smarttechdemo

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.netcore.android.smartechpush.SmartPush
import java.lang.ref.WeakReference

class Firebase_Service : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i("message","fcm service" )
        super.onMessageReceived(remoteMessage)
        Log.i("message",remoteMessage.toString() )
        SmartPush.getInstance(WeakReference(applicationContext)).handlePushNotification(remoteMessage.data.toString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("fcmtoken", token)
        SmartPush.getInstance(WeakReference(this)).setDevicePushToken(token)//fetch token
    }
}