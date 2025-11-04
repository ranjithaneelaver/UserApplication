package com.example.usertestapplication.util

sealed class NavRoute(val path:String) {

    object Detail : NavRoute("Detail/{userId}") {
        fun createRoute(userId: Int) = "Detail/$userId"
    }
    object UserHome: NavRoute("UserHome")
}