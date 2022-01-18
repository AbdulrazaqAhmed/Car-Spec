package com.example.car_spec.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_spec.model.CarModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MyCarsViewModel"


class MyCarsViewModel : ViewModel() {
    //____________________Repo ________________
    private val apiServ = ApiServiceRepo.get()

    //___________________ LiveData __________________
    val mycarsLiveData = MutableLiveData<List<CarModel>>()
    val mycarsErrorLiveData = MutableLiveData<List<String>>()
    val selectedItemMutableLiveData = MutableLiveData<CarModel>()
    val deleteMyCarLiveData = MutableLiveData<String>()
    val deleteMyCarErrorLiveData = MutableLiveData<String>()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()



    fun getMyCars() {
        var car = mutableListOf<CarModel>()
//        val docRef = firestore.collection("cars").orderBy("Title", Query.Direction.ASCENDING)
        apiServ.fitchMyCars()
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
                mycarsLiveData.postValue(car)
                // type observer
            }

            .addOnFailureListener() {
                Log.d(TAG, "fitch: Failed")

            }

    }

    fun editMycarInfo(mycar: CarModel) {
        apiServ.updateMycarInfo(mycar)
            .addOnSuccessListener {
                Log.d(TAG, "Modify My cars Info: ")

            }
    }


    fun deleteMyCar(carModel: CarModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try { apiServ.deleteMycars(carModel).addOnSuccessListener {
                deleteMyCarLiveData.postValue("")
                Log.d(TAG, "deleteMyCar: ")
            }.addOnFailureListener{
                deleteMyCarErrorLiveData.postValue(it.message.toString())
//                Log.d("Delete Error ",it.message.toString())
            }

            }catch (e: Exception){
                Log.d(TAG, e.message.toString())
                deleteMyCarErrorLiveData.postValue(e.message.toString())
            }
        }


    }

    // Delete - delete




        fun addFavoriteCar() {}

    }

