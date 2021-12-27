package com.example.car_spec.model

import java.util.*


data class CarModel(

    val Make: String,
    val Model: String,
    val Color: String,
    val Year: String,
    val Title: String,
    val Location: String,
    val Id: Int,
    val Date: Date,
    val Price: Double,
    val isFavorite: Boolean,
    val Image: String = ""

)
