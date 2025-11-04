package com.example.usertestapplication.presenter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.AsyncImage
import com.example.usertestapplication.data.UserResult
import com.example.usertestapplication.presenter.viewmodel.UserViewModel

@Composable
fun DetailScreen(userId: Int?, popBackStack: () -> Unit, popBackLoginStack: () -> Unit) {
    val viewModel: UserViewModel = hiltViewModel()
    val userDetailDataValue by viewModel._userDetailData.collectAsState()

    LaunchedEffect(userId) {
        userId?.let { viewModel.getDataDetails(it) }
    }

    when (userDetailDataValue) {
        is UserResult.Loading -> {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is UserResult.Success -> {
            val users = (userDetailDataValue as UserResult.Success)

            Column(modifier =  Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    users.users.let {
                        Text(
                            text = it.name,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                        Text(
                            text = it.company,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            text = it.email,
                            color = Color.Black,
                            fontSize = 14.sp
                        )

                        Card(
                            modifier = Modifier.size(100.dp),
                            shape = CircleShape
                        ) {
                            LoadImageFromUri(it.photo)
                        }
                    }

                }
            }
        }

        is UserResult.Failure -> {
            val errorMessage = (userDetailDataValue as UserResult.Failure).error
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Error: $errorMessage")
                    Button(onClick = { viewModel.getDataDetails(userId ?: 1) }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Composable
fun LoadImageFromUri(imageUri: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUri,
        contentDescription = "Image loaded from URI",
        modifier = modifier.fillMaxWidth()
    )
}