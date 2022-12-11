package com.training.e_cathering.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.training.e_cathering.Screens.CreateCatheringScreen.CreateCatheringScreen
import com.training.e_cathering.Screens.CreateFrozenFoodScreen.CreateFrozenFoodScreen
import com.training.e_cathering.Screens.CreateProductScreen.CreateProductScreen
import com.training.e_cathering.Screens.HomeScreen.HomeScreenActivity
import com.training.e_cathering.Screens.LoginScreen.LoginScreenActivity
import com.training.e_cathering.Screens.SplashScreenActivity

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = NavigationEnum.CreateCatheringActivity.name){
        composable(NavigationEnum.SplashScreenActivity.name){
            SplashScreenActivity(navController)
        }

        composable(NavigationEnum.LoginScreenActivity.name){
            LoginScreenActivity(navController)
        }

        composable(NavigationEnum.HomeScreenActivity.name){
            HomeScreenActivity()
        }
        composable(NavigationEnum.CreateProductActivity.name){
            CreateProductScreen()
        }
        composable(NavigationEnum.CreateFrozenFoodActivity.name){
            CreateFrozenFoodScreen()
        }
        composable(NavigationEnum.CreateCatheringActivity.name){
            CreateCatheringScreen()
        }
    }
}