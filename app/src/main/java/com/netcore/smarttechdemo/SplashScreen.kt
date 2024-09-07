package com.netcore.smarttechdemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.netcore.android.Smartech
import java.lang.ref.WeakReference


class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        val isSmartechHandledDeeplink = Smartech.getInstance(WeakReference(this)).isDeepLinkFromSmartech(intent)
        if (!isSmartechHandledDeeplink) {
            //Handle deeplink on app side
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}