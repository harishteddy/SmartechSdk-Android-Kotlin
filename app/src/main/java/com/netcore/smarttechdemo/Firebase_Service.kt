package com.netcore.smarttechdemo

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.netcore.android.smartechpush.SmartPush
import java.lang.ref.WeakReference

class Firebase_Service : FirebaseMessagingService() {
   override fun onMessageReceived(remoteMessage: RemoteMessage) {
       super.onMessageReceived(remoteMessage)
       val isPushFromSmartech:Boolean = SmartPush.getInstance(WeakReference(applicationContext)).isRemoteNotificationFromSmartech(remoteMessage)
       if(isPushFromSmartech){
           SmartPush.getInstance(WeakReference(applicationContext)).handleRemotePushNotification(remoteMessage)
       } else {
           // Notification received from other sources
       }
   }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("fcmtoken", token)
        SmartPush.getInstance(WeakReference(this)).setDevicePushToken(token)//fetch token
    }
}