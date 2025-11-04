package com.example.usertestapplication.domain

import com.example.usertestapplication.data.ApiService
import com.example.usertestapplication.data.model.Users
import javax.inject.Inject

class UserRepository @Inject constructor(val apiService: ApiService) {

    suspend fun getUserData(): List<Users> {
        return apiService.getData()
    }

    suspend fun getUserDetailsData(id: Int): Users {
        return apiService.getUserDetail(id)
    }
}
