package com.example.sp_dbproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var signupPageOpenInText=findViewById<TextView>(R.id.signupPageOpenTextView)
        signupPageOpenInText.setOnClickListener {
            startActivity(Intent(this,signup_page::class.java))
            finish()
        }



        var editTextEmailLogin=findViewById<EditText>(R.id.userNameEditTextLogin)
        var editTextPasswordLogin=findViewById<EditText>(R.id.passwordEditTextLogin)
        var clickButton=findViewById<Button>(R.id.LoginButtonClick)

        clickButton.setOnClickListener {
            var getDataSignupSharedPreferences=getSharedPreferences("StoreData", MODE_PRIVATE)
           var signupGetDataUserName= getDataSignupSharedPreferences.getString("Email",null)
           var signupGetDataPassword= getDataSignupSharedPreferences.getString("Password",null)

            var inputLoginUserName=editTextEmailLogin.text.toString()
            var inputLoginPassword=editTextPasswordLogin.text.toString()


            if (editTextEmailLogin.text.toString().isEmpty() || editTextPasswordLogin.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter Your Email & Password", Toast.LENGTH_SHORT).show()
            }
            else if (inputLoginUserName != signupGetDataUserName || inputLoginPassword != signupGetDataPassword){
                Toast.makeText(this, "Password Don't Match", Toast.LENGTH_SHORT).show()
            }
            else{
                var LoginSharedPreferences=getSharedPreferences("StoreData", MODE_PRIVATE).edit()
                LoginSharedPreferences.putString("userName",editTextEmailLogin.text.toString())
                LoginSharedPreferences.putString("Password",editTextPasswordLogin.text.toString())

                startActivity(Intent(this,HomePage_ListView_Show::class.java))
                LoginSharedPreferences.apply()
            }
        }


    }
}