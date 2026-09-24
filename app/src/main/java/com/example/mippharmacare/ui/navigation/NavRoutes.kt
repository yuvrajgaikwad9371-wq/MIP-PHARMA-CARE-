package com.example.mippharmacare.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Main : Screen("main")
    object SymptomList : Screen("symptoms")
    object AskPharmacist : Screen("ask_pharmacist")
    object MyMedicineList : Screen("my_medicines")
    object About : Screen("about")
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val testTag: String
) {
    object Home : BottomNavItem("home_tab", "Home", "nav_home")
    object Search : BottomNavItem("search_tab", "Search", "nav_search")
    object Pharmacy : BottomNavItem("pharmacy_tab", "Pharmacy", "nav_pharmacy")
    object Profile : BottomNavItem("profile_tab", "Profile", "nav_profile")
}
