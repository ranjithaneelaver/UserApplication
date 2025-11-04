package com.example.usertestapplication.presenter

import app.cash.turbine.test
import com.example.usertestapplication.data.UserResult
import com.example.usertestapplication.data.model.Users
import com.example.usertestapplication.domain.UserRepository
import com.example.usertestapplication.presenter.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: UserViewModel
    private var dispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        userRepository = mockk()
        viewModel = UserViewModel(userRepository)
    }

    @Test
    fun `Loading should be instance of UserResult`() {
        val result = UserResult.Loading
        TestCase.assertTrue(result is UserResult.Loading)
    }

    @Test
    fun `Success should contain correct data`() {
        val mockUsers = listOf(Users(1, "Test"), Users(2, "Test1"))
        val result = UserResult.Success(mockUsers)

        TestCase.assertTrue(result is UserResult.Success)
        TestCase.assertEquals(mockUsers, result.users)
    }

    @Test
    fun `Failure should contain correct message`() {
        val errorMessage = "Something went wrong"
        val result = UserResult.Failure(errorMessage)

        TestCase.assertTrue(result is UserResult.Failure)
        TestCase.assertEquals(errorMessage, result.error)
    }

    @Test
    fun `getData emits Loading then Success`() = runTest {
        val mockUsers = listOf(Users(1, "Test"), Users(2, "Test1"))

        coEvery { userRepository.getUserData() } returns mockUsers

        viewModel._userData.test {
            viewModel.getData()

            TestCase.assertTrue(awaitItem() is UserResult.Loading)
            val result = awaitItem()
            TestCase.assertTrue(result is UserResult.Success)
            TestCase.assertEquals(mockUsers, (result as UserResult.Success).users)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `getData emits Failure on exception`() = runTest {
        coEvery { userRepository.getUserData() } throws Exception("Network error")

        viewModel._userData.test {
            viewModel.getData()

            assert(awaitItem() is UserResult.Loading)
            val result = awaitItem()
            assert(result is UserResult.Failure)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getDetailData emits Loading then Success`() = runTest {
        val mockUsers = Users(1, "Test")

        coEvery { userRepository.getUserDetailsData(1) } returns mockUsers

        viewModel._userDetailData.test {
            viewModel.getDataDetails(1)

            TestCase.assertTrue(awaitItem() is UserResult.Loading)
            val result = awaitItem()
            TestCase.assertTrue(result is UserResult.Success)
            TestCase.assertEquals(mockUsers, (result as UserResult.Success).users)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `getDetailData emits Failure on exception`() = runTest {
        coEvery { userRepository.getUserData() } throws Exception("Network error")

        viewModel._userDetailData.test {
            viewModel.getDataDetails(0)

            assert(awaitItem() is UserResult.Loading)
            val result = awaitItem()
            assert(result is UserResult.Failure)
            cancelAndIgnoreRemainingEvents()
        }
    }
}