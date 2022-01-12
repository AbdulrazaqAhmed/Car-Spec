package com.example.car_spec.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_spec.model.CarModel
import com.example.car_spec.repository.ApiServiceRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private val apiServ = ApiServiceRepo.get()
//
//    val favoriteLiveData = MutableLiveData<List<CarModel>>()
//    val favoriteErrorData = MutableLiveData<List<CarModel>>()
//
//
//    fun callFavorites() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = apiServ.
//
//            }
//        }
//
//    }


}