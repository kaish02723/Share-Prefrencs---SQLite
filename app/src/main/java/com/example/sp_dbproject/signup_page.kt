package com.example.sp_dbproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class signup_page : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_page)

        var editTextNameSignup=findViewById<EditText>(R.id.nameEditTextSignup)
        var editTextemailSignup=findViewById<EditText>(R.id.emailEditTextSignup)
        var editTextPasswordSignup=findViewById<EditText>(R.id.passwordEditTextSignup)
        var editTextRePasswordSignup=findViewById<EditText>(R.id.rePasswordEditTextSignup)


        var signuToHomePageClickButton=findViewById<Button>(R.id.signupButton)
        signuToHomePageClickButton.setOnClickListener {
            if(editTextNameSignup.text.toString().isEmpty() || editTextemailSignup.text.toString().isEmpty()
                || editTextPasswordSignup.text.toString().isEmpty() || editTextRePasswordSignup.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter Your Name & Email & Password ", Toast.LENGTH_SHORT).show()
            }
            else if(editTextPasswordSignup.text.toString() != editTextRePasswordSignup.text.toString()){
                Toast.makeText(this, "Password Don't Match", Toast.LENGTH_SHORT).show()
            }
            else{
                var sharedPreferences=getSharedPreferences("StoreData", MODE_PRIVATE).edit()
                sharedPreferences.putString("Name",editTextNameSignup.text.toString())
                sharedPreferences.putString("Email",editTextemailSignup.text.toString())
                sharedPreferences.putString("Password",editTextPasswordSignup.text.toString())
                sharedPreferences.putString("ConfirmPassword",editTextRePasswordSignup.text.toString())

                startActivity(Intent(this,HomePage_ListView_Show::class.java))
                finish()
                sharedPreferences.apply()
            }
        }















        var clickButtonAndGoLoginPage=findViewById<TextView>(R.id.textViewSignupPageExit)
        clickButtonAndGoLoginPage.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}