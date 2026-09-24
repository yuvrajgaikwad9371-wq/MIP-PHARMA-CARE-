package com.example.mippharmacare.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mippharmacare.data.model.MedicineEntity
import com.example.mippharmacare.data.model.PharmacistQuestionEntity
import com.example.mippharmacare.data.model.PharmacyEntity
import com.example.mippharmacare.data.model.SymptomEntity
import com.example.mippharmacare.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MIPPharmaDao {

    // --- Medicines ---
    @Query("SELECT * FROM medicines ORDER BY medicine_name ASC")
    fun getAllMedicines(): Flow<List<MedicineEntity>>

    @Query("""
        SELECT * FROM medicines 
        WHERE medicine_name LIKE '%' || :query || '%' 
           OR active_ingredient LIKE '%' || :query || '%'
           OR general_uses LIKE '%' || :query || '%'
        ORDER BY medicine_name ASC
    """)
    fun searchMedicines(query: String): Flow<List<MedicineEntity>>

    @Query("SELECT * FROM medicines WHERE category = :category ORDER BY medicine_name ASC")
    fun getMedicinesByCategory(category: String): Flow<List<MedicineEntity>>

    @Query("SELECT * FROM medicines WHERE is_favorite = 1 ORDER BY medicine_name ASC")
    fun getFavoriteMedicines(): Flow<List<MedicineEntity>>

    @Query("SELECT * FROM medicines WHERE id = :id LIMIT 1")
    fun getMedicineById(id: Long): Flow<MedicineEntity?>

    @Query("UPDATE medicines SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun setMedicineFavorite(id: Long, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<MedicineEntity>)

    @Query("SELECT COUNT(*) FROM medicines")
    suspend fun getMedicineCount(): Int

    // --- Symptoms ---
    @Query("SELECT * FROM symptoms ORDER BY symptom_name ASC")
    fun getAllSymptoms(): Flow<List<SymptomEntity>>

    @Query("""
        SELECT * FROM symptoms 
        WHERE symptom_name LIKE '%' || :query || '%'
           OR description LIKE '%' || :query || '%'
           OR common_associations LIKE '%' || :query || '%'
        ORDER BY symptom_name ASC
    """)
    fun searchSymptoms(query: String): Flow<List<SymptomEntity>>

    @Query("SELECT * FROM symptoms WHERE id = :id LIMIT 1")
    fun getSymptomById(id: Long): Flow<SymptomEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymptoms(symptoms: List<SymptomEntity>)

    @Query("SELECT COUNT(*) FROM symptoms")
    suspend fun getSymptomCount(): Int

    // --- Pharmacies ---
    @Query("SELECT * FROM pharmacies ORDER BY pharmacy_name ASC")
    fun getAllPharmacies(): Flow<List<PharmacyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPharmacies(pharmacies: List<PharmacyEntity>)

    @Query("SELECT COUNT(*) FROM pharmacies")
    suspend fun getPharmacyCount(): Int

    // --- User Profile & Authentication ---
    @Query("SELECT * FROM users WHERE is_logged_in = 1 LIMIT 1")
    fun getLoggedInUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET is_logged_in = 0")
    suspend fun logoutAllUsers()

    @Query("UPDATE users SET is_logged_in = :isLoggedIn WHERE id = :userId")
    suspend fun setLoggedIn(userId: Long, isLoggedIn: Boolean)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    // Legacy fallback
    @Query("SELECT * FROM users LIMIT 1")
    fun getUserProfile(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(user: UserEntity)

    // --- Pharmacist Questions ---
    @Query("SELECT * FROM pharmacist_questions ORDER BY timestamp DESC")
    fun getAllQuestions(): Flow<List<PharmacistQuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: PharmacistQuestionEntity): Long
}
