package com.netcore.smarttechdemo


import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.netcore.android.smartechpush.SmartPush
import com.netcore.android.smartechpush.notification.channel.SMTNotificationChannel
import java.lang.ref.WeakReference
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity()  {
    private lateinit var logOutTextView: TextView
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences=getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)

        init()


        SmartPush.getInstance(WeakReference(this)).createNotificationChannelGroup("customSoundGroup", "testgroup")
        val smtBuilder: SMTNotificationChannel.Builder= SMTNotificationChannel.Builder(
            "1234",
            "CustSound",
            NotificationManager.IMPORTANCE_HIGH)
        smtBuilder.setChannelDescription("thisischanneldesc");

        //To set the description to the channel add below method.
        smtBuilder.setChannelGroupId("customSoundGroup");

        //To set sound to channel, add below method. (Note that sound name must be without extention.)
        smtBuilder.setNotificationSound("lau")

        val smtNotificationChannel: SMTNotificationChannel = smtBuilder.build()

        SmartPush.getInstance(WeakReference(this)).createNotificationChannel(smtNotificationChannel)




       logOutTextView.setOnClickListener {
           val editor:SharedPreferences.Editor=preferences.edit()
           editor.clear()
           editor.apply()
           val intent=Intent(this,SplashScreen::class.java)
           startActivity(intent)
           finish()
       }

    }

    private fun init() {
      logOutTextView=findViewById(R.id.tv_logout)
    }


   /* override fun onClick(view: View?) {

 val editor:SharedPreferences.Editor=preferences.edit()
        editor.clear()
        editor.apply()
         val intent=Intent(this,SplashScreen::class.java)
        startActivity(intent)
        finish()
    }*/


}