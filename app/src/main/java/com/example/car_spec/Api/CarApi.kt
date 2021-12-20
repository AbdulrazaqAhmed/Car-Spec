package com.example.car_spec.Api

import retrofit2.Response
import retrofit2.http.GET

interface CarApi {
    @GET()
    suspend fun getCars() : Response<CarApi>
}