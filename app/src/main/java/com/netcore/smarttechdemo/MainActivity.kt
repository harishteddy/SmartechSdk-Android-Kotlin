package com.netcore.smarttechdemo

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netcore.android.Smartech
import com.netcore.android.smartechappinbox.SmartechAppInbox
import com.netcore.android.smartechappinbox.network.listeners.SMTInboxCallback
import com.netcore.android.smartechappinbox.network.model.SMTInboxMessageData
import com.netcore.android.smartechappinbox.utility.SMTAppInboxRequestBuilder
import com.netcore.android.smartechappinbox.utility.SMTInboxDataType
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var btnAppInbox: Button
    private lateinit var btnAppInbox2: Button
    private lateinit var btnLogout: Button
    private lateinit var preferences: SharedPreferences
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Smartech.getInstance(WeakReference(applicationContext)).login("harish@gmail.com")
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        initUI() // Initialize UI elements

        // Set up button click listeners
        btnAppInbox.setOnClickListener {
            fetchInboxMessages() // Handle APPINBOX button click
        }

        btnAppInbox2.setOnClickListener {
            Toast.makeText(this, "APPINBOX2 clicked", Toast.LENGTH_SHORT).show()
            SmartechAppInbox.getInstance(WeakReference(applicationContext)).displayAppInbox(this)
        }

        btnLogout.setOnClickListener {
            logOut() // Handle Logout button click
        }
    }

    private fun fetchInboxMessages() {
        val categoryList = arrayListOf("nuvama")
        Toast.makeText(applicationContext, "Fetching inbox messages...", Toast.LENGTH_SHORT).show()

        val smartechAppInbox = SmartechAppInbox.getInstance(WeakReference(applicationContext))
        val builder = SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
            .setCallback(object : SMTInboxCallback {

                override fun onInboxFail() {
                    hideProgressBar()
                    Toast.makeText(applicationContext, "Failed to fetch inbox messages.", Toast.LENGTH_SHORT).show()
                }

                override fun onInboxProgress() {
                    runOnUiThread {
                        progressBar.visibility = View.VISIBLE // Show progress bar while loading
                    }
                }

                override fun onInboxSuccess(messages: MutableList<SMTInboxMessageData>?) {
                    Log.i("INBOX Success", "Messages fetched: ${messages.toString()}")
                    hideProgressBar()

                    if (!messages.isNullOrEmpty()) {
                        val inboxMessages = messages.map { messageData ->
                            InboxMessage(
                                title = messageData.smtPayload.title ?: "No Title",
                                body = messageData.smtPayload.body ?: "No Body",
                                time = messageData.smtPayload.publishedDate ?: "No Date",
                                mediaUrl = messageData.smtPayload.mediaUrl ?: ""
                            )
                        }

                        // Update RecyclerView
                        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        recyclerView.adapter = InboxAdapter(inboxMessages)
                    } else {
                        Toast.makeText(applicationContext, "No messages found.", Toast.LENGTH_SHORT).show()
                    }
                }

            }).setCategory(categoryList)
            .setLimit(10)
            .build()

        smartechAppInbox.getAppInboxMessages(builder)
    }

    private fun hideProgressBar() {
        runOnUiThread {
            progressBar.visibility = View.GONE // Hide the progress bar
        }
    }

    private fun initUI() {
        btnAppInbox = findViewById(R.id.btn_appinbox)
        btnAppInbox2 = findViewById(R.id.btn_appinbox2)
        btnLogout = findViewById(R.id.btn_logout)
        progressBar = findViewById(R.id.progressBar)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun logOut() {
        Toast.makeText(applicationContext, "Logging out...", Toast.LENGTH_SHORT).show()

        val editor = preferences.edit()
        editor.clear() // Clear preferences on logout
        editor.apply()

        val intent = Intent(this, SplashScreen::class.java)
        startActivity(intent)
        finish() // Close the activity to prevent going back after logout
    }
}


















/*  private fun fetchInboxMessages() {
      val categoryList = arrayListOf("nuvama")
      Toast.makeText(applicationContext, "INBOX Start", Toast.LENGTH_SHORT).show()

      val smartechAppInbox = SmartechAppInbox.getInstance(WeakReference(applicationContext))

      val builder = SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
          .setCallback(object : SMTInboxCallback {

              override fun onInboxFail() {
                  hideProgressBar()
                  Toast.makeText(applicationContext, "INBOX Fail", Toast.LENGTH_SHORT).show()
              }

              override fun onInboxProgress() {
                  runOnUiThread {
                      progressBar.visibility = View.VISIBLE // Show progress bar
                  }
              }

              override fun onInboxSuccess(messages: MutableList<SMTInboxMessageData>?) {
                  Log.i("INBOX Success", "INBOX Success: ${messages.toString()}")
                  hideProgressBar()

                  if (!messages.isNullOrEmpty()) {
                      val inboxMessages = messages.map {
                          InboxMessage(
                              title = it.toString() ?: "No Title",
                              body = it.toString() ?: "No Body",
                              deeplink = it.toString() ?: "http://example.com"
                          )
                      }

                      val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                      recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                      recyclerView.adapter = InboxAdapter(inboxMessages)
                  } else {
                      Toast.makeText(applicationContext, "No messages found", Toast.LENGTH_SHORT).show()
                  }
              }

          }).setCategory(categoryList)
          .setLimit(10)
          .build()

      smartechAppInbox.getAppInboxMessages(builder)
  }*/

















/*
package com.netcore.smarttechdemo

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.netcore.android.Smartech
import com.netcore.android.smartechappinbox.SmartechAppInbox
import com.netcore.android.smartechappinbox.network.listeners.SMTInboxCallback
import com.netcore.android.smartechappinbox.network.model.SMTInboxMessageData
import com.netcore.android.smartechappinbox.utility.SMTAppInboxMessageType
import com.netcore.android.smartechappinbox.utility.SMTAppInboxRequestBuilder
import com.netcore.android.smartechappinbox.utility.SMTInboxDataType
import com.netcore.android.smartechpush.SmartPush
import com.netcore.android.smartechpush.notification.channel.SMTNotificationChannel
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var logOutTextView: TextView
    private lateinit var preferences: SharedPreferences
    private lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Smartech.getInstance(WeakReference(applicationContext)).login("harish@gmail.com")
        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        initUI()
        setupNotificationChannel()



    }
    override fun onResume() {
        super.onResume()
        setupLogoutListener()
    }




    private fun initUI() {
        logOutTextView = findViewById(R.id.tv_logout)
        progressBar = findViewById(R.id.progressBar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun setupNotificationChannel() {
        SmartPush.getInstance(WeakReference(this)).createNotificationChannelGroup("customSoundGroup", "testgroup")

        val smtBuilder = SMTNotificationChannel.Builder(
            "1234", "CustSound", NotificationManager.IMPORTANCE_HIGH
        )
            .setChannelDescription("This is the channel description")
            .setChannelGroupId("customSoundGroup")
            .setNotificationSound("lau")  // Ensure sound file exists without extension

        val smtNotificationChannel = smtBuilder.build()
        SmartPush.getInstance(WeakReference(this)).createNotificationChannel(smtNotificationChannel)
    }

    private fun hideProgressBar() {
        runOnUiThread {
            progressBar.visibility = View.GONE // Hide the progress bar when done
        }
    }
    private fun setupLogoutListener() {
        logOutTextView.setOnClickListener {
           fetchInboxMessages()

            logOut()
        }
    }



    private fun fetchInboxMessages() {
        val categoryList = arrayListOf("nuvama")
        Toast.makeText(applicationContext, "INBOX Start", Toast.LENGTH_SHORT).show()
        val smartechAppInbox = SmartechAppInbox.getInstance(WeakReference(applicationContext))

        val builder = SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
            .setCallback(object : SMTInboxCallback {


                override fun onInboxFail() {
                    hideProgressBar()
                    Toast.makeText(applicationContext, "INBOX Fail", Toast.LENGTH_SHORT).show()
                }


                override fun onInboxProgress() {
                    try {
                        runOnUiThread {
                            progressBar.visibility = View.VISIBLE // Show the progress bar
                        }
                    } catch (e: Exception) {
                        Log.e("onInboxProgress", "Error during inbox progress: ${e.message}", e)
                    }
                }

                override fun onInboxSuccess(messages: MutableList<SMTInboxMessageData>?) {
                   // Toast.makeText(applicationContext, "INBOX Success", Toast.LENGTH_SHORT).show()
                    Log.i("INBOX Success", "INBOX Success:${messages.toString()}")

                    if (!messages.isNullOrEmpty()) {

                        hideProgressBar()
                        val inboxMessages = messages.map {
                           InboxMessage(
                               title = "messages.toString() "?: "No Title",
                                body = "messages.toString()"?: "No Body",
                                deeplink = "messages.toString()" ?: "http://example.com"
                            )
                        }
                        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        recyclerView.adapter = InboxAdapter(inboxMessages)
                    } else {
                        Toast.makeText(applicationContext, "No messages found", Toast.LENGTH_SHORT).show()
                    }
                }


            }).setCategory(categoryList)
            .setLimit(10)
            .build()

        smartechAppInbox.getAppInboxMessages(builder)


    }


    private fun logOut() {

        //Default app inbox ui
   */
/* val smartechAppInbox = SmartechAppInbox.getInstance(WeakReference(applicationContext))
        smartechAppInbox.displayAppInbox(this)*//*


       */
/* val editor = preferences.edit()
        editor.clear()
        editor.apply()
        Toast.makeText(applicationContext, " Appinbox clicked", Toast.LENGTH_SHORT).show()*//*



      */
/*  val intent = Intent(this, SplashScreen::class.java)
        startActivity(intent)*//*

       // finish()
    }

    fun onClick(view: View?) {
        logOut()

    }
}

*/


































































/*
package com.netcore.smarttechdemo


import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.netcore.android.Smartech
import com.netcore.android.smartechappinbox.SmartechAppInbox
import com.netcore.android.smartechappinbox.network.listeners.SMTInboxCallback
import com.netcore.android.smartechappinbox.network.model.SMTInboxMessageData
import com.netcore.android.smartechappinbox.utility.SMTAppInboxRequestBuilder
import com.netcore.android.smartechappinbox.utility.SMTInboxDataType
import com.netcore.android.smartechpush.SmartPush
import com.netcore.android.smartechpush.notification.channel.SMTNotificationChannel
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity()  {
    private lateinit var logOutTextView: TextView
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Smartech.getInstance(WeakReference(applicationContext)).login("harish@gmail.com")
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

           //Toast.makeText(applicationContext,"Inbox start", Toast.LENGTH_SHORT).show()


           //Toast.makeText(applicationContext,"Inbox end", Toast.LENGTH_SHORT).show()

           val smartechAppInbox = SmartechAppInbox.getInstance(WeakReference(applicationContext))
           val builder = SMTAppInboxRequestBuilder.Builder(SMTInboxDataType.ALL)
               .setCallback(object : SMTInboxCallback {
                   override fun onInboxFail() {
                   }

                   override fun onInboxProgress() {
                   }

                   override fun onInboxSuccess(messages: MutableList<SMTInboxMessageData>?) {


                       Toast.makeText(applicationContext,"Inbox success"+messages.toString(), Toast.LENGTH_SHORT).show()
                   }
               })
               .setCategory(arrayListOf("cat1"))
               .setLimit(10).build();
           smartechAppInbox.getAppInboxMessages(builder)



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


   fun onClick(view: View?) {

 val editor:SharedPreferences.Editor=preferences.edit()
        editor.clear()
        editor.apply()
         val intent= Intent(this,SplashScreen::class.java)
        startActivity(intent)
        finish()
    }


}
*/
