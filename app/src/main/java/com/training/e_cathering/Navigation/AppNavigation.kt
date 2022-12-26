package com.training.e_cathering.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.training.e_cathering.Screens.CreateCatheringScreen.CreateCatheringScreen
import com.training.e_cathering.Screens.CreateFrozenFoodScreen.CreateFrozenFoodScreen
import com.training.e_cathering.Screens.CreateProductScreen.CreateProductScreen
import com.training.e_cathering.Screens.CreateProductScreen.productViewModel
import com.training.e_cathering.Screens.HistoryScreenActivity.HistoryScreenActivity
import com.training.e_cathering.Screens.HomeScreen.HomeScreenActivity
import com.training.e_cathering.Screens.HomeScreen.HomeViewModel
import com.training.e_cathering.Screens.LoginScreen.LoginScreenActivity
import com.training.e_cathering.Screens.LoginScreen.LoginViewModel
import com.training.e_cathering.Screens.RegisterScreen.RegisterViewModel
import com.training.e_cathering.Screens.RegisterScreen.RegisterScreenActivity
import com.training.e_cathering.Screens.SettingScreenActivity.SettingScreenActivity
import com.training.e_cathering.Screens.SplashScreenActivity

@Composable
fun AppNavigation(navController: NavHostController ,homeViewModel: HomeViewModel = viewModel(), registerViewModel: RegisterViewModel = viewModel(), loginViewModel: LoginViewModel = viewModel(), productViewModel: productViewModel = viewModel()) {
 

    NavHost(navController=navController, startDestination = NavigationEnum.HomeScreenActivity.name){

        composable(NavigationEnum.SplashScreenActivity.name){
            SplashScreenActivity(navController)
        }

        composable(NavigationEnum.LoginScreenActivity.name){
            LoginScreenActivity(navController, loginViewModel)
        }

        composable(NavigationEnum.HomeScreenActivity.name){
            HomeScreenActivity(homeViewModel)
        }

        composable(NavigationEnum.RegisterScreenActivity.name){
            RegisterScreenActivity(registerViewModel)
        }
        composable(NavigationEnum.CreateProductActivity.name){
            CreateProductScreen(productViewModel)
        }
        composable(NavigationEnum.CreateFrozenFoodActivity.name){
            CreateFrozenFoodScreen()
        }
        composable(NavigationEnum.CreateCatheringActivity.name){
            CreateCatheringScreen()
        }

        composable(NavigationEnum.HistoryScreenActivity.name){
            HistoryScreenActivity()
        }

        composable(NavigationEnum.SettingScreenActivity.name){
            SettingScreenActivity()
        }
    }
}