package com.training.e_cathering.Navigation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.training.e_cathering.Components.UserPageBottomNavigation
import com.training.e_cathering.Screens.CatheringDetailScreen.CatheringDetailScreen
import com.training.e_cathering.Screens.CatheringDetailScreen.CatheringDetailViewModel
import com.training.e_cathering.Screens.CatheringListScreen.CatheringListScreen
import com.training.e_cathering.Screens.CatheringListScreen.CatheringListViewModel
import com.training.e_cathering.Screens.CreateCatheringScreen.CreateCatheringScreen
import com.training.e_cathering.Screens.CreateFrozenFoodScreen.CreateFrozenFoodScreen
import com.training.e_cathering.Screens.CreateProductScreen.CreateProductScreen
import com.training.e_cathering.Screens.CreateProductScreen.productViewModel
import com.training.e_cathering.Screens.HistoryScreenActivity.HistoryScreenActivity
import com.training.e_cathering.Screens.HomeScreen.HomeScreenActivity
import com.training.e_cathering.Screens.HomeScreen.HomeViewModel
import com.training.e_cathering.Screens.LoginScreen.LoginScreenActivity
import com.training.e_cathering.Screens.LoginScreen.LoginViewModel
import com.training.e_cathering.Screens.ProductDetailScreen.ProductDetailScreen
import com.training.e_cathering.Screens.ProductDetailScreen.ProductDetailViewModel
import com.training.e_cathering.Screens.RegisterScreen.RegisterViewModel
import com.training.e_cathering.Screens.RegisterScreen.RegisterScreenActivity
import com.training.e_cathering.Screens.SettingScreenActivity.SettingScreenActivity
import com.training.e_cathering.Screens.SplashScreenActivity

@Composable
fun AppNavigation(homeViewModel: HomeViewModel = viewModel(),
                  registerViewModel: RegisterViewModel = viewModel(),
                  loginViewModel: LoginViewModel = viewModel(),
                  productViewModel: productViewModel = viewModel(),
                  productDetailViewModel : ProductDetailViewModel= viewModel(),
                  catheringListViewModel: CatheringListViewModel = viewModel(),
                  catheringDetailViewModel: CatheringDetailViewModel = viewModel()) {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val appBarState = rememberSaveable { (mutableStateOf(false)) }
    val navController = rememberNavController()

// Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

// Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        NavigationEnum.HistoryScreenActivity.name -> {
            // Show BottomBar and TopBar
            bottomBarState.value = false
            appBarState.value = true
        }
        NavigationEnum.HomeScreenActivity.name -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true
            appBarState.value = false
        }

        NavigationEnum.LoginScreenActivity.name -> {
            // Show BottomBar and TopBar
            bottomBarState.value = false
            appBarState.value = false
        }

        NavigationEnum.CatheringDetailActivity.name + "/{catheringId}" -> {
            // Show BottomBar and TopBar
            bottomBarState.value = false
            appBarState.value = true
        }

        NavigationEnum.CatheringListActivity.name + "/{genre}" -> {
            bottomBarState.value = false
            appBarState.value = true
        }

    }

    Scaffold(
        topBar = {
            if(appBarState.value){
                AnimatedVisibility(visible = appBarState.value, enter = slideInHorizontally  ( initialOffsetX = { it } ), exit = slideOutHorizontally  ( targetOffsetX = { it } )) {
                    TopAppBar(modifier = Modifier.background(MaterialTheme.colors.primary), ) {
                        Row(Modifier.fillMaxSize()){
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Filled.ArrowLeft, "Back button")
                            }
                        }
                    }
                }
            }

            },
        bottomBar = { UserPageBottomNavigation(navController = navController, bottomBarState) }
    ) {

        NavHost(navController=navController, startDestination = NavigationEnum.LoginScreenActivity.name){

            composable(NavigationEnum.SplashScreenActivity.name){
                SplashScreenActivity(navController)
            }

            composable(NavigationEnum.LoginScreenActivity.name){
                LoginScreenActivity(navController, loginViewModel)
            }

            composable(NavigationEnum.HomeScreenActivity.name){
                HomeScreenActivity(homeViewModel, navController)
            }

            composable(NavigationEnum.CatheringListActivity.name + "/{genre}", arguments = listOf(
                navArgument("genre"){type = NavType.StringType})){
                it.arguments?.getString("genre")
                    ?.let { it1 -> CatheringListScreen(it1, catheringListViewModel) }
            }

            composable(NavigationEnum.RegisterScreenActivity.name){
                RegisterScreenActivity(registerViewModel)
            }
            composable(NavigationEnum.CreateProductActivity.name){
                CreateProductScreen(productViewModel)
            }

            composable(NavigationEnum.ProductDetailActivity.name + "/{productId}", arguments = listOf(
                navArgument("productId"){type = NavType.StringType}
            )){
                it.arguments?.getString("productId")
                    ?.let { it1 -> ProductDetailScreen(navController, it1, productDetailViewModel) }
            }

            composable(NavigationEnum.CartDetailActivity.name + "/{catheringId}", arguments = listOf(
                navArgument("catheringId"){type = NavType.StringType}
            )){
                it.arguments?.getString("catheringId")
                    ?.let { it1 -> ProductDetailScreen(navController, it1, productDetailViewModel) }
            }

            composable(NavigationEnum.CatheringDetailActivity.name + "/{catheringId}", arguments = listOf(
                navArgument("catheringId"){type = NavType.StringType}
            )){
                it.arguments?.getString("catheringId")
                    ?.let { it1 -> CatheringDetailScreen(navController, it1, catheringDetailViewModel) }
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
}