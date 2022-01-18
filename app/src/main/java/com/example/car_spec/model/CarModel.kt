package com.example.car_spec.model

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import java.util.*


data class CarModel(

    var make: String = "",
    var model: String = "",
    var color: String = "",
    var year: String = "",
    var title: String = "",
    var location: String = "",
    var id: Int = 0,
    var date: String = "",
    var price: Double = 0.0,
    var favorite: Boolean = true,
    var image: String = "",
    var description: String = "",
    val userId: String = "",
    @DocumentId
    val documentId : String= ""

)
