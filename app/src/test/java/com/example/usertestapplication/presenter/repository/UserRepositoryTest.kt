package com.example.usertestapplication.presenter.repository

import com.example.usertestapplication.data.ApiService
import com.example.usertestapplication.data.model.Users
import com.example.usertestapplication.domain.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var repository: UserRepository

    private val testUsers = listOf(
        Users(
            id = 1,
            name = "Test"
        ),
        Users(id = 2, name = "Test1")
    )

    @Before
    fun setup() {
        apiService = mockk()
        repository = UserRepository(apiService)
    }

    @Test
    fun `getUserData returns list of users`() = runTest {
        coEvery { apiService.getData() } returns testUsers

        val result = repository.getUserData()

        assertEquals(testUsers, result)
    }

    @Test
    fun `getUserDetailsData returns user by id`() = runTest {
        val user = testUsers[0]
        coEvery { apiService.getUserDetail(user.id) } returns user

        val result = repository.getUserDetailsData(user.id)

        assertEquals(user, result)
    }
}