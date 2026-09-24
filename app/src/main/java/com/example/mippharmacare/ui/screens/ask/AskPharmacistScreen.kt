package com.example.mippharmacare.ui.screens.ask

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mippharmacare.data.model.PharmacistQuestionEntity
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AskPharmacistScreen(
    viewModel: PharmaViewModel,
    prefilledMedicineName: String = "",
    onBack: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val questions by viewModel.pharmacistQuestions.collectAsStateWithLifecycle()
    val submissionSuccess by viewModel.submissionSuccess.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    var medicineName by remember { mutableStateOf(prefilledMedicineName) }
    var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Pre-populate user details if available
    LaunchedEffect(userProfile) {
        userProfile?.let {
            if (name.isEmpty()) name = it.name
            if (age.isEmpty()) age = it.age
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedPhotoUri = uri
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MedicalBackground)
            .testTag("ask_pharmacist_screen"),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 32.dp)
    ) {
        // App Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("ask_pharmacist_back")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "👨‍⚕️ Ask a Pharmacist",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Clinical pharmacy guidance from MIP College Yeola",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }
        }

        // Educational Disclaimer Header (Mandatory)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .background(Color(0xFFFEF3C7), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "“Pharmacist responses are for informational purposes and do not replace medical diagnosis or emergency care.”",
                        fontSize = 12.sp,
                        color = Color(0xFF92400E),
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Submission Success Message
        if (submissionSuccess) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .testTag("ask_pharmacist_success_banner"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = OtcBadgeGreenBg)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = OtcBadgeGreen)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Question Submitted Successfully!",
                                fontWeight = FontWeight.Bold,
                                color = OtcBadgeGreen,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Our clinical pharmacy desk will review your question. Check below for updates.",
                                fontSize = 11.sp,
                                color = Color(0xFF166534)
                            )
                        }
                    }
                }
            }
        }

        // Form Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Submit a Medicine Query",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )

                    errorMessage?.let {
                        Text(
                            text = "⚠️ $it",
                            color = Color(0xFFDC2626),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Field 1: Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Your Name *") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = MedicalGreenPrimary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pharmacist_name_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    // Field 2: Age
                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Age *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pharmacist_age_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    // Field 3: Optional Medicine Name
                    OutlinedTextField(
                        value = medicineName,
                        onValueChange = { medicineName = it },
                        label = { Text("Medicine Name (Optional)") },
                        leadingIcon = { Icon(Icons.Default.Medication, contentDescription = null, tint = HealthcareBluePrimary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pharmacist_medicine_name_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    // Field 4: Question Details
                    OutlinedTextField(
                        value = question,
                        onValueChange = { question = it },
                        label = { Text("Your Question / Medicine Concern *") },
                        placeholder = { Text("e.g., Can I take this with my blood pressure medicine? How should I store it?", fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .testTag("pharmacist_question_input"),
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 5
                    )

                    // Field 5: Optional Photo / Prescription Upload (Using Photo Picker)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("upload_prescription_photo_button")
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = HealthcareBluePrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (selectedPhotoUri != null) "Photo Selected" else "Attach Photo / Rx (Optional)",
                                fontSize = 12.sp,
                                color = HealthcareBluePrimary
                            )
                        }

                        if (selectedPhotoUri != null) {
                            IconButton(onClick = { selectedPhotoUri = null }) {
                                Icon(Icons.Default.Clear, contentDescription = "Remove photo", tint = Color(0xFF64748B))
                            }
                        }
                    }

                    // Submit Button
                    Button(
                        onClick = {
                            val parsedAge = age.toIntOrNull()
                            when {
                                name.isBlank() -> errorMessage = "Please enter your name."
                                parsedAge == null || parsedAge <= 0 -> errorMessage = "Please enter a valid age."
                                question.isBlank() -> errorMessage = "Please describe your question."
                                else -> {
                                    errorMessage = null
                                    viewModel.submitQuestion(
                                        name = name,
                                        age = parsedAge,
                                        question = question,
                                        medicineName = medicineName,
                                        photoUri = selectedPhotoUri?.toString()
                                    ) {
                                        question = ""
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_pharmacist_question_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = MedicalGreenPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Submit Question", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Section: Previous Inquiries History
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Previous Inquiries & Pharmacist Responses",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (questions.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No inquiries submitted yet. Questions you submit will appear here.",
                        fontSize = 12.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        } else {
            items(questions, key = { it.id }) { q ->
                QuestionHistoryCard(q)
            }
        }
    }
}

@Composable
fun QuestionHistoryCard(question: PharmacistQuestionEntity) {
    var expanded by remember { mutableStateOf(false) }
    val dateStr = remember(question.timestamp) {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        sdf.format(Date(question.timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .clickable { expanded = !expanded }
            .testTag("question_item_${question.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Chip
                Box(
                    modifier = Modifier
                        .background(
                            if (question.status == "Answered") OtcBadgeGreenBg else HealthcareBlueContainer,
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = question.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (question.status == "Answered") OtcBadgeGreen else HealthcareBlueDark
                    )
                }

                Text(
                    text = dateStr,
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (question.medicineName.isNotBlank()) {
                Text(
                    text = "Medicine: " + question.medicineName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HealthcareBluePrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
            }

            Text(
                text = question.question,
                fontSize = 13.sp,
                color = Color(0xFF1E293B),
                fontWeight = FontWeight.Medium
            )

            // Pharmacist Reply Box
            AnimatedVisibility(visible = expanded || question.pharmacistReply != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MedicalGreenContainer.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.QuestionAnswer,
                            contentDescription = null,
                            tint = MedicalGreenDark,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "MIP Clinical Pharmacy Desk Reply:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedicalGreenDark
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = question.pharmacistReply ?: "Inquiry is in queue for registered pharmacist evaluation.",
                        fontSize = 12.sp,
                        color = Color(0xFF14532D),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
