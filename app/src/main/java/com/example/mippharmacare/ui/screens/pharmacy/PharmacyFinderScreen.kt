package com.example.mippharmacare.ui.screens.pharmacy

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mippharmacare.data.repository.PharmacyWithDistance
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
import com.google.android.gms.location.LocationServices

@Composable
fun PharmacyFinderScreen(
    viewModel: PharmaViewModel
) {
    val context = LocalContext.current
    val pharmacies by viewModel.pharmacyList.collectAsStateWithLifecycle()
    val activeFilter by viewModel.pharmacyFilter.collectAsStateWithLifecycle()
    val userLat by viewModel.userLatitude.collectAsStateWithLifecycle()
    val userLng by viewModel.userLongitude.collectAsStateWithLifecycle()
    val isLocating by viewModel.isLocating.collectAsStateWithLifecycle()
    val locationName by viewModel.locationName.collectAsStateWithLifecycle()

    var manualSearchText by remember { mutableStateOf("") }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    fun fetchDeviceGpsLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.setLocating(true)
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        viewModel.updateUserLocation(
                            loc.latitude,
                            loc.longitude,
                            "GPS Live (${String.format("%.4f", loc.latitude)}, ${String.format("%.4f", loc.longitude)})"
                        )
                    } else {
                        // Fallback to default campus coordinates if lastLocation null in emulator
                        viewModel.updateUserLocation(20.0425, 74.4891, "Yeola / Dhanore (MIP Campus)")
                    }
                }.addOnFailureListener {
                    viewModel.updateUserLocation(20.0425, 74.4891, "Yeola / Dhanore (MIP Campus)")
                }
            } catch (e: SecurityException) {
                viewModel.setLocating(false)
            }
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasLocationPermission = granted
        if (granted) {
            fetchDeviceGpsLocation()
        }
    }

    // Auto-fetch if permission already granted
    LaunchedEffect(Unit) {
        if (hasLocationPermission && userLat == null) {
            fetchDeviceGpsLocation()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MedicalBackground)
            .testTag("pharmacy_finder_screen")
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "📍 Nearby Pharmacy Finder",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Text(
                text = "Live GPS locations, opening hours, contact & turn-by-turn directions",
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Location Status & GPS Request Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HealthcareBlueContainer.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.MyLocation,
                        contentDescription = "Location",
                        tint = HealthcareBluePrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Current Location:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = locationName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = HealthcareBlueDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (isLocating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = HealthcareBluePrimary
                    )
                } else {
                    Button(
                        onClick = {
                            if (!hasLocationPermission) {
                                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            } else {
                                fetchDeviceGpsLocation()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = HealthcareBluePrimary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("use_gps_button")
                    ) {
                        Text(
                            text = if (hasLocationPermission) "Refresh GPS" else "Enable GPS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Manual Location Search if permission denied or custom town
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = manualSearchText,
                    onValueChange = { manualSearchText = it },
                    placeholder = { Text("Search location manually (e.g. Yeola, Nashik)", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("manual_location_input"),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = HealthcareBluePrimary,
                        unfocusedBorderColor = Color(0xFFE2E8F0)
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (manualSearchText.isNotBlank()) {
                            viewModel.manualSearchLocation(manualSearchText)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                    modifier = Modifier.testTag("submit_manual_location")
                ) {
                    Text("Search", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Filter Chips: Open Now | 24 Hours | Nearest | All
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = listOf("All", "Open Now", "24 Hours", "Nearest")
                items(filters) { filter ->
                    FilterChip(
                        selected = activeFilter == filter,
                        onClick = { viewModel.setPharmacyFilter(filter) },
                        label = { Text(filter, fontSize = 12.sp) },
                        leadingIcon = if (activeFilter == filter) {
                            { Icon(Icons.Default.FilterList, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MedicalGreenContainer,
                            selectedLabelColor = MedicalGreenDark
                        ),
                        modifier = Modifier.testTag("filter_chip_$filter")
                    )
                }
            }
        }

        // Interactive Map Visualization Header
        InteractivePharmacyMapCanvas(
            pharmacies = pharmacies,
            userLat = userLat ?: 20.0425,
            userLng = userLng ?: 74.4891
        )

        // Pharmacy List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Found ${pharmacies.size} Pharmacies",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155)
                    )
                    Text(
                        text = "Sorted by proximity",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            if (pharmacies.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.LocationSearching,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No pharmacies found matching filter",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF334155)
                            )
                            Text(
                                text = "Try clearing filters or switching location",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            } else {
                items(pharmacies, key = { it.pharmacy.id }) { item ->
                    PharmacyCard(
                        item = item,
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
fun InteractivePharmacyMapCanvas(
    pharmacies: List<PharmacyWithDistance>,
    userLat: Double,
    userLng: Double
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(Color(0xFFE2E8F0))
            .testTag("interactive_pharmacy_map_canvas")
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            // Background terrain & map grid lines
            drawRect(Color(0xFFEAF2EB))

            // Grid lines
            val step = 40f
            var x = 0f
            while (x < canvasW) {
                drawLine(
                    color = Color(0xFFD6E4D8),
                    start = Offset(x, 0f),
                    end = Offset(x, canvasH),
                    strokeWidth = 1f
                )
                x += step
            }
            var y = 0f
            while (y < canvasH) {
                drawLine(
                    color = Color(0xFFD6E4D8),
                    start = Offset(0f, y),
                    end = Offset(canvasW, y),
                    strokeWidth = 1f
                )
                y += step
            }

            // Radar concentric distance rings around user
            val center = Offset(canvasW / 2f, canvasH / 2f)
            drawCircle(
                color = Color(0x330284C7),
                radius = 35f,
                center = center,
                style = Stroke(width = 1.5f)
            )
            drawCircle(
                color = Color(0x220284C7),
                radius = 70f,
                center = center,
                style = Stroke(width = 1.5f)
            )
            drawCircle(
                color = Color(0x150284C7),
                radius = 110f,
                center = center,
                style = Stroke(width = 1.5f)
            )

            // Draw User Center Location Pin
            drawCircle(
                color = Color(0x440284C7),
                radius = 14f,
                center = center
            )
            drawCircle(
                color = Color(0xFF0284C7),
                radius = 7f,
                center = center
            )
            drawCircle(
                color = Color.White,
                radius = 3f,
                center = center
            )

            // Plot nearby pharmacy markers relative to user
            val scale = 800f // degrees to canvas pixels
            pharmacies.take(8).forEachIndexed { index, p ->
                val dLat = (p.pharmacy.latitude - userLat).toFloat()
                val dLng = (p.pharmacy.longitude - userLng).toFloat()

                val pinX = (center.x + dLng * scale).coerceIn(20f, canvasW - 20f)
                val pinY = (center.y - dLat * scale).coerceIn(20f, canvasH - 20f)

                // Pharmacy green pin
                drawCircle(
                    color = Color(0xFF0F7644),
                    radius = 9f,
                    center = Offset(pinX, pinY)
                )
                drawCircle(
                    color = Color.White,
                    radius = 4f,
                    center = Offset(pinX, pinY)
                )
            }
        }

        // Overlay Map Badge & Indicator
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .background(Color.White.copy(alpha = 0.92f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(HealthcareBluePrimary, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("You", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = HealthcareBlueDark)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(MedicalGreenPrimary, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Pharmacies", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MedicalGreenDark)
        }

        // Live Radar indicator
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                .padding(horizontal = 6.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Live World GPS Radar", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun PharmacyCard(
    item: PharmacyWithDistance,
    context: Context
) {
    val pharmacy = item.pharmacy

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pharmacy_item_${pharmacy.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top row: Name & Open Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = pharmacy.pharmacyName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = pharmacy.address,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Distance Pill
                if (item.distanceKm != null) {
                    Box(
                        modifier = Modifier
                            .background(HealthcareBlueContainer, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${item.distanceKm} km",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = HealthcareBlueDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Badges: Open Status + 24 Hours + Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Open status
                Box(
                    modifier = Modifier
                        .background(
                            if (pharmacy.isOpenNow) OtcBadgeGreenBg else Color(0xFFFEE2E2),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (pharmacy.isOpenNow) "Open Now" else "Closed",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (pharmacy.isOpenNow) OtcBadgeGreen else Color(0xFFDC2626)
                    )
                }

                if (pharmacy.is24Hours) {
                    Box(
                        modifier = Modifier
                            .background(MedicalGreenContainer, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "24/7 Hours",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedicalGreenDark
                        )
                    }
                }

                // Hours
                Text(
                    text = pharmacy.openingHours,
                    fontSize = 11.sp,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons: Call & Directions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Call Button
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${pharmacy.phone}")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("call_pharmacy_${pharmacy.id}"),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Call",
                        tint = HealthcareBluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call Store", fontSize = 12.sp, color = HealthcareBluePrimary, fontWeight = FontWeight.SemiBold)
                }

                // Directions Button (Direct GPS Navigation intent)
                Button(
                    onClick = {
                        // Open Google Maps turn-by-turn navigation or geo view
                        val uri = Uri.parse("geo:${pharmacy.latitude},${pharmacy.longitude}?q=${Uri.encode(pharmacy.pharmacyName + ", " + pharmacy.address)}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                            setPackage("com.google.android.apps.maps")
                        }
                        try {
                            context.startActivity(mapIntent)
                        } catch (e: Exception) {
                            // Fallback to generic geo intent or web browser
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${pharmacy.latitude},${pharmacy.longitude}")
                            )
                            context.startActivity(webIntent)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("directions_pharmacy_${pharmacy.id}"),
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Directions,
                        contentDescription = "Directions",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Directions", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
