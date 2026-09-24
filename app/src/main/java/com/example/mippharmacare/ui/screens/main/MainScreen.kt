package com.example.mippharmacare.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mippharmacare.data.model.MedicineEntity
import com.example.mippharmacare.ui.components.EmergencyHelpBottomSheet
import com.example.mippharmacare.ui.navigation.BottomNavItem
import com.example.mippharmacare.ui.screens.home.HomeScreen
import com.example.mippharmacare.ui.screens.pharmacy.PharmacyFinderScreen
import com.example.mippharmacare.ui.screens.profile.ProfileScreen
import com.example.mippharmacare.ui.screens.search.MedicineSearchScreen
import com.example.mippharmacare.ui.viewmodel.PharmaViewModel
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalBackground
import com.example.ui.theme.MedicalGreenContainer
import com.example.ui.theme.MedicalGreenDark
import com.example.ui.theme.MedicalGreenPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: PharmaViewModel,
    onNavigateToSymptoms: () -> Unit,
    onNavigateToAskPharmacist: (medicineName: String) -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToSavedMedicines: () -> Unit
) {
    var selectedTab by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    val showEmergencyHelp by viewModel.showEmergencyHelp.collectAsStateWithLifecycle()
    val emergencySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_screen_scaffold"),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                // Home
                NavigationBarItem(
                    selected = selectedTab == BottomNavItem.Home,
                    onClick = { selectedTab = BottomNavItem.Home },
                    icon = {
                        Icon(
                            if (selectedTab == BottomNavItem.Home) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home", fontSize = 11.sp, fontWeight = if (selectedTab == BottomNavItem.Home) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MedicalGreenDark,
                        selectedTextColor = MedicalGreenDark,
                        indicatorColor = MedicalGreenContainer,
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag("nav_home")
                )

                // Search
                NavigationBarItem(
                    selected = selectedTab == BottomNavItem.Search,
                    onClick = { selectedTab = BottomNavItem.Search },
                    icon = {
                        Icon(
                            if (selectedTab == BottomNavItem.Search) Icons.Filled.Search else Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    },
                    label = { Text("Search", fontSize = 11.sp, fontWeight = if (selectedTab == BottomNavItem.Search) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MedicalGreenDark,
                        selectedTextColor = MedicalGreenDark,
                        indicatorColor = MedicalGreenContainer,
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag("nav_search")
                )

                // Pharmacy
                NavigationBarItem(
                    selected = selectedTab == BottomNavItem.Pharmacy,
                    onClick = { selectedTab = BottomNavItem.Pharmacy },
                    icon = {
                        Icon(
                            if (selectedTab == BottomNavItem.Pharmacy) Icons.Filled.LocationOn else Icons.Outlined.LocationOn,
                            contentDescription = "Pharmacy"
                        )
                    },
                    label = { Text("Pharmacy", fontSize = 11.sp, fontWeight = if (selectedTab == BottomNavItem.Pharmacy) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MedicalGreenDark,
                        selectedTextColor = MedicalGreenDark,
                        indicatorColor = MedicalGreenContainer,
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag("nav_pharmacy")
                )

                // Profile
                NavigationBarItem(
                    selected = selectedTab == BottomNavItem.Profile,
                    onClick = { selectedTab = BottomNavItem.Profile },
                    icon = {
                        Icon(
                            if (selectedTab == BottomNavItem.Profile) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile", fontSize = 11.sp, fontWeight = if (selectedTab == BottomNavItem.Profile) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MedicalGreenDark,
                        selectedTextColor = MedicalGreenDark,
                        indicatorColor = MedicalGreenContainer,
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag("nav_profile")
                )
            }
        },
        floatingActionButton = {
            // Floating Emergency Hotline Button
            FloatingActionButton(
                onClick = { viewModel.setEmergencyHelpVisible(true) },
                containerColor = EmergencyRed,
                contentColor = Color.White,
                modifier = Modifier
                    .size(52.dp)
                    .testTag("fab_emergency_help")
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Emergency Assistance",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MedicalBackground)
        ) {
            when (selectedTab) {
                BottomNavItem.Home -> HomeScreen(
                    onNavigateToSearch = { selectedTab = BottomNavItem.Search },
                    onNavigateToPharmacy = { selectedTab = BottomNavItem.Pharmacy },
                    onNavigateToSymptoms = onNavigateToSymptoms,
                    onNavigateToAsk = { onNavigateToAskPharmacist("") },
                    onOpenEmergencyHelp = { viewModel.setEmergencyHelpVisible(true) }
                )
                BottomNavItem.Search -> MedicineSearchScreen(
                    viewModel = viewModel,
                    onNavigateToAskAboutMedicine = { med -> onNavigateToAskPharmacist(med.medicineName) }
                )
                BottomNavItem.Pharmacy -> PharmacyFinderScreen(
                    viewModel = viewModel
                )
                BottomNavItem.Profile -> ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToAuth = onNavigateToAuth,
                    onNavigateToSavedMedicines = onNavigateToSavedMedicines
                )
            }
        }
    }

    // Emergency Bottom Sheet
    if (showEmergencyHelp) {
        EmergencyHelpBottomSheet(
            sheetState = emergencySheetState,
            onDismiss = {
                coroutineScope.launch {
                    emergencySheetState.hide()
                    viewModel.setEmergencyHelpVisible(false)
                }
            }
        )
    }
}
