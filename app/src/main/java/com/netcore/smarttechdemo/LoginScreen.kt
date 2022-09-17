package com.netcore.smarttechdemo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class LoginScreen: AppCompatActivity(), View.OnClickListener {
    private val activity = this@LoginScreen
    private lateinit var linearBody: LinearLayout
    private lateinit var textEditTextUser: EditText
    private lateinit var textEditTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var checkBox: CheckBox
    private lateinit var btnRegister: Button
    private lateinit var inputValidation: InputValidation
    private lateinit var dbHelper: DbHelper
    lateinit var sharedPreferences: SharedPreferences
    var isRemembered=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        supportActionBar!!.hide()

        initViews()
        initListeners()
        initObjects()
        sharedPreferences=getSharedPreferences("Shared_pref",Context.MODE_PRIVATE)

    }

    //Initialize views
    private fun initViews() {
        textEditTextUser = findViewById(R.id.username_field)
        textEditTextPassword = findViewById(R.id.password_field)
        btnLogin = findViewById(R.id.login_button)
        checkBox=findViewById(R.id.checkBox)
        btnRegister = findViewById(R.id.register_button)
        linearBody = findViewById(R.id.linearBody)
    }
    //Initialize listeners
    private fun initListeners() {
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }
    //Initialize objects
    private fun initObjects() {
        dbHelper = DbHelper(activity)
        inputValidation = InputValidation(activity)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_button -> verifyFromDB()
            R.id.register_button -> {
                val intentReg = Intent(applicationContext, RegisterScreen::class.java)
                startActivity(intentReg)
            }
        }
    }

    private fun verifyFromDB() {

        val checked:Boolean=checkBox.isChecked
        val editor:SharedPreferences.Editor=sharedPreferences.edit()
        editor.putString("Email",textEditTextUser.text.toString())
        editor.putString("password",textEditTextPassword.text.toString())
        editor.putBoolean("CHECKBOX",checked)
        editor.apply()
        Toast.makeText(this,"usercredentilas saved",Toast.LENGTH_SHORT).show()
        if (!inputValidation.isTextFilled(textEditTextUser, getString(R.string.error_message_nofill_name))) {

            return
        }
        if (!inputValidation.isTextFilled(textEditTextPassword, getString(R.string.error_message_nofill_pass))) {
            return
        }

        if (dbHelper.logCheckUser(textEditTextUser.text.toString().trim() { it <= ' '}, textEditTextPassword.text.toString().trim() { it <= ' '})) {

            isRemembered=sharedPreferences.getBoolean("CHECKBOX",false)
            if (isRemembered){
                val mainIntent = Intent(activity, MainActivity::class.java)
                mainIntent.putExtra("uname", textEditTextUser.text.toString().trim())
                textEditTextUser.text = null
                textEditTextPassword.text = null
                startActivity(mainIntent)
                finish()
            }

        } else {
            Snackbar.make(linearBody, getString(R.string.error_message_invalid), Snackbar.LENGTH_LONG).show()
        }
    }
}