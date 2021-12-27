package com.example.car_spec.repository

import android.content.Context
import android.util.Log
import com.example.car_spec.Api.CarApi
import com.example.car_spec.accessablity.SHARED_PREF_FILE
import com.example.car_spec.model.CarModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.auth.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception

private const val BASE_URL = ""
const val USER_ID = "USER_ID"

class ApiServiceRepo(context: Context) {

    //car firebase to get instance
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    private val userId = sharedPreferences.getString(USER_ID, "")
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = firestore.collection("user")
    private val car = firestore.collection("car")
    private lateinit var carRepo: ApiServiceRepo


//suspend fun getCars()= users.add && user.get


    suspend fun getCars() = firestore.firestoreSettings

    fun save(car: CarModel) =
        firestore.collection("car")
            .add(car)

    fun fitch() =
        firestore.collection("car")
            .get()

    companion object {
        private var instance: ApiServiceRepo? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiServiceRepo(context)

        }

        fun get(): ApiServiceRepo {
            return instance ?: throw Exception("ApiServiceRepository must be initialized")
        }

    }


}