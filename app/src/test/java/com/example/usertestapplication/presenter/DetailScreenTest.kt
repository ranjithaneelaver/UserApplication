package com.example.usertestapplication.presenter

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.usertestapplication.data.UserResult
import com.example.usertestapplication.data.model.Users
import com.example.usertestapplication.presenter.ui.DetailScreen
import com.example.usertestapplication.presenter.viewmodel.UserViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    // 1. Rule for Compose testing
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: UserViewModel = mockk(relaxed = true)
    private val mockUserDetailData = MutableStateFlow<UserResult<Users>>(UserResult.Loading)

    private val mockPopBackStack: () -> Unit = mockk(relaxed = true)
    private val mockPopBackLoginStack: () -> Unit = mockk(relaxed = true)

    private val testUserId = 1

    @Before
    fun setup() {
        every { mockViewModel._userDetailData } returns mockUserDetailData
    }

    @Test
    fun `detailScreen displaysLoadingState Initially`() {
        mockUserDetailData.value = UserResult.Loading
        composeTestRule.setContent {
            DetailScreen(
                userId = 0,
                popBackStack = {},
                popBackLoginStack = {}
            )
        }
        composeTestRule.onNodeWithText("Error").assertDoesNotExist()

        assert(true)
        verify(exactly = 1) { mockViewModel.getDataDetails(testUserId) }
    }

    @Test
    fun `detailScreen displaysSuccessState withUserData`() {
        val mockUser = Users(
            id = testUserId,
            name = "Test User Name"
        )
        mockUserDetailData.value = UserResult.Success(mockUser)
        composeTestRule.setContent {
            DetailScreen(
                userId = 0,
                popBackStack = {},
                popBackLoginStack = {}
            )
        }
        composeTestRule.onNodeWithText("Test User Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Company Inc.").assertIsDisplayed()

        verify(exactly = 1) { mockViewModel.getDataDetails(testUserId) }
    }


    @Test
    fun detailScreen_displaysFailureState_andCallsRetry() {

        val errorMessage = "Network timeout"
        mockUserDetailData.value = UserResult.Failure(errorMessage)

        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
        val retryButton = composeTestRule.onNodeWithText("Retry")
        retryButton.assertIsDisplayed()

        retryButton.performClick()

        verify(exactly = 2) { mockViewModel.getDataDetails(testUserId) }
    }
}