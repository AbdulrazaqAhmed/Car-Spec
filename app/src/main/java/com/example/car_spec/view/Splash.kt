package com.example.car_spec.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.car_spec.Login
import com.example.car_spec.MainActivity
import com.example.car_spec.R
import com.example.car_spec.SHARED_PREF_FILE

private lateinit var sharedPref : SharedPreferences

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("state", true)){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Log.d(TAG,sharedPref.getBoolean("state", true).toString())
        }

        val intent = Intent(this, MainActivity::class.java)
        object : CountDownTimer(2000,3000) {
            override fun onTick(p0: Long) {

            }


            override fun onFinish() {
                startActivity(intent)
            }

        }.start()
    }
}