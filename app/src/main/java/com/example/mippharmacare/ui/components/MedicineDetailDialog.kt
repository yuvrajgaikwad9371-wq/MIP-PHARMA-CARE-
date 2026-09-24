package com.example.mippharmacare.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.QuestionAnswer
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mippharmacare.data.model.MedicineEntity
import com.example.ui.theme.HealthcareBlueContainer
import com.example.ui.theme.HealthcareBluePrimary
import com.example.ui.theme.MedicalGreenContainer
import com.example.ui.theme.MedicalGreenPrimary
import com.example.ui.theme.OtcBadgeGreen
import com.example.ui.theme.OtcBadgeGreenBg
import com.example.ui.theme.RxBadgeAmber
import com.example.ui.theme.RxBadgeAmberBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailBottomSheet(
    medicine: MedicineEntity,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onToggleFavorite: (MedicineEntity) -> Unit,
    onAskPharmacistAbout: (MedicineEntity) -> Unit
) {
    val isOtc = medicine.prescriptionStatus.contains("OTC", ignoreCase = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header: Name & Close & Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = medicine.medicineName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Active: " + medicine.activeIngredient,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = HealthcareBluePrimary
                    )
                }

                Row {
                    IconButton(
                        onClick = { onToggleFavorite(medicine) },
                        modifier = Modifier.testTag("toggle_favorite_medicine")
                    ) {
                        Icon(
                            imageVector = if (medicine.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save Medicine",
                            tint = if (medicine.isFavorite) MedicalGreenPrimary else Color(0xFF64748B)
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_medicine_detail")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Badges row: Category + OTC / Rx Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Category Chip
                Box(
                    modifier = Modifier
                        .background(HealthcareBlueContainer, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = medicine.category,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HealthcareBluePrimary
                    )
                }

                // OTC or Prescription Badge
                Box(
                    modifier = Modifier
                        .background(
                            if (isOtc) OtcBadgeGreenBg else RxBadgeAmberBg,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = medicine.prescriptionStatus,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isOtc) OtcBadgeGreen else RxBadgeAmber
                    )
                }
            }

            // Prescription Warning banner if Rx
            if (!isOtc) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = RxBadgeAmberBg)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = null,
                            tint = RxBadgeAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Prescription Only (Rx): Must be prescribed by a licensed doctor following clinical examination. Do not self-administer.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF78350F),
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Section 1: General Uses
            DetailSection(
                title = "General Uses",
                content = medicine.generalUses,
                icon = Icons.Default.MedicalServices,
                iconTint = MedicalGreenPrimary
            )

            // Section 2: Important Precautions
            DetailSection(
                title = "Important Precautions",
                content = medicine.precautions,
                icon = Icons.Default.Warning,
                iconTint = Color(0xFFD97706)
            )

            // Section 3: Common Side Effects
            DetailSection(
                title = "Common Side Effects",
                content = medicine.sideEffects,
                icon = Icons.Default.Info,
                iconTint = HealthcareBluePrimary
            )

            // Section 4: Contraindications
            DetailSection(
                title = "Common Contraindications",
                content = medicine.contraindications,
                icon = Icons.Default.Warning,
                iconTint = Color(0xFFDC2626)
            )

            // Section 5: Drug Interactions
            DetailSection(
                title = "Important Drug Interactions",
                content = medicine.interactions,
                icon = Icons.Default.Info,
                iconTint = Color(0xFF7C3AED)
            )

            // Section 6: Storage Information
            DetailSection(
                title = "Storage Information",
                content = medicine.storage,
                icon = Icons.Default.MedicalServices,
                iconTint = Color(0xFF475569)
            )

            // Source
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reference Source: ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B)
                )
                Text(
                    text = medicine.source,
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mandatory Educational Disclaimer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8FAFC), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "⚠️ Medical Safety Notice: This information is educational and does not replace advice from a doctor or qualified pharmacist. The app does not diagnose or independently prescribe medications. For personalized dosing, children, pregnancy, or pre-existing diseases, always consult a qualified professional.",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action: Ask a Pharmacist
            Button(
                onClick = {
                    onDismiss()
                    onAskPharmacistAbout(medicine)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ask_pharmacist_for_medicine"),
                colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.QuestionAnswer, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Have Questions? Ask a Pharmacist", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailSection(
    title: String,
    content: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = content,
                fontSize = 13.sp,
                color = Color(0xFF334155),
                lineHeight = 18.sp
            )
        }
    }
}
