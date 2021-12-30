package com.example.car_spec

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.car_spec.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
private lateinit var sharedPref : SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)


        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)



    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}

