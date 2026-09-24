package com.example.mippharmacare.ui.screens.search

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mippharmacare.data.model.MedicineEntity
import com.example.mippharmacare.ui.components.MedicineDetailBottomSheet
import com.example.mippharmacare.ui.viewmodel.PharmaViewModel
import com.example.ui.theme.HealthcareBlueContainer
import com.example.ui.theme.HealthcareBluePrimary
import com.example.ui.theme.MedicalBackground
import com.example.ui.theme.MedicalGreenContainer
import com.example.ui.theme.MedicalGreenDark
import com.example.ui.theme.MedicalGreenPrimary
import com.example.ui.theme.OtcBadgeGreen
import com.example.ui.theme.OtcBadgeGreenBg
import com.example.ui.theme.RxBadgeAmber
import com.example.ui.theme.RxBadgeAmberBg
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineSearchScreen(
    viewModel: PharmaViewModel,
    onNavigateToAskAboutMedicine: (MedicineEntity) -> Unit
) {
    val searchQuery by viewModel.medicineSearchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val medicineList by viewModel.medicineList.collectAsStateWithLifecycle()
    val selectedMedicine by viewModel.selectedMedicine.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf(
        "All" to "All",
        "Pain & Fever" to "💊 Pain & Fever",
        "Cold & Cough" to "🤧 Cold & Cough",
        "Allergy" to "🌸 Allergy",
        "Acidity" to "🫃 Acidity",
        "Skin Care" to "🧴 Skin Care",
        "Infection" to "🦠 Infection",
        "Cardiovascular" to "💗 Cardiovascular",
        "Diabetes" to "🩺 Diabetes",
        "Mental Health" to "🧠 Mental Health",
        "Other" to "Other"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MedicalBackground)
            .testTag("medicine_search_screen")
    ) {
        // Search & Filter Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "🔍 Medicine Search",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Text(
                text = "Search by brand name, generic active ingredient, or medical use",
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Search input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onMedicineSearchChange(it) },
                placeholder = {
                    Text(
                        text = "Search medicine or active ingredient…",
                        fontSize = 13.sp,
                        color = Color(0xFF94A3B8)
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MedicalGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.onMedicineSearchChange("") },
                            modifier = Modifier.testTag("clear_medicine_search")
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color(0xFF64748B))
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("medicine_search_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MedicalGreenPrimary,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Categories horizontal list
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { (key, label) ->
                    val isSelected = selectedCategory == key
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.onCategorySelect(key) },
                        label = { Text(label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MedicalGreenContainer,
                            selectedLabelColor = MedicalGreenDark
                        ),
                        modifier = Modifier.testTag("category_chip_$key")
                    )
                }
            }
        }

        // List of Medicines
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Found ${medicineList.size} Medicines",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569)
                    )
                    Text(
                        text = "Tap for dosage safety & precautions",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            if (medicineList.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Medication,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No medicines match your search",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF334155)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Try searching by generic name (e.g. Paracetamol, Cetirizine)",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            } else {
                items(medicineList, key = { it.id }) { medicine ->
                    MedicineCardItem(
                        medicine = medicine,
                        onClick = { viewModel.selectMedicine(medicine) },
                        onToggleFavorite = { viewModel.toggleFavorite(medicine) }
                    )
                }
            }
        }
    }

    // Detail Bottom Sheet
    selectedMedicine?.let { medicine ->
        MedicineDetailBottomSheet(
            medicine = medicine,
            sheetState = sheetState,
            onDismiss = {
                coroutineScope.launch {
                    sheetState.hide()
                    viewModel.selectMedicine(null)
                }
            },
            onToggleFavorite = { viewModel.toggleFavorite(it) },
            onAskPharmacistAbout = {
                viewModel.selectMedicine(null)
                onNavigateToAskAboutMedicine(it)
            }
        )
    }
}

@Composable
fun MedicineCardItem(
    medicine: MedicineEntity,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val isOtc = medicine.prescriptionStatus.contains("OTC", ignoreCase = true)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("medicine_item_${medicine.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = medicine.medicineName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = medicine.activeIngredient,
                    fontSize = 12.sp,
                    color = HealthcareBluePrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category Tag
                    Box(
                        modifier = Modifier
                            .background(HealthcareBlueContainer, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = medicine.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = HealthcareBluePrimary
                        )
                    }

                    // OTC or Rx tag
                    Box(
                        modifier = Modifier
                            .background(
                                if (isOtc) OtcBadgeGreenBg else RxBadgeAmberBg,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (isOtc) "OTC" else "Rx Only",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isOtc) OtcBadgeGreen else RxBadgeAmber
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Bookmark button
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (medicine.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (medicine.isFavorite) MedicalGreenPrimary else Color(0xFFCBD5E1),
                    modifier = Modifier.size(20.dp)
                )
            }

            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "View Detail",
                tint = Color(0xFFCBD5E1),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
