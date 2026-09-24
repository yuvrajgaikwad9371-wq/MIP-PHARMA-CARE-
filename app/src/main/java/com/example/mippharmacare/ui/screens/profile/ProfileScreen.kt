package com.example.mippharmacare.ui.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.mippharmacare.ui.components.HELPLINE_DISPLAY_NUMBER
import com.example.mippharmacare.ui.components.HELPLINE_PHONE_NUMBER
import com.example.mippharmacare.ui.viewmodel.PharmaViewModel
import com.example.ui.theme.HealthcareBlueContainer
import com.example.ui.theme.HealthcareBlueDark
import com.example.ui.theme.HealthcareBluePrimary
import com.example.ui.theme.MedicalBackground
import com.example.ui.theme.MedicalGreenContainer
import com.example.ui.theme.MedicalGreenDark
import com.example.ui.theme.MedicalGreenPrimary
import com.example.ui.theme.OtcBadgeGreen
import com.example.ui.theme.OtcBadgeGreenBg

@Composable
fun ProfileScreen(
    viewModel: PharmaViewModel,
    onNavigateToAuth: () -> Unit,
    onNavigateToSavedMedicines: () -> Unit
) {
    val context = LocalContext.current
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val favorites by viewModel.favoriteMedicines.collectAsStateWithLifecycle()

    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showHealthInfoDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MedicalBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
            .testTag("profile_screen_content")
    ) {
        // Top Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "My Profile",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Personal Health Information & Safety Preferences",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                if (userProfile != null) {
                    IconButton(
                        onClick = { showEditProfileDialog = true },
                        modifier = Modifier
                            .background(HealthcareBlueContainer, CircleShape)
                            .size(38.dp)
                            .testTag("edit_profile_button")
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = HealthcareBluePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // User Account Card
        if (userProfile != null) {
            val user = userProfile!!
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .background(MedicalGreenContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.take(1).uppercase().ifEmpty { "P" },
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedicalGreenDark
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = user.name.ifEmpty { "Patient" },
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            if (user.email.isNotEmpty()) {
                                Text(
                                    text = user.email,
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                            if (user.age.isNotEmpty()) {
                                Text(
                                    text = "Age: ${user.age} yrs",
                                    fontSize = 12.sp,
                                    color = HealthcareBluePrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Logout button
                        IconButton(
                            onClick = { viewModel.logOut() },
                            modifier = Modifier.testTag("logout_button")
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = "Log Out", tint = Color(0xFFDC2626))
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Health Summary Fields
                    HealthSummaryPill(
                        label = "Known Allergies",
                        value = user.allergies.ifEmpty { "None recorded (Tap edit to add)" },
                        tint = if (user.allergies.isNotEmpty()) Color(0xFFDC2626) else Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    HealthSummaryPill(
                        label = "Current Medicines",
                        value = user.medicines.ifEmpty { "None recorded (Tap edit to add)" },
                        tint = HealthcareBlueDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    HealthSummaryPill(
                        label = "Health Conditions",
                        value = user.healthInformation.ifEmpty { "General health (Tap edit to add)" },
                        tint = Color(0xFF0F172A)
                    )
                }
            }
        } else {
            // Not Logged In Callout Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HealthcareBlueContainer.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = HealthcareBlueDark,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Save Health Data Securely",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = HealthcareBlueDark
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Sign in or create an account to securely store your allergy records, current medications, and consult history across sessions.",
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onNavigateToAuth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("profile_login_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = HealthcareBluePrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Log In or Sign Up", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Features Menu Section
        Text(
            text = "Health & Application Features",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                // 1. My Medicine List
                ProfileMenuItem(
                    icon = Icons.Default.Bookmark,
                    iconTint = MedicalGreenPrimary,
                    title = "📋 My Medicine List",
                    subtitle = "${favorites.size} saved medicines",
                    testTag = "menu_my_medicine_list",
                    onClick = onNavigateToSavedMedicines
                )

                // 2. Health Information
                ProfileMenuItem(
                    icon = Icons.Default.Favorite,
                    iconTint = Color(0xFFE11D48),
                    title = "❤️ Health Information",
                    subtitle = "Allergies, conditions & emergency contacts",
                    testTag = "menu_health_information",
                    onClick = { showHealthInfoDialog = true }
                )

                // 3. Notifications & Medication Reminders
                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    iconTint = Color(0xFFD97706),
                    title = "🔔 Notifications & Reminders",
                    subtitle = "Medication timing & refill alerts",
                    testTag = "menu_notifications",
                    onClick = { showNotificationsDialog = true }
                )

                // 4. Privacy & Health Data Policy
                ProfileMenuItem(
                    icon = Icons.Default.Security,
                    iconTint = HealthcareBluePrimary,
                    title = "🔒 Privacy & Data Protection",
                    subtitle = "Local storage & medical data ethics",
                    testTag = "menu_privacy",
                    onClick = { showPrivacyDialog = true }
                )

                // 5. About PharmaCare
                ProfileMenuItem(
                    icon = Icons.Default.Info,
                    iconTint = Color(0xFF64748B),
                    title = "ℹ️ About MIP PharmaCare",
                    subtitle = "Version 1.0 • Matoshri Institute of Pharmacy",
                    testTag = "menu_about",
                    onClick = { showAboutDialog = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Official Helpline Support Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MedicalGreenContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MedicalGreenPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Helpline Support",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedicalGreenDark
                        )
                        Text(
                            text = HELPLINE_DISPLAY_NUMBER,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MedicalGreenDark
                        )
                    }
                }
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$HELPLINE_PHONE_NUMBER")
                        }
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("profile_call_helpline_btn")
                ) {
                    Text("Call", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mandatory Corner Advertisement line requested by user
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(
                    Color(0xFFF1F5F9),
                    RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .testTag("developer_advertisement_banner"),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.School,
                    contentDescription = "College Emblem",
                    tint = MedicalGreenPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "This app is made by Yuvraj from MIP College Yeola",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF334155)
                )
            }
        }
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        val current = userProfile
        var editName by remember { mutableStateOf(current?.name ?: "") }
        var editAge by remember { mutableStateOf(current?.age ?: "") }
        var editAllergies by remember { mutableStateOf(current?.allergies ?: "") }
        var editMedicines by remember { mutableStateOf(current?.medicines ?: "") }
        var editHealthInfo by remember { mutableStateOf(current?.healthInformation ?: "") }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Edit Patient Health Profile", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_name_field"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = editAge,
                        onValueChange = { editAge = it },
                        label = { Text("Age") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_age_field"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = editAllergies,
                        onValueChange = { editAllergies = it },
                        label = { Text("Allergies (e.g. Penicillin, Sulfa, Peanuts)") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_allergies_field")
                    )
                    OutlinedTextField(
                        value = editMedicines,
                        onValueChange = { editMedicines = it },
                        label = { Text("Current Daily Medicines") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_medicines_field")
                    )
                    OutlinedTextField(
                        value = editHealthInfo,
                        onValueChange = { editHealthInfo = it },
                        label = { Text("Existing Health Conditions") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_health_field")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateUserProfile(
                            name = editName,
                            age = editAge,
                            allergies = editAllergies,
                            medicines = editMedicines,
                            healthInfo = editHealthInfo
                        )
                        showEditProfileDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary),
                    modifier = Modifier.testTag("save_profile_button")
                ) {
                    Text("Save Changes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // About PharmaCare Dialog
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_mip_college_logo),
                    contentDescription = "MIP Logo",
                    modifier = Modifier.size(56.dp)
                )
            },
            title = {
                Text("About MIP PHARMA CARE", fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "“Right Information. Safer Healthcare.”",
                        fontWeight = FontWeight.Bold,
                        color = MedicalGreenPrimary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Developed by Matoshri Institute of Pharmacy, Dhanore, Tal. Yeola, Dist. Nashik.",
                        fontSize = 12.sp,
                        color = Color(0xFF334155)
                    )
                    Text(
                        text = "Designed and developed by Yuvraj from MIP College Yeola.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HealthcareBlueDark
                    )
                    Text(
                        text = "Support Helpline: $HELPLINE_DISPLAY_NUMBER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Purpose: Empower patients and caregivers with reliable medicine educational info, OTC precautions, symptom self-care guidance, nearby pharmacy GPS locations, and registered pharmacist consultation.",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showAboutDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary)
                ) {
                    Text("Close")
                }
            }
        )
    }

    // Privacy Dialog
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("🔒 Privacy & Health Data Policy", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Your health information, allergy records, and saved medicines are stored locally on your device using a secure local Room database. They are never sold or shared with third-party advertisers.",
                        fontSize = 12.sp,
                        color = Color(0xFF334155)
                    )
                    Text(
                        text = "Inquiries submitted to Ask a Pharmacist are kept confidential for clinical review purposes.",
                        fontSize = 12.sp,
                        color = Color(0xFF334155)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showPrivacyDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary)
                ) {
                    Text("Got It")
                }
            }
        )
    }

    // Notifications Dialog
    if (showNotificationsDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            title = { Text("🔔 Medication Reminders", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Medication reminder system active. You will receive notifications for dosage schedules and prescription refills based on your saved medicine list.",
                        fontSize = 12.sp,
                        color = Color(0xFF334155)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showNotificationsDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary)
                ) {
                    Text("OK")
                }
            }
        )
    }

    // Health Info Dialog
    if (showHealthInfoDialog) {
        val user = userProfile
        AlertDialog(
            onDismissRequest = { showHealthInfoDialog = false },
            title = { Text("❤️ Health Profile Details", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Patient: ${user?.name ?: "Guest"}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(text = "Allergies: ${user?.allergies?.ifEmpty { "None recorded" } ?: "None recorded"}", fontSize = 12.sp)
                    Text(text = "Current Medicines: ${user?.medicines?.ifEmpty { "None recorded" } ?: "None recorded"}", fontSize = 12.sp)
                    Text(text = "Conditions: ${user?.healthInformation?.ifEmpty { "None recorded" } ?: "None recorded"}", fontSize = 12.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showHealthInfoDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary)
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun HealthSummaryPill(label: String, value: String, tint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 11.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
        Text(text = value, fontSize = 11.sp, color = tint, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    testTag: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(iconTint.copy(alpha = 0.12f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            Text(text = subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
        }

        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color(0xFFCBD5E1), modifier = Modifier.size(16.dp))
    }
}
