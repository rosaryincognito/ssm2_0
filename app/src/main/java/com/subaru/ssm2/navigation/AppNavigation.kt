package com.subaru.ssm2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.subaru.ssm2.ui.connection.ConnectionScreen
import com.subaru.ssm2.ui.dashboard.DashboardScreen
import com.subaru.ssm2.ui.livedata.LiveDataScreen
import com.subaru.ssm2.ui.dtc.DtcScreen
import com.subaru.ssm2.ui.logging.LoggingScreen
import com.subaru.ssm2.ui.settings.SettingsScreen
import com.subaru.ssm2.ui.about.AboutScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Connection
    ) {

        composable(Routes.Connection) {

            ConnectionScreen(

                onSearchClick = {
                    // позже Bluetooth Scan
                },

                onConnectClick = {

                    navController.navigate(Routes.Dashboard) {

                        popUpTo(Routes.Connection) {
                            inclusive = true
                        }

                    }

                }

            )

        }

        composable(Routes.Dashboard) {

            DashboardScreen(

                onConnection = {

                    navController.navigate(Routes.Connection)

                },

                onLiveData = {

                    navController.navigate(Routes.LiveData)

                },

                onDtc = {

                    navController.navigate(Routes.Dtc)

                },

                onLogging = {

                    navController.navigate(Routes.Logging)

                },

                onSettings = {

                    navController.navigate(Routes.Settings)

                },

                onAbout = {

                    navController.navigate(Routes.About)

                }

            )

        }

        composable(Routes.LiveData) {

            LiveDataScreen()

        }

        composable(Routes.Dtc) {

            DtcScreen()

        }

        composable(Routes.Logging) {

            LoggingScreen()

        }

        composable(Routes.Settings) {

            SettingsScreen()

        }

        composable(Routes.About) {

            AboutScreen()

        }

    }

}