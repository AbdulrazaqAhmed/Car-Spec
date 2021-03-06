package com.example.car_spec.accessablity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.car_spec.MainActivity
import com.example.car_spec.R
import com.example.car_spec.model.UsersModel
import com.example.car_spec.view.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {

    private val usersViewModel: UsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regEmail: EditText = findViewById(R.id.register_email_edittext)
        val regPass: EditText = findViewById(R.id.register_password_edittext)
        val firstName: EditText = findViewById(R.id.firstNameRegister_editText)
        val lastname: EditText = findViewById(R.id.lastNameRegister_EditText)
        val regButton: Button = findViewById(R.id.register_button)
        val haveAccount: TextView = findViewById(R.id.alreadyHaveAccountNavToLogin_textview)



        haveAccount.setOnClickListener() {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        regButton.setOnClickListener {

            val firstNameReg: String = firstName.text.toString()
            val lastNameReg: String = lastname.text.toString()
            val emailReg: String = regEmail.text.toString()
            val passReg: String = regPass.text.toString()


            if (firstNameReg.isNotEmpty() && lastNameReg.isNotEmpty() && emailReg.isNotEmpty() && passReg.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailReg, passReg)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user =
                                UsersModel(emailReg, firstNameReg, lastNameReg, firebaseUser.uid)
                            usersViewModel.saveUsers(user)

                            Toast.makeText(this, "Registerd successfully", Toast.LENGTH_SHORT)
                                .show()

                            //navigate to Main Activity
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("userId", firebaseUser.uid)
                            intent.putExtra("Email", firebaseUser.email)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


            }


        }


    }


}