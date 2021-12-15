package com.example.car_spec.accessablity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.car_spec.MainActivity
import com.example.car_spec.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regEmail : EditText =findViewById(R.id.register_email_edittext)
        val regPass  : EditText =findViewById(R.id.register_password_edittext)
        val regButton  : Button =findViewById(R.id.register_button)
        val haveAccount : TextView = findViewById(R.id.alreadyHaveAccountNavToLogin_textview)

        haveAccount.setOnClickListener(){
            startActivity(Intent(this,Login::class.java))
            finish()
        }

        regButton.setOnClickListener {
                val emailReg : String =regEmail.text.toString()
                val passReg : String = regPass.text.toString()


            if (emailReg.isNotEmpty() && passReg.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailReg,passReg).addOnCompleteListener{
                        task -> if (task.isSuccessful){
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    Toast.makeText(this, "Registerd successfully", Toast.LENGTH_SHORT).show()
                    //navigate to Main Activity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userId", firebaseUser.uid)
                    intent.putExtra("Email",firebaseUser.email)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
                }
            }


        }


    }
}