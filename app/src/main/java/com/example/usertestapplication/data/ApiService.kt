package com.example.usertestapplication.data

import com.example.usertestapplication.data.model.Users
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getData(): List<Users>

    @GET("users/{id}")
    suspend fun getUserDetail(@Path("id") id:Int): Users
}