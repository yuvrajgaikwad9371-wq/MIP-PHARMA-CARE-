package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mippharmacare.ui.navigation.Screen
import com.example.mippharmacare.ui.screens.ask.AskPharmacistScreen
import com.example.mippharmacare.ui.screens.auth.AuthScreen
import com.example.mippharmacare.ui.screens.main.MainScreen
import com.example.mippharmacare.ui.screens.saved.MyMedicineListScreen
import com.example.mippharmacare.ui.screens.splash.SplashScreen
import com.example.mippharmacare.ui.screens.symptoms.SymptomScreen
import com.example.mippharmacare.ui.viewmodel.PharmaViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MIPPharmaCareApp()
                }
            }
        }
    }
}

@Composable
fun MIPPharmaCareApp() {
    val navController = rememberNavController()
    val viewModel: PharmaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // 1. Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Main Dashboard (Home, Search, Pharmacy, Profile)
        composable(Screen.Main.route) {
            MainScreen(
                viewModel = viewModel,
                onNavigateToSymptoms = {
                    navController.navigate(Screen.SymptomList.route)
                },
                onNavigateToAskPharmacist = { medicineName ->
                    val encoded = if (medicineName.isNotBlank()) "?med=${medicineName}" else ""
                    navController.navigate("ask_pharmacist$encoded")
                },
                onNavigateToAuth = {
                    navController.navigate("auth")
                },
                onNavigateToSavedMedicines = {
                    navController.navigate(Screen.MyMedicineList.route)
                }
            )
        }

        // 3. Health Problems & Symptoms Explorer
        composable(Screen.SymptomList.route) {
            SymptomScreen(
                viewModel = viewModel,
                onNavigateToAskPharmacist = {
                    navController.navigate("ask_pharmacist")
                }
            )
        }

        // 4. Ask a Pharmacist
        composable(
            route = "ask_pharmacist?med={med}",
            arguments = listOf(navArgument("med") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            val prefilledMed = backStackEntry.arguments?.getString("med") ?: ""
            AskPharmacistScreen(
                viewModel = viewModel,
                prefilledMedicineName = prefilledMed,
                onBack = { navController.popBackStack() }
            )
        }

        // 5. User Authentication (Sign Up & Log In)
        composable("auth") {
            AuthScreen(
                viewModel = viewModel,
                onAuthSuccess = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        // 6. My Medicine List
        composable(Screen.MyMedicineList.route) {
            MyMedicineListScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToAskAboutMedicine = { med ->
                    navController.navigate("ask_pharmacist?med=${med.medicineName}")
                }
            )
        }
    }
}
