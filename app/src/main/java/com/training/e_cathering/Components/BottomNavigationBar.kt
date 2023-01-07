package com.training.e_cathering.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.training.e_cathering.Navigation.NavigationEnum
import com.training.e_cathering.R

@Composable
fun UserPageBottomNavigation(navController: NavController, bottomBarState : MutableState<Boolean>) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.History,
        BottomNavItem.Setting
    )
    AnimatedVisibility(visible = bottomBarState.value, enter = slideInVertically ( initialOffsetY = { it } ),  exit = slideOutVertically(targetOffsetY = { it })) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.Black,

            ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                    label = { Text(text = item.title,
                        fontSize = 9.sp) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home,NavigationEnum.HomeScreenActivity.name)
    object History: BottomNavItem("History",R.drawable.ic_baseline_history, NavigationEnum.HistoryScreenActivity.name)
    object Setting: BottomNavItem("Setting",R.drawable.ic_baseline_settings,NavigationEnum.SettingScreenActivity.name)

}