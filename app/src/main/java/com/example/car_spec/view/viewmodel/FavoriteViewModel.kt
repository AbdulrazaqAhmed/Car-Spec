package com.example.car_spec.view.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_spec.model.CarModel
import com.example.car_spec.repository.ApiServiceRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "FavoriteViewModel"

class FavoriteViewModel : ViewModel() {
    private val apiServ = ApiServiceRepo.get()

    val favoriteLiveData = MutableLiveData<List<CarModel>>()
    val favoriteErrorData = MutableLiveData<String>()


    fun callFavorites() {
        val car = mutableListOf<CarModel>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServ.fitchFavorites()
                if (response.isSuccessful) {
                    favoriteLiveData.postValue(car)
                } else {
                    favoriteErrorData.postValue("Error")
                    Log.d(TAG, this.toString())
                }


            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                favoriteErrorData.postValue("Error")

            }
        }

    }

//    fun removeFavorite(productId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiServ.removeFavoriteProduct(productId)
//                if (!response.isSuccessful) {
//                    Log.d(ContentValues.TAG, response.message())
//                    productsErrorLiveData.postValue(response.message())
//
//                }
//            } catch (e: Exception) {
//                Log.d(ContentValues.TAG, e.message.toString())
//                productsErrorLiveData.postValue(e.message.toString())
//
//            }
//        }
//    }

}