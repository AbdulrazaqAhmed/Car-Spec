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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.core.FirestoreClient
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson

private lateinit var sharedpreff: SharedPreferences
private const val TAG = "CarsViewModel"
class CarsViewModel : ViewModel() {
    //-----------------------Repo declaration--------------
    private val apiServ = ApiServiceRepo.get()

    //----------------------live Data && Error Data -----------
    val carsLiveData =
        MutableLiveData<List<CarModel>>()       //open variable to usse in car fragment observer fun
    val carsErrorLiveData =
        MutableLiveData<List<String>>()    //open variable to usse in car fragment observer fun
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = firestore.collection("user")
    private val car = firestore.collection("car")

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

    }


    fun save(car: CarModel) {
        apiServ.save(car)
            .addOnSuccessListener {
                Log.d("Firebase", "Document saved")

            }

            .addOnFailureListener() {
                Log.d("Firebase", "save Failed ")

            }

    }

    fun fitch() {
        var car = mutableListOf<CarModel>()
//        val docRef = firestore.collection("cars").orderBy("Title", Query.Direction.ASCENDING)
        apiServ.fitch()
            .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(com.example.car_spec.view.viewmodel.TAG ,"${document.id} => ${document.data}")
               // Log.d(com.example.car_spec.view.viewmodel.TAG, "${document.toObject(CarModel::class.java)}")

            //    car.add(Gson().fromJson(document.data.toString(),CarModel::class.java))
                //                car.add(Gson().fromJson(document.data.toString(),CarModel::class.java))

                car.add(document.toObject<CarModel>())


            }

            carsLiveData.postValue(car)
            // type observer
        }

            .addOnFailureListener() {

            }

    }

    // Update - set

    fun set(car: CarModel) {
        firestore.collection("car")
            .add(car)

    }

    // Delete - delete

    fun delete(car: CarModel) {
        firestore.collection("car")


        fun addFavoriteCar() {}

    }


}