package com.example.car_spec.accessablity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.car_spec.MainActivity
import com.example.car_spec.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.core.View
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
const val SHARED_PREF_FILE = "Auth"

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val logEmail: EditText = findViewById(R.id.email_login_Edittext)
        val logPass: EditText = findViewById(R.id.password_login_editText)
        val logButton: Button = findViewById(R.id.login_Button)
        val forgotPass: TextView = findViewById(R.id.forgotPasssword_textview)
        val registerHere: TextView = findViewById(R.id.regiseterHereNav_textview)

        registerHere.setOnClickListener() {

            startActivity(Intent(this, Register::class.java))
            finish()
        }

        forgotPass.setOnClickListener() {

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.resetpassword_dialog)
            val titleText = dialog.findViewById(R.id.reset_password_textView) as TextView

            val email: EditText = dialog.findViewById(R.id.email_R_editText)
            titleText.text = title
            val yesBtn = dialog.findViewById(R.id.resetPass_button) as Button
            val noBtn = dialog.findViewById(R.id.cancelReset_button) as Button
            yesBtn.setOnClickListener {
                forgotenPass(email.text.toString())


            }
            noBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()

        }



        logButton.setOnClickListener {
            val emailLog: String = logEmail.text.toString()
            val passLog: String = logPass.text.toString()
            if (emailLog.isNotEmpty() && passLog.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailLog, passLog)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            sharedPref =
                                this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("state", true)
                            sharedPrefEditor.commit()
                            Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT)
                                .show()


                            //Navigate to Main Activity
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("UserId", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("Email", FirebaseAuth.getInstance().currentUser!!.email)
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

    fun forgotenPass(email: String) {

//        if (logEmail.text.toString().isEmpty()) {
//            return
//
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            return
//        }
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
    }
}


