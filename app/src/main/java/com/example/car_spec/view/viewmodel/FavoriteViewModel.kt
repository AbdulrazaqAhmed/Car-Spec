package com.example.car_spec.view.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_spec.model.CarModel
import com.example.car_spec.model.FavoriteModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import java.lang.Exception

private const val TAG = "FavoriteViewModel"

class FavoriteViewModel : ViewModel() {
    private val apiServ = ApiServiceRepo.get()
val favoritesModel = mutableListOf<FavoriteModel>()
    val favoriteLiveData = MutableLiveData<List<CarModel>>()
    val favoriteErrorData = MutableLiveData<String>()
    val carfitch = mutableListOf<CarModel>()


    fun callFavorites() {

//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiServ.fitchFavorites()
//                if (response.isSuccessful) {
//                    favoriteLiveData.postValue()
//                } else {
//                    favoriteErrorData.postValue("")
//                    Log.d(TAG, this.toString())
//                }
//
//
//            } catch (e: Exception) {
//                Log.d(TAG, e.message.toString())
//                favoriteErrorData.postValue("Error")
//
//            }
//        }
        apiServ.fitchFavorites().addOnSuccessListener { documents ->
            for (document in documents){
                Log.d(
                    com.example.car_spec.view.viewmodel.TAG,
                    "${document.id} => ${document.data}"
                )
                carfitch.add(document.toObject<CarModel>())
            }
            favoriteLiveData.postValue(carfitch)
        }.addOnFailureListener{
            Log.d(TAG, "callFavorites: Failed")
        }
    }

    fun removeFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServ.removeFavorit()
                if (!response.isSuccessful) {
                    Log.d(TAG, "response")
//                    favoriteLiveData.postValue()

                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                favoriteErrorData.postValue(e.message.toString())

            }
        }
    }

    fun addFavorites(car : CarModel){
//        val addMyCar = mutableListOf<CarModel>()

        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = apiServ.addFavorites(car)

                if (response.isSuccessful){
                    Log.d(TAG,response.toString())
                    favoriteLiveData.postValue(carfitch)
                }

            }catch (e:Exception){
                Log.d(TAG, e.message.toString())
                favoriteErrorData.postValue(e.message.toString())
            }
        }

    }

}