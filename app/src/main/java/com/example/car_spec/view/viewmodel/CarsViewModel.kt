package com.example.car_spec.view.viewmodel

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_spec.model.CarModel
import com.example.car_spec.model.UsersModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

//private val imageUri : Uri? = null
private lateinit var sharedpreff: SharedPreferences
private const val TAG = "CarsViewModel"
const val REQUEST_CODE = 200


class CarsViewModel : ViewModel() {
    //-----------------------Repo declaration--------------
    private val apiServ = ApiServiceRepo.get()

    //----------------------live Data && Error Data -----------
    val carsLiveData =
        MutableLiveData<List<CarModel>>()       //open variable to use in car fragment observer fun
    val uploadImageLiveData = MutableLiveData<String>()
    val uploadImageErrorLiveData = MutableLiveData<String>()
    val carsErrorLiveData =
        MutableLiveData<List<String>>()    //open variable to use in car fragment observer fun
    val selectedCarItemFireStore : CarModel? = null// for favorite toggle button
    val selectedItemMutableLiveData = MutableLiveData<CarModel>()
    private lateinit var usersModel : UsersModel
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


    fun uploadPhoto(imge: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseImage = apiServ.uploadImage(imge)

                responseImage.addOnSuccessListener { taskSnapshot ->

                    Log.d(TAG, taskSnapshot.metadata?.name.toString())
                    if (responseImage.isSuccessful) {
                        uploadImageLiveData.postValue("successful")
                    } else {

                        uploadImageErrorLiveData.postValue("")
                        Log.d(TAG,"exception, uploadImageErrorLiveData ")
                    }

                }.addOnFailureListener {

                    Log.d(TAG, it.message.toString())
                    carsErrorLiveData.postValue(listOf(it.message.toString()))
                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())

            }
        }
    }




    fun fitch() {
        var car = mutableListOf<CarModel>()
//        val docRef = firestore.collection("cars").orderBy("Title", Query.Direction.ASCENDING)
        apiServ.fitch()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(
                        com.example.car_spec.view.viewmodel.TAG,
                        "${document.id} => ${document.data}"
                    )
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