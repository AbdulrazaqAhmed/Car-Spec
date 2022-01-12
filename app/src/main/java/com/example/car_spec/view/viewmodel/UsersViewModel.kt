package com.example.car_spec.view.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_spec.model.UsersModel
import com.example.car_spec.repository.ApiServiceRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UsersViewModel"

class UsersViewModel : ViewModel() {
    //----------------------Reposetory---------------
    private val apiServ = ApiServiceRepo.get()

    //----------------------LiveData---------------
    val usersLiveData = MutableLiveData<UsersModel>()
    val usersErrorLiveData = MutableLiveData<List<String>>()
    //-----------------------upload users variables ---------------------
    val uploadUsersImageLiveData = MutableLiveData<String>()
    val uploadUsersImageErrorLiveData = MutableLiveData<String>()


    //--------------------Firestore-------------------
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

    fun fetchUsers() {
val userId = FirebaseAuth.getInstance().uid
        apiServ.fitchUsers(userId!!)
            .addOnSuccessListener {
                Log.d(TAG, "Firebase: Users Document fetched")

                var user = it.documents[0].toObject(UsersModel::class.java)
                usersLiveData.postValue(user!!)
            }
    }

    fun uploadUsersPhoto(profileimage: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
//            val profileimage = FirebaseAuth.getInstance().uid

            try {

                val responseImage = apiServ.uploadProfileImage(profileimage)

                responseImage.addOnSuccessListener { taskSnapshot ->

                    Log.d(TAG, taskSnapshot.metadata?.name.toString())
                    if (responseImage.isSuccessful) {
                        uploadUsersImageLiveData.postValue("successful")
                    } else {

                        uploadUsersImageErrorLiveData.postValue("")
                        Log.d(TAG, "exception, uploadImageErrorLiveData ")
                    }

                }.addOnFailureListener {

                    Log.d(TAG, it.message.toString())
                    usersErrorLiveData.postValue(listOf(it.message.toString()))
                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())

            }
        }
    }


}