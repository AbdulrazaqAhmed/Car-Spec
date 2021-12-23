package com.example.car_spec.view.viewmodel

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.car_spec.model.CarModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.core.FirestoreClient

private lateinit var sharedpreff : SharedPreferences
class CarsViewModel : ViewModel() {
//-----------------------Repo declaration--------------
    private val apiServ = ApiServiceRepo.get()

//----------------------live Data && Error Data -----------
    private val carsLiveData = MutableLiveData<List<CarModel>>()
    private val carsErrorLiveData = MutableLiveData<List<String>>()











}