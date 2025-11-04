package com.example.usertestapplication.presenter

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.usertestapplication.data.UserResult
import com.example.usertestapplication.data.model.Users
import com.example.usertestapplication.presenter.ui.UserScreen
import com.example.usertestapplication.presenter.viewmodel.UserViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testUsers = listOf(
        Users(id = 1, name = "Test"),
        Users(id = 2, name = "Test1")
    )

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)
        every { viewModel._userData } returns MutableStateFlow(UserResult.Success(testUsers))
    }

    @Test
    fun userScreen_displaysUserList_onSuccess() {
        composeTestRule.setContent {
            UserScreen(navigateHome = {}, viewModel = viewModel)
        }

        // Verify that user names are displayed
        composeTestRule.onNodeWithText("Test").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test1").assertIsDisplayed()

        // Verify that "View Details" buttons are present
        composeTestRule.onAllNodesWithText("View Details")
            .assertCountEquals(testUsers.size)
    }
}
