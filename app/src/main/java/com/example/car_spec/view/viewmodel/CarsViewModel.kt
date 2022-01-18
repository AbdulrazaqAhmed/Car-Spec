package com.example.car_spec.view.viewmodel

import android.content.ContentValues
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

//private val imageUri : Uri? = null
private lateinit var sharedpreff: SharedPreferences
private const val TAG = "CarsViewModel"
const val REQUEST_CODE = 200


class CarsViewModel : ViewModel() {
    //-----------------------Repo declaration--------------
    private val apiServ = ApiServiceRepo.get()
    private val storCarRef = apiServ.storageCarReference

    //----------------------live Data && Error Data -----------
    val carsLiveData =
        MutableLiveData<List<CarModel>>()       //open variable to use in car fragment observer fun
    val uploadImageLiveData = MutableLiveData<String>()
    val uploadImageErrorLiveData = MutableLiveData<String>()
    val carsErrorLiveData =
        MutableLiveData<List<String>>()    //open variable to use in car fragment observer fun
    val selectedCarItemFireStore: CarModel? = null// for favorite toggle button
    val selectedItemMutableLiveData = MutableLiveData<CarModel>()
    private lateinit var usersModel: UsersModel
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = firestore.collection("user")
    private val car = firestore.collection("car")

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

    }


    fun save(car: CarModel, uri: Uri) {
        apiServ.save(car)

            .addOnSuccessListener {
                Log.d("Firebase", "Document saved")
                var documentId = it.id
                Log.d(TAG, documentId)
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss", Locale.getDefault())
//                val now = Date()
                val time = formatter.format(Date())
                val imagename =
                    documentId + "_" + FirebaseAuth.getInstance().uid.toString() + "_" + time + "/"

                uploadPhoto(uri, car.image)
                Log.d(TAG, "File Name")

                // updating the document with Image Name
                //

            }

            .addOnFailureListener() {
                Log.d("Firebase", "save Failed ")

            }
    }


    fun uploadPhoto(imge: Uri, imagename: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseImage = apiServ.uploadImage(imge, imagename)

                responseImage.addOnSuccessListener { taskSnapshot ->

                    Log.d(TAG, taskSnapshot.metadata?.name.toString())

                    if (responseImage.isSuccessful) {
                        uploadImageLiveData.postValue(this.toString())
                    } else {

                        uploadImageErrorLiveData.postValue("")
                        Log.d(TAG, "exception, uploadImageErrorLiveData ")
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
                Log.d(TAG, "fitch: Failed")

            }

    }

//    fun removeFavoriteProduct(productId : Int){
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiServ.removeFavorit(carId)
//                if (!response.isSuccessful){
//                    Log.d(ContentValues.TAG, response.message())
//                    productsErrorLiveData.postValue(response.message())
//
//                }
//            }catch (e: Exception){
//                Log.d(ContentValues.TAG, e.message.toString())
//                productsErrorLiveData.postValue(e.message.toString())
//
//            }
//        }
//    }

//    fun removeFavoritCar(carId : Int){
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiServ.removeFavoriteProduct(productId)
//                if (!response.isSuccessful){
//                    Log.d(ContentValues.TAG, response.message())
//                    productsErrorLiveData.postValue(response.message())
//
//                }
//            }catch (e: Exception){
//                Log.d(ContentValues.TAG, e.message.toString())
//                productsErrorLiveData.postValue(e.message.toString())
//
//            }
//        }
//    }
//
//    fun addFavoriteCar(carId : Int) {
//
//    }
//
//    // Update - set
//
//    fun set(car: CarModel) {
//        firestore.collection("car")
//            .add(car)
//
//    }
//
//    // Delete - delete
//
//    fun delete(car: CarModel) {
//        firestore.collection("car")
//
//
//
//
//    }
//
//    fun addFavoriteCar() {}
}