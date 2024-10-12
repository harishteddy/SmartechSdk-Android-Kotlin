package com.netcore.smarttechdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.netcore.android.SMTBundleKeys

class DeeplinkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundleExtra = intent?.extras
        bundleExtra?.let {
            bundleExtra.containsKey(SMTBundleKeys.SMT_BUNDLE_KEY_CLICK_DEEPLINK)
                    val deepLinkvalue = bundleExtra.getString(SMTBundleKeys.SMT_KEY_DEEPLINK)
                    val customPayload = it.getString(SMTBundleKeys.SMT_KEY_CUSTOM_PAYLOAD)
                    Log.v("harish deeplink", deepLinkvalue.toString())

                if (deepLinkvalue != null && deepLinkvalue.isNotEmpty()) {
                        if (deepLinkvalue.equals("sampleapp://profile")) {
                            openprofile(context)
                        } else if (deepLinkvalue.equals("sampleapp://login")) {
                            openprofile(context)
                        } else {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkvalue))
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            context?.startActivity(intent)
                        }
                    } }
    }
    private fun openprofile(context: Context?) {
        val displayScreen = Intent(context, RegisterScreen::class.java)
        displayScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(displayScreen)
    }

}