package com.example.mippharmacare.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.EmergencyRedContainer
import com.example.ui.theme.OnEmergencyRedContainer

const val HELPLINE_PHONE_NUMBER = "+918793693213"
const val HELPLINE_DISPLAY_NUMBER = "+91 8793693213"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyHelpBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(EmergencyRedContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Emergency Icon",
                            tint = EmergencyRed,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Emergency Medical Guidance",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmergencyRed
                        )
                        Text(
                            text = "Immediate action for urgent situations",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_emergency_sheet")
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Primary Hotline Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("emergency_helpline_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = EmergencyRedContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "MIP Healthcare Support & Helpline",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnEmergencyRedContainer
                    )
                    Text(
                        text = "Connect with medical helpline assistance immediately:",
                        fontSize = 13.sp,
                        color = OnEmergencyRedContainer.copy(alpha = 0.85f),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = HELPLINE_DISPLAY_NUMBER,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = EmergencyRed
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$HELPLINE_PHONE_NUMBER")
                            }
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dial_helpline_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Call Helpline Now ($HELPLINE_DISPLAY_NUMBER)", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Ambulance / 108 Quick Dial
            OutlinedButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:108")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dial_ambulance_108"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.LocalHospital, contentDescription = null, tint = EmergencyRed)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Dial National Ambulance (108 / 112)", color = EmergencyRed, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Red Flag Symptoms
            Text(
                text = "Seek Immediate Emergency Care If You Experience:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(8.dp))

            val emergencySymptoms = listOf(
                "Severe crushing chest pain, pressure, or tightness radiating to left arm or jaw",
                "Sudden weakness, facial drooping, or numbness on one side of body",
                "Severe shortness of breath or inability to speak full sentences",
                "Sudden severe explosive headache unlike anything previously felt",
                "Swelling of the lips, tongue, or throat with breathing difficulty (Anaphylaxis)",
                "Coughing up blood or vomiting blood / coffee-ground material",
                "Sudden loss of consciousness, confusion, or seizures"
            )

            emergencySymptoms.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("⚠️ ", fontSize = 13.sp)
                    Text(
                        text = item,
                        fontSize = 13.sp,
                        color = Color(0xFF334155),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Safety Warning Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F5F9), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Medical Disclaimer: MIP PHARMA CARE is an educational information resource. It does not provide medical triage or diagnosis. For life-threatening emergencies, proceed to the nearest hospital casualty or emergency department immediately.",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
