package com.example.sp_dbproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class lottie_splash_screen_animation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lottie_splash_screen_animation)

        Handler(Looper.getMainLooper()).postDelayed({
            var getSharedPreferences=getSharedPreferences("StoreData", MODE_PRIVATE)
           var check= getSharedPreferences.getString("Name",null)
            if (check != null){
                startActivity(Intent(this,HomePage_ListView_Show::class.java))
                finish()
            }
            else{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }},2500)
    }
}