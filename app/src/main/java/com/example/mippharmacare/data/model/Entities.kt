package com.example.mippharmacare.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "medicine_name")
    val medicineName: String,
    @ColumnInfo(name = "active_ingredient")
    val activeIngredient: String,
    @ColumnInfo(name = "category")
    val category: String, // Pain & Fever, Cold & Cough, Allergy, Acidity, Skin Care, Infection, Cardiovascular, Diabetes, Mental Health, Other
    @ColumnInfo(name = "general_uses")
    val generalUses: String,
    @ColumnInfo(name = "precautions")
    val precautions: String,
    @ColumnInfo(name = "side_effects")
    val sideEffects: String,
    @ColumnInfo(name = "contraindications")
    val contraindications: String,
    @ColumnInfo(name = "interactions")
    val interactions: String,
    @ColumnInfo(name = "storage")
    val storage: String,
    @ColumnInfo(name = "prescription_status")
    val prescriptionStatus: String, // "OTC" or "Prescription Only (Rx)"
    @ColumnInfo(name = "source")
    val source: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)

@Entity(tableName = "symptoms")
data class SymptomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "symptom_name")
    val symptomName: String,
    @ColumnInfo(name = "icon_emoji")
    val iconEmoji: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "common_associations")
    val commonAssociations: String,
    @ColumnInfo(name = "self_care")
    val selfCare: String,
    @ColumnInfo(name = "otc_information")
    val otcInformation: String,
    @ColumnInfo(name = "warning_signs")
    val warningSigns: String,
    @ColumnInfo(name = "when_to_consult_doctor")
    val whenToConsultDoctor: String,
    @ColumnInfo(name = "source")
    val source: String
)

@Entity(tableName = "pharmacies")
data class PharmacyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "pharmacy_name")
    val pharmacyName: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "opening_hours")
    val openingHours: String,
    @ColumnInfo(name = "is_open_now")
    val isOpenNow: Boolean = true,
    @ColumnInfo(name = "is_24_hours")
    val is24Hours: Boolean = false,
    @ColumnInfo(name = "rating")
    val rating: Float = 4.5f
)

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "password")
    val password: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "age")
    val age: String = "",
    @ColumnInfo(name = "allergies")
    val allergies: String = "",
    @ColumnInfo(name = "medicines")
    val medicines: String = "",
    @ColumnInfo(name = "health_information")
    val healthInformation: String = "",
    @ColumnInfo(name = "is_logged_in")
    val isLoggedIn: Boolean = false
)

@Entity(tableName = "pharmacist_questions")
data class PharmacistQuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "medicine_name")
    val medicineName: String = "",
    @ColumnInfo(name = "photo_uri")
    val photoUri: String? = null,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "status")
    val status: String = "Pending Review", // "Pending Review", "Answered"
    @ColumnInfo(name = "pharmacist_reply")
    val pharmacistReply: String? = null
)
