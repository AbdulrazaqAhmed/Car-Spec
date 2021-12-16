package com.example.car_spec

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)


        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)



    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}

