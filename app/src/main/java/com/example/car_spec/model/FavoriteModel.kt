package com.example.car_spec.model

import com.google.firebase.firestore.DocumentId

data class FavoriteModel(
    var make: String = "",
    var model: String = "",
    var color: String = "",
    var year: String = "",
    var title: String = "",
    var location: String = "",
    var id: Int = 0,
    var date: String = "",
    var price: Double = 0.0,
    var favorite: Boolean = false,
    var image: String = "",
    var description: String = "",
    @DocumentId
    var documentId : String = "",
    var userId : String = ""
)
