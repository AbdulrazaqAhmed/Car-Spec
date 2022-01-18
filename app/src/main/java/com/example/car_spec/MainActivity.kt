package com.example.car_spec

import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.car_spec.databinding.ActivityMainBinding
import com.example.car_spec.view.main.CarFragment
import com.example.car_spec.view.main.FavoritFragment
import com.example.car_spec.view.main.MyCarFragment

private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private var favoriteFragment = FavoritFragment()
    private var carFragment = CarFragment()
    private var myCarFragment = MyCarFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replacementFragment(myCarFragment)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.carFragment -> replacementFragment(carFragment)
                R.id.favoritFragment2 -> replacementFragment(favoriteFragment)
                R.id.myCarFragment2 -> replacementFragment(myCarFragment)
//                R.id.message        -> replacementFragment(me)


//                else -> {return}
            }
            true

        }



        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 0
            )

        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)




        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)


//        val appBartitle = AppBarConfiguration(setOf(R.id.carFragment, R.id.favoritFragment,R.id.NotificationFragment,R.id.MessageFragment))
//        setupActionBarWithNavController(navController, appBartitle)
//

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()

    }
    private fun replacementFragment(fragment : Fragment) {


        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView4, fragment)
        }
    }

//    override fun onBackPressed() {
////        when((supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as NavHostFragment).navController.currentDestination?.label) {
////          "fragment_car" -> super.onBackPressed()
//
////        }
//    }

}

