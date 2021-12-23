package com.example.car_spec.model




data class CarModel(

    val Make: String,
    val Model: String,
    val Color: String,
    val Year: String,
    val Title: String,
    val Location: String,
    val Id: Int,
    val Time: Int,
    val Price: Int,
    val Image: String = ""

)
