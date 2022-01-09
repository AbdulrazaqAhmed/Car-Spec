package com.example.car_spec.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class CarModel(

    val make: String = "",
    val model: String = "",
    val color: String = "",
    val year: String = "",
    val title: String = "",
    val location: String = "",
    val id: Int = 0,
    val date: String = "",
    val price: Double = 0.0,
    val favorite: Boolean = false,
    val image: String = "",
    val description: String = "",
    val userId: String = ""


)
