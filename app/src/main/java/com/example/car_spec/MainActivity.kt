package com.example.car_spec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userIdTextView : TextView = findViewById(R.id.userID_TextView)
        val emailAddressTextView : TextView = findViewById(R.id.emailAddress_TextView)
        val logoutButton : Button = findViewById(R.id.logout_button)

        val userID = intent.getStringArrayExtra("UserId")
        val emailAddress = intent.getBooleanArrayExtra("Email")

        userIdTextView.text= "UserID:" + userID
        emailAddressTextView.text = "EmailAddress: " + emailAddress


        logoutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,Login::class.java))
            finish()

        }


    }
}

