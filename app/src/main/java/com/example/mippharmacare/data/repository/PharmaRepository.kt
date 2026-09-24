package com.example.mippharmacare.data.repository

import android.location.Location
import com.example.mippharmacare.data.local.InitialData
import com.example.mippharmacare.data.local.MIPPharmaDao
import com.example.mippharmacare.data.model.MedicineEntity
import com.example.mippharmacare.data.model.PharmacistQuestionEntity
import com.example.mippharmacare.data.model.PharmacyEntity
import com.example.mippharmacare.data.model.SymptomEntity
import com.example.mippharmacare.data.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

data class PharmacyWithDistance(
    val pharmacy: PharmacyEntity,
    val distanceKm: Double? = null
)

class PharmaRepository(private val dao: MIPPharmaDao) {

    suspend fun ensureDataSeeded() = withContext(Dispatchers.IO) {
        if (dao.getMedicineCount() == 0) {
            dao.insertMedicines(InitialData.initialMedicines)
        }
        if (dao.getSymptomCount() == 0) {
            dao.insertSymptoms(InitialData.initialSymptoms)
        }
        if (dao.getPharmacyCount() == 0) {
            dao.insertPharmacies(InitialData.initialPharmacies)
        }
    }

    // Medicines
    fun getAllMedicines(): Flow<List<MedicineEntity>> = dao.getAllMedicines()

    fun searchMedicines(query: String): Flow<List<MedicineEntity>> {
        return if (query.isBlank()) {
            dao.getAllMedicines()
        } else {
            dao.searchMedicines(query.trim())
        }
    }

    fun getMedicinesByCategory(category: String): Flow<List<MedicineEntity>> {
        return if (category == "All") {
            dao.getAllMedicines()
        } else {
            dao.getMedicinesByCategory(category)
        }
    }

    fun getFavoriteMedicines(): Flow<List<MedicineEntity>> = dao.getFavoriteMedicines()

    fun getMedicineById(id: Long): Flow<MedicineEntity?> = dao.getMedicineById(id)

    suspend fun toggleFavorite(id: Long, currentFavorite: Boolean) = withContext(Dispatchers.IO) {
        dao.setMedicineFavorite(id, !currentFavorite)
    }

    // Symptoms
    fun getAllSymptoms(): Flow<List<SymptomEntity>> = dao.getAllSymptoms()

    fun searchSymptoms(query: String): Flow<List<SymptomEntity>> {
        return if (query.isBlank()) {
            dao.getAllSymptoms()
        } else {
            dao.searchSymptoms(query.trim())
        }
    }

    fun getSymptomById(id: Long): Flow<SymptomEntity?> = dao.getSymptomById(id)

    // Pharmacies with optional user location
    fun getPharmacies(
        filter: String = "All", // "All", "Open Now", "24 Hours", "Nearest"
        userLat: Double? = null,
        userLng: Double? = null
    ): Flow<List<PharmacyWithDistance>> {
        return dao.getAllPharmacies().map { list ->
            var items = list.map { pharmacy ->
                val dist = if (userLat != null && userLng != null) {
                    calculateDistanceKm(userLat, userLng, pharmacy.latitude, pharmacy.longitude)
                } else null
                PharmacyWithDistance(pharmacy, dist)
            }

            // Apply filter
            items = when (filter) {
                "Open Now" -> items.filter { it.pharmacy.isOpenNow }
                "24 Hours" -> items.filter { it.pharmacy.is24Hours }
                "Nearest" -> items.sortedBy { it.distanceKm ?: Double.MAX_VALUE }
                else -> items
            }

            if (filter != "Nearest" && userLat != null && userLng != null) {
                items.sortedBy { it.distanceKm ?: Double.MAX_VALUE }
            } else {
                items
            }
        }
    }

    // User Profile & Authentication
    fun getLoggedInUser(): Flow<UserEntity?> = dao.getLoggedInUser()

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        age: String
    ): Result<UserEntity> = withContext(Dispatchers.IO) {
        val normalizedEmail = email.trim().lowercase()
        val existing = dao.getUserByEmail(normalizedEmail)
        if (existing != null) {
            return@withContext Result.failure(Exception("An account with this email already exists."))
        }
        dao.logoutAllUsers()
        val newUser = UserEntity(
            name = name.trim(),
            email = normalizedEmail,
            password = password,
            age = age.trim(),
            allergies = "",
            medicines = "",
            healthInformation = "",
            isLoggedIn = true
        )
        val id = dao.insertUser(newUser)
        Result.success(newUser.copy(id = id))
    }

    suspend fun logIn(email: String, password: String): Result<UserEntity> = withContext(Dispatchers.IO) {
        val normalizedEmail = email.trim().lowercase()
        val user = dao.getUserByEmail(normalizedEmail)
        if (user == null || user.password != password) {
            return@withContext Result.failure(Exception("Invalid email or password."))
        }
        dao.logoutAllUsers()
        dao.setLoggedIn(user.id, true)
        Result.success(user.copy(isLoggedIn = true))
    }

    suspend fun logOut() = withContext(Dispatchers.IO) {
        dao.logoutAllUsers()
    }

    suspend fun updateCurrentUser(user: UserEntity) = withContext(Dispatchers.IO) {
        dao.updateUser(user)
    }

    // Fallback save
    fun getUserProfile(): Flow<UserEntity?> = dao.getLoggedInUser()

    suspend fun saveUserProfile(user: UserEntity) = withContext(Dispatchers.IO) {
        dao.updateUser(user)
    }

    // Pharmacist Questions
    fun getQuestions(): Flow<List<PharmacistQuestionEntity>> = dao.getAllQuestions()

    suspend fun submitQuestion(
        name: String,
        age: Int,
        question: String,
        medicineName: String = "",
        photoUri: String? = null
    ): Long = withContext(Dispatchers.IO) {
        val entity = PharmacistQuestionEntity(
            name = name,
            age = age,
            question = question,
            medicineName = medicineName,
            photoUri = photoUri,
            timestamp = System.currentTimeMillis(),
            status = "Under Review",
            pharmacistReply = "Your inquiry has been received by MIP Clinical Pharmacy Desk. A registered pharmacist will evaluate this query according to standard drug safety protocols."
        )
        dao.insertQuestion(entity)
    }

    companion object {
        fun calculateDistanceKm(
            lat1: Double, lon1: Double,
            lat2: Double, lon2: Double
        ): Double {
            val results = FloatArray(1)
            Location.distanceBetween(lat1, lon1, lat2, lon2, results)
            return (results[0] / 1000.0 * 10).toInt() / 10.0 // 1 decimal place
        }
    }
}
