package com.example.car_spec.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.car_spec.model.UsersModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

private const val TAG = "UsersViewModel"

class UsersViewModel : ViewModel() {
    //----------------------Reposetory---------------
    private val apiServ = ApiServiceRepo.get()

    //----------------------LiveData---------------
    val usersLiveData = MutableLiveData<List<UsersModel>>()
    val usersErrorLiveData = MutableLiveData<List<UsersModel>>()
    //--------------------firestore-------------------
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

    }


    fun saveUsers(users: UsersModel) {
        apiServ.saveUsers(users)
            .addOnSuccessListener {
                Log.d(TAG, "Firebase, Users Document saved ")
            }
    }

    fun fetchUsers(users: UsersModel){
        apiServ.fitchUsers()
            .addOnSuccessListener{
                Log.d(TAG, "Firebase: Users Document fetched")
            }
    }


}