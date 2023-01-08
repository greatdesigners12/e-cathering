package com.training.e_cathering.Navigation

import android.content.Context
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
import com.training.e_cathering.Screens.AdminHomeScreen.AdminHomeScreen
import com.training.e_cathering.Screens.AdminHomeScreen.AdminHomeViewModel
import com.training.e_cathering.Screens.CartDetailActivity.CartDetailActivity
import com.training.e_cathering.Screens.CartDetailActivity.CartDetailViewModel
import com.training.e_cathering.Screens.CatheringApprovalScreen.CatheringApprovalScreen
import com.training.e_cathering.Screens.CatheringApprovalScreen.CatheringApprovalViewModel
import com.training.e_cathering.Screens.CatheringDetailScreen.CatheringDetailScreen
import com.training.e_cathering.Screens.CatheringDetailScreen.CatheringDetailViewModel
import com.training.e_cathering.Screens.CatheringHomeScreen.CatheringHomeScreen
import com.training.e_cathering.Screens.CatheringHomeScreen.CatheringHomeViewModel
import com.training.e_cathering.Screens.CatheringListScreen.CatheringListScreen
import com.training.e_cathering.Screens.CatheringListScreen.CatheringListViewModel
import com.training.e_cathering.Screens.CatheringProfileScreen.CatheringProfileScreen
import com.training.e_cathering.Screens.CatheringProfileScreen.CatheringProfileViewModel
import com.training.e_cathering.Screens.CreateCatheringScreenActivity.CreateCatheringScreenActivity
import com.training.e_cathering.Screens.CreateCatheringScreenActivity.CreateCatheringScreenActivityViewModel
import com.training.e_cathering.Screens.CreateProductScreen.CreateProductScreen
import com.training.e_cathering.Screens.CreateProductScreen.productViewModel
import com.training.e_cathering.Screens.HistoryScreenActivity.HistoryScreenActivity
import com.training.e_cathering.Screens.HistoryScreenActivity.HistoryTransactionViewModel
import com.training.e_cathering.Screens.HomeScreen.HomeScreenActivity
import com.training.e_cathering.Screens.HomeScreen.HomeViewModel
import com.training.e_cathering.Screens.LoginScreen.LoginScreenActivity
import com.training.e_cathering.Screens.LoginScreen.LoginViewModel
import com.training.e_cathering.Screens.OrderDetailScreen.OrderDetailScreen
import com.training.e_cathering.Screens.OrderDetailScreen.OrderDetailViewModel
import com.training.e_cathering.Screens.OrderListScreen.OrderListScreen
import com.training.e_cathering.Screens.OrderListScreen.OrderListViewModel
import com.training.e_cathering.Screens.ProductDetailScreen.ProductDetailScreen
import com.training.e_cathering.Screens.ProductDetailScreen.ProductDetailViewModel
import com.training.e_cathering.Screens.ProductManagementScreen.ProductManagementScreen
import com.training.e_cathering.Screens.ProductManagementScreen.ProductManagementViewModel
import com.training.e_cathering.Screens.RegisterScreen.RegisterViewModel
import com.training.e_cathering.Screens.RegisterScreen.RegisterScreenActivity
import com.training.e_cathering.Screens.ResetPasswordScreen.ResetPasswordActivity
import com.training.e_cathering.Screens.ResetPasswordScreen.ResetPasswordViewModel
import com.training.e_cathering.Screens.SettingScreenActivity.SettingScreenActivity
import com.training.e_cathering.Screens.SettingScreenActivity.SettingViewModel
import com.training.e_cathering.Screens.SplashScreenActivity
import com.training.e_cathering.Screens.TransactionDetailScreen.TransactionDetailActivity
import com.training.e_cathering.Screens.TransactionDetailScreen.TransactionDetailViewModel
import com.training.e_cathering.Screens.UpdateProductActivity.UpdateProductActivity
import com.training.e_cathering.Screens.UpdateProductActivity.UpdateProductViewModel

@Composable
fun AppNavigation(homeViewModel: HomeViewModel = viewModel(),
                  registerViewModel: RegisterViewModel = viewModel(),
                  loginViewModel: LoginViewModel = viewModel(),
                  productViewModel: productViewModel = viewModel(),
                  productDetailViewModel : ProductDetailViewModel= viewModel(),
                  catheringListViewModel: CatheringListViewModel = viewModel(),
                  catheringDetailViewModel: CatheringDetailViewModel = viewModel(),
                  catheringProfileViewModel: CatheringProfileViewModel = viewModel(),
                  catheringApprovalViewModel: CatheringApprovalViewModel = viewModel(),
                  cartDetailViewModel: CartDetailViewModel = viewModel(),
                  transactionViewModel: HistoryTransactionViewModel = viewModel(),
                    transactionDetailViewModel: TransactionDetailViewModel = viewModel(),
                    settingViewModel: SettingViewModel = viewModel(),
                  orderDetailViewModel: OrderDetailViewModel = viewModel(),
                  orderListViewModel: OrderListViewModel = viewModel(),
                  createCatheringScreenActivityViewModel: CreateCatheringScreenActivityViewModel = viewModel(),
                  resetPasswordViewModel: ResetPasswordViewModel = viewModel(),
                    productManagementViewModel: ProductManagementViewModel = viewModel(),
                  catheringHomeViewModel: CatheringHomeViewModel = viewModel(),
                  adminHomeViewModel: AdminHomeViewModel = viewModel(),
                    updateProductViewModel: UpdateProductViewModel = viewModel()) {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val appBarState = rememberSaveable { (mutableStateOf(false)) }
    val navController = rememberNavController()

// Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

// Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        NavigationEnum.HistoryScreenActivity.name -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true
            appBarState.value = false
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

        NavigationEnum.TransactionDetailActivity.name + "/{transactionId}" -> {
            bottomBarState.value = false
            appBarState.value = true
        }
        NavigationEnum.OrderListScreenActivity.name->{
            bottomBarState.value = false
            appBarState.value = true
        }
        NavigationEnum.OrderDetailScreenActivity.name+"/{groupId}"->{
            bottomBarState.value = false
            appBarState.value = true
        }
        NavigationEnum.CatheringProfileScreenActivity.name->{
            bottomBarState.value = false
            appBarState.value = true
        }
        NavigationEnum.CatheringApprovalScreenActivity.name->{
            bottomBarState.value = false
            appBarState.value = true
        }
        NavigationEnum.CreateCatheringScreenActivity.name->{
            bottomBarState.value = false
            appBarState.value = false
        }
        NavigationEnum.CatheringHomeScreenActivity.name->{
            bottomBarState.value = false
            appBarState.value = false
        }
        NavigationEnum.AdminHomeScreenActivity.name->{
            bottomBarState.value = false
            appBarState.value = false
        }

        NavigationEnum.ResetPasswordActivity.name -> {
            bottomBarState.value = false
            appBarState.value = true
        }

        NavigationEnum.SettingScreenActivity.name -> {
            bottomBarState.value = true
            appBarState.value = false
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
    ){

        NavHost(navController=navController, startDestination = NavigationEnum.CatheringHomeScreenActivity.name){

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
                    ?.let { it1 -> CatheringListScreen(navController ,it1, catheringListViewModel) }
            }

            composable(NavigationEnum.CreateProductActivity.name + "/{product_id}", arguments = listOf(
                navArgument("product_id"){type = NavType.StringType})){
                it.arguments?.getString("product_id")
                    ?.let { it1 -> CreateProductScreen(navController ,it1, productViewModel) }
            }

            composable(NavigationEnum.RegisterScreenActivity.name){
                RegisterScreenActivity(registerViewModel)
            }
            composable(NavigationEnum.CreateProductActivity.name){
                CreateProductScreen(navController, null, productViewModel)
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
                    ?.let { it1 ->
                        CartDetailActivity(
                            navController,
                            it1,
                            cartDetailViewModel
                        )
                    }
            }
            composable(NavigationEnum.CatheringDetailActivity.name + "/{catheringId}", arguments = listOf(
                navArgument("catheringId"){type = NavType.StringType}
            )){
                it.arguments?.getString("catheringId")
                    ?.let { it1 -> CatheringDetailScreen(navController, it1, catheringDetailViewModel) }
            }
            composable(NavigationEnum.UpdateProductActivity.name + "/{product_id}", arguments = listOf(
                navArgument("product_id"){type = NavType.StringType}
            )){
                it.arguments?.getString("product_id")
                    ?.let { it1 -> CreateProductScreen(navController, it1, productViewModel) }
            }
            composable(NavigationEnum.ProductManagementActivity.name){
                ProductManagementScreen(navController, productManagementViewModel)
            }
            composable(NavigationEnum.CatheringDetailActivity.name + "/{catheringId}", arguments = listOf(
                navArgument("catheringId"){type = NavType.StringType}
            )){
                it.arguments?.getString("catheringId")
                    ?.let { it1 -> CatheringDetailScreen(navController, it1, catheringDetailViewModel) }
            }

            composable(NavigationEnum.HistoryScreenActivity.name){
                HistoryScreenActivity(navController, transactionViewModel)
            }

            composable(NavigationEnum.TransactionDetailActivity.name + "/{transactionId}", arguments = listOf(
                navArgument("transactionId"){type = NavType.StringType}
            )){
                it.arguments?.getString("transactionId")?.let { it1 -> TransactionDetailActivity(navController = navController, id = it1, viewModel = transactionDetailViewModel) }
            }

            composable(NavigationEnum.SettingScreenActivity.name){
                SettingScreenActivity(navController, settingViewModel)
            }
            composable(NavigationEnum.CatheringProfileScreenActivity.name){
               CatheringProfileScreen( catheringProfileViewModel)
                 }
            composable(NavigationEnum.CatheringApprovalScreenActivity.name){
                CatheringApprovalScreen(catheringApprovalViewModel)
            }
            composable(NavigationEnum.OrderListScreenActivity.name){
                OrderListScreen(navController = navController,"2",orderListViewModel)
            }
            composable(NavigationEnum.OrderDetailScreenActivity.name+ "/{groupId}", arguments =  listOf(
                navArgument("groupId"){type = NavType.StringType}
            )
            ){
                it.arguments?.getString("groupId")
                    ?.let { it1 -> OrderDetailScreen(navController = navController, it1, orderDetailViewModel) }

            }
            composable(NavigationEnum.CreateCatheringScreenActivity.name){
                CreateCatheringScreenActivity(createCatheringScreenActivityViewModel)
            }
            composable(NavigationEnum.CatheringHomeScreenActivity.name){
                CatheringHomeScreen(navController = navController, catheringHomeViewModel)
            }
            composable(NavigationEnum.AdminHomeScreenActivity.name){
                AdminHomeScreen(navController = navController, adminHomeViewModel)
            }
            composable(NavigationEnum.ResetPasswordActivity.name){
                ResetPasswordActivity(navController, resetPasswordViewModel)
            }

        }

    }
}