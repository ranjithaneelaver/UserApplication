package com.example.usertestapplication.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.usertestapplication.presenter.ui.DetailScreen
import com.example.usertestapplication.presenter.ui.UserScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.UserHome.path ){
        addUserScreen(navController,this)
        addDetailScreen(navController,this)

    }
}

fun addUserScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
   navGraphBuilder.composable(route = NavRoute.UserHome.path){
       UserScreen(
           navigateHome = { users ->
               println(users)
               navController.navigate(
                   // 2. Use the created route string as the destination
                   NavRoute.Detail.createRoute(users.id)
               )
               // NavRoute.Detail.createRoute(users.id)
           }
       )

   }
}

fun addDetailScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
   navGraphBuilder.composable(route = NavRoute.Detail.path,arguments = listOf(
       navArgument("userId") { type = NavType.IntType } // Define argument details
   )){ backStackEntry->
       val userId = backStackEntry.arguments?.getInt("userId")
       println(userId)
       DetailScreen(userId, popBackStack = { navController.popBackStack() }) {
           navController.popBackStack()
       }
   }
}
