package com.example.car_spec.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_spec.model.FavoriteModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import java.lang.Exception

private const val TAG = "FavoriteViewModel"

class FavoriteViewModel : ViewModel() {
    private val apiServ = ApiServiceRepo.get()
val favoritesModel = mutableListOf<FavoriteModel>()
    val favoriteLiveData = MutableLiveData<List<FavoriteModel>>()
    val favoriteErrorData = MutableLiveData<String>()
//    val carfitch = mutableListOf<CarModel>()


    fun callFavorites() {

//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiServ.fitchFavorites()
//                if (response.isSuccessful) {
//                    favoriteLiveData.postValue(carfitch)
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
        var favList = mutableListOf<FavoriteModel>()
        apiServ.fitchFavorites().addOnSuccessListener { documents ->
            for (document in documents){
                Log.d(
                    com.example.car_spec.view.viewmodel.TAG,
                    "${document.id} => ${document.data}")
                favList.add(document.toObject<FavoriteModel>())
//                getCar.userId = document.id
//                carfitch.add(document.toObject<CarModel>())
                favoriteLiveData.postValue(favList)
            }

        }.addOnFailureListener{
            Log.d(TAG, "callFavorites: Failed")
        }
    }

    fun removeFavorite(fav : FavoriteModel) {
        val favo = mutableListOf<FavoriteModel>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServ.removeFavorit(fav)
                if (!response.isSuccessful) {
                    Log.d(TAG, "response")
                    favoriteLiveData.postValue(favo)

                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                favoriteErrorData.postValue(e.message.toString())

            }
        }
    }

    fun addFavorites(fav : FavoriteModel){
//        val addMyCar = mutableListOf<CarModel>()

        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = apiServ.addFavorites(fav)

                if (response.isSuccessful){
                    Log.d(TAG,response.toString())
//                    favoriteLiveData.postValue(carfitch)
                }

            }catch (e:Exception){
                Log.d(TAG, e.message.toString())
                favoriteErrorData.postValue(e.message.toString())
            }
        }

    }

}
