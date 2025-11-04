package com.example.usertestapplication.presenter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.usertestapplication.data.UserResult
import com.example.usertestapplication.data.model.Users
import com.example.usertestapplication.presenter.viewmodel.UserViewModel

@Composable
fun UserScreen(navigateHome: (Users) -> Unit, viewModel: UserViewModel = hiltViewModel()) {
    LoadData(navigateHome, viewModel)
}

@Composable
fun LoadData(navigateHome: (Users) -> Unit, viewModel: UserViewModel) {
    val userDataValue by viewModel._userData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDataOnce()
    }


    when (userDataValue) {
        is UserResult.Loading -> {
            Column(modifier =  Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is UserResult.Success -> {
            val data = (userDataValue as UserResult.Success)
            LazyColumn {
                items(data.users.size, key = { index -> data.users[index].id }) { index ->
                    LoadUserData(navigateHome, data.users[index])
                }
            }
        }

        is UserResult.Failure -> {
            val errorMessage = (userDataValue as UserResult.Failure).error
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Error: $errorMessage")
                    Button(onClick = { viewModel.getData() }) {
                        Text("Retry")
                    }
                }
            }

        }
    }
}


@Composable
fun LoadUserData(navigateHome: (Users) -> Unit, users: Users) {
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Column() {
            Text(
                text = users.name,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(10.dp)
            )
            Button(
                onClick = { navigateHome(users) },
                modifier = Modifier
                    .align(Alignment.End)
                    .wrapContentWidth()
                    .height(35.dp)
            ) {
                Text(text = "View Details", fontSize = 12.sp)
            }

            HorizontalDivider(
                color = Color.Cyan,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}
