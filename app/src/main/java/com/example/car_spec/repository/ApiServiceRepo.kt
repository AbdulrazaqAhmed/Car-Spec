package com.example.car_spec.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.car_spec.Api.CarApi
import com.example.car_spec.accessablity.SHARED_PREF_FILE
import com.example.car_spec.model.CarModel
import com.example.car_spec.model.UsersModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = ""
const val USER_ID = "USER_ID"

class ApiServiceRepo(context: Context) {

    //car firebase to get instance
    val storageCarReference = FirebaseStorage.getInstance().getReference("image")
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    private val userId = sharedPreferences.getString(USER_ID, "")
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = Firebase.firestore.collection("user")
    private val car = Firebase.firestore.collection("car")
    var storageProfileReference = FirebaseStorage.getInstance().getReference("profile images")

    //-----------------------------Users Save fun------------------------------------------
    fun saveUsers(users: UsersModel) =
        firestore.collection("users").document(FirebaseAuth.getInstance().uid.toString())
            .set(users) // for get instead get

    //---------------------------------update users info -----------------------------------------
    fun updateUsers(users: UsersModel) =
        firestore.collection("users").document(FirebaseAuth.getInstance().uid.toString())
            .set(users)


    //____________________________---- upload profile image---________________________________----done
    fun uploadProfileImage(profileimage: Uri) =
        storageProfileReference.child(FirebaseAuth.getInstance().uid.toString())
            .putFile(profileimage)

    //-----------------------------Users Get fun------------------------------------------------done
    fun fitchUsers(userId: String) =
        firestore.collection("users").whereEqualTo("userId", userId).get()

    //-----------------------------------Delete user fun---------------------------------------------done

    fun deleteUserProfile() = firestore.document(FirebaseAuth.getInstance().uid.toString()).delete()

    //-----------------------------car save collection------------------------------------done
    fun save(car: CarModel) =
        firestore.collection("car")
            .add(car)

    //---------------------------------Upload Car Image Fun-----------------------------------------done


//    val time: String? = Calendar.getInstance().getTime().toString()
//    val storageRef = storageCarReference.child("/documentId_" + FirebaseAuth.getInstance().uid.toString() + "_" + time + "/") //put in variable

    fun uploadImage(imge: Uri, imagename: String) =
        storageCarReference.child(imagename).putFile(imge)

    //--------------------------------car fitch fun--------------------------------------------done
    fun fitch() =
        firestore.collection("car")
            .get()
    //------------------------------- fetch MyCar fun -----------------------------------------done
    fun fitchMyCars() =
        firestore.collection("car")
            .whereEqualTo("userId", FirebaseAuth.getInstance().uid.toString())
            .get()

    //------------------------------ detele mycar --------------------------------------------
fun deleteMycars(car: CarModel) =
    firestore.collection("car").document(FirebaseAuth.getInstance().uid.toString()).delete()


    //-------------------------------update My Cars info ---------------------------------------
    fun updateMycarInfo(car: CarModel)=
        firestore.collection("car").document(FirebaseAuth.getInstance().uid.toString()).set(car)
    //------------------------------- favorite add --------------------------------------------
    fun addFavorites(car: CarModel)=
        firestore.collection("car").add(car)

    //------------------------------favorite get ----------------------------------------------
    fun fitchFavorites() =
        firestore.collection("car")
            .get()

    //------------------------------------Remove Favorite -------------------------------------------
    fun removeFavorit(car: CarModel) =
        firestore.collection("car")


    //------------------------------------

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