package com.netcore.smarttechdemo


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
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity()  {
    private lateinit var logOutTextView: TextView
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences=getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)

        init()

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