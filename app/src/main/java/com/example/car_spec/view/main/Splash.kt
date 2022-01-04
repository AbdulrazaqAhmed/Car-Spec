package com.example.car_spec.view.main

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.car_spec.MainActivity
import com.example.car_spec.R
import com.example.car_spec.accessablity.Login
import com.example.car_spec.accessablity.SHARED_PREF_FILE
import com.example.car_spec.repository.ApiServiceRepo

private lateinit var sharedPref: SharedPreferences

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        ApiServiceRepo.init(this)
        setContentView(R.layout.activity_splash)
        sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)

        object : CountDownTimer(2000, 3000) {
            override fun onTick(p0: Long) {

            }


            override fun onFinish() {

                if (sharedPref.getBoolean("state", false)) {
                    val intent = Intent(this@Splash, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    Log.d(TAG, sharedPref.getBoolean("state", true).toString())
                }  else{
                    val intent = Intent(applicationContext, Login::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }.start()
    }
}