package com.example.usertestapplication.data

sealed class UserResult<out T> {
    data class Success<out T>(val users: T) : UserResult<T>()
    data class Failure(val error: String) : UserResult<Nothing>()
    object Loading : UserResult<Nothing>()
}