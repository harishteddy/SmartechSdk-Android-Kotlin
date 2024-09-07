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
        if (bundleExtra != null) {
            if (bundleExtra.containsKey(SMTBundleKeys.SMT_BUNDLE_KEY_CLICK_DEEPLINK)) {
                val deepLinkvalue = bundleExtra.getString(SMTBundleKeys.SMT_BUNDLE_KEY_CLICK_DEEPLINK)
                Log.v("harishb", deepLinkvalue.toString())

                if (deepLinkvalue != null) {
                    if (deepLinkvalue.equals("sampleapp://profile")) {
                        openprofile(context)
                    }else if (deepLinkvalue.equals("sampleapp://login")){
                        openprofile(context)
                    }else{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkvalue))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context?.startActivity(intent)
                    }
                }
            } else {
                Log.v("Activity", "Does not have deeplink path.")
            }

        }
    }
    private fun openprofile(context: Context?) {
        val displayScreen = Intent(context, RegisterScreen::class.java)
        displayScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(displayScreen)
    }

}