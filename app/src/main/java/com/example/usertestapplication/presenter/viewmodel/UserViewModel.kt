package com.example.usertestapplication.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usertestapplication.data.UserResult
import com.example.usertestapplication.data.model.Users
import com.example.usertestapplication.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    var userData = MutableStateFlow<UserResult<List<Users>>>(UserResult.Loading)
    val _userData: StateFlow<UserResult<List<Users>>> = userData

    var userDetaildata = MutableStateFlow<UserResult<Users>>(UserResult.Loading)
    val _userDetailData: StateFlow<UserResult<Users>> = userDetaildata


    fun getData() {
        viewModelScope.launch {
            userData.value = UserResult.Loading
            try {
                userData.value = UserResult.Success(userRepository.getUserData())
            } catch (e: Exception) {
                userData.value = UserResult.Failure("Failed to fetch data")
            }
        }
    }

    fun getDataDetails(userId: Int) {
        viewModelScope.launch {
            userDetaildata.value = UserResult.Loading
            try {
                userDetaildata.value = UserResult.Success(userRepository.getUserDetailsData(userId))
            } catch (e: Exception) {
                userDetaildata.value = UserResult.Failure("Failed to fetch data")
            }
        }
    }
}