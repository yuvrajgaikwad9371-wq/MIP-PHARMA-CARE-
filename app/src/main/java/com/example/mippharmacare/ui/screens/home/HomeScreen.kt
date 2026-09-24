package com.example.mippharmacare.ui.screens.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.mippharmacare.ui.components.HELPLINE_DISPLAY_NUMBER
import com.example.mippharmacare.ui.components.HELPLINE_PHONE_NUMBER
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.EmergencyRedContainer
import com.example.ui.theme.HealthcareBlueContainer
import com.example.ui.theme.HealthcareBluePrimary
import com.example.ui.theme.MedicalBackground
import com.example.ui.theme.MedicalGreenContainer
import com.example.ui.theme.MedicalGreenDark
import com.example.ui.theme.MedicalGreenPrimary
import com.example.ui.theme.OnEmergencyRedContainer

@Composable
fun HomeScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToPharmacy: () -> Unit,
    onNavigateToSymptoms: () -> Unit,
    onNavigateToAsk: () -> Unit,
    onOpenEmergencyHelp: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MedicalBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
            .testTag("home_screen_content")
    ) {
        // Top Brand Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MedicalGreenDark,
                            MedicalGreenPrimary
                        )
                    ),
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_mip_college_logo),
                            contentDescription = "MIP Emblem",
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "MIP PHARMA CARE",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = "Matoshri Institute of Pharmacy, Yeola",
                                fontSize = 11.sp,
                                color = MedicalGreenContainer
                            )
                        }
                    }

                    // Quick Helpline Button
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$HELPLINE_PHONE_NUMBER")
                            }
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .size(38.dp)
                            .testTag("home_quick_helpline_icon")
                    ) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Call Helpline",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Welcome Greeting
                Text(
                    text = "Hello! 👋",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "How can we help you today?",
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Quick Search Bar Affordance
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(14.dp))
                        .clickable { onNavigateToSearch() }
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                        .testTag("home_search_bar_trigger"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Search medicine or active ingredient...",
                        fontSize = 13.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Emergency Help Button (Prominent banner as requested)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable { onOpenEmergencyHelp() }
                .testTag("home_emergency_button"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = EmergencyRedContainer)
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
                            .size(40.dp)
                            .background(EmergencyRed, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Emergency",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "🚨 Emergency Help",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnEmergencyRedContainer
                        )
                        Text(
                            text = "Helpline: $HELPLINE_DISPLAY_NUMBER | Dial 108 for urgent care",
                            fontSize = 12.sp,
                            color = EmergencyRed,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Open Emergency",
                    tint = EmergencyRed
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Four Large Feature Cards Section
        Text(
            text = "Core Healthcare Services",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Card 1: Find Pharmacy Nearby
            FeatureCard(
                title = "📍 Find Pharmacy Nearby",
                description = "Find pharmacies based on your current GPS location, check 24/7 hours and get directions.",
                icon = Icons.Default.LocationOn,
                iconBg = HealthcareBlueContainer,
                iconTint = HealthcareBluePrimary,
                testTag = "card_find_pharmacy",
                onClick = onNavigateToPharmacy
            )

            // Card 2: Medicine Search
            FeatureCard(
                title = "🔍 Medicine Search",
                description = "Search reliable educational info on generic ingredients, uses, precautions, side effects & interactions.",
                icon = Icons.Default.Search,
                iconBg = MedicalGreenContainer,
                iconTint = MedicalGreenPrimary,
                testTag = "card_medicine_search",
                onClick = onNavigateToSearch
            )

            // Card 3: Health Problems & Symptoms
            FeatureCard(
                title = "🤒 Health Problems & Symptoms",
                description = "Explore evidence-based guidance for common symptoms, safe self-care, and when to consult a doctor.",
                icon = Icons.Default.Sick,
                iconBg = Color(0xFFFEE2E2),
                iconTint = Color(0xFFE11D48),
                testTag = "card_health_problems",
                onClick = onNavigateToSymptoms
            )

            // Card 4: Ask a Pharmacist
            FeatureCard(
                title = "👨‍⚕️ Ask a Pharmacist",
                description = "Submit medicine-related questions securely to qualified pharmacists for educational guidance.",
                icon = Icons.Default.QuestionAnswer,
                iconBg = Color(0xFFEDE9FE),
                iconTint = Color(0xFF7C3AED),
                testTag = "card_ask_pharmacist",
                onClick = onNavigateToAsk
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Common Symptoms Quick Access
        Text(
            text = "Common Symptoms Quick Check",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val quickSymptoms = listOf(
                "Headache 🤕",
                "Fever 🌡️",
                "Cold 🤧",
                "Cough 😷",
                "Acidity 🫃",
                "Allergy 🌸",
                "Diarrhea 💩"
            )
            items(quickSymptoms) { symptom ->
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .clickable { onNavigateToSymptoms() }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = symptom,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF334155)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mandatory Medical Disclaimer Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(Color(0xFFF1F5F9), RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "🛡️ Educational & Medical Safety Commitment",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "MIP PHARMA CARE provides educational information only and does NOT diagnose diseases or independently prescribe medicines. For prescription medicines, severe symptoms, pregnancy, or children, always consult a qualified doctor or pharmacist.",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun FeatureCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(iconBg, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Go",
                tint = Color(0xFFCBD5E1),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
