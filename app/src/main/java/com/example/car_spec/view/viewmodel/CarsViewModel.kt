package com.example.car_spec.view.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.core.FirestoreClient

private lateinit var sharedpreff : SharedPreferences
class CarsViewModel : ViewModel() {
//   private val toyota : MutableLiveData<ArrayList<Cars>>
    private val apiServ = ApiServiceRepo.get()
    private var firestore : FirebaseFirestore = FirebaseFirestore.getInstance()


    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

    }

    fun save (user : User){
        firestore.collection("user")
            .add(user)
            .addOnSuccessListener {

            }
            .addOnFailureListener() {

            }

    }

    fun fitch (user : User){
        firestore.collection("user")
            .get()
            .addOnSuccessListener {

            }
            .addOnFailureListener() {

            }
    }









}