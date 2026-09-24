package com.example.mippharmacare.ui.viewmodel

import android.app.Application
import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mippharmacare.data.local.MIPPharmaDatabase
import com.example.mippharmacare.data.model.MedicineEntity
import com.example.mippharmacare.data.model.PharmacistQuestionEntity
import com.example.mippharmacare.data.model.PharmacyEntity
import com.example.mippharmacare.data.model.SymptomEntity
import com.example.mippharmacare.data.model.UserEntity
import com.example.mippharmacare.data.repository.PharmaRepository
import com.example.mippharmacare.data.repository.PharmacyWithDistance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PharmaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = MIPPharmaDatabase.getInstance(application)
    private val repository = PharmaRepository(db.pharmaDao())

    init {
        viewModelScope.launch {
            repository.ensureDataSeeded()
        }
    }

    // --- Search & Medicines ---
    private val _medicineSearchQuery = MutableStateFlow("")
    val medicineSearchQuery: StateFlow<String> = _medicineSearchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val medicineList: StateFlow<List<MedicineEntity>> = combine(
        _medicineSearchQuery,
        _selectedCategory
    ) { query, category ->
        Pair(query, category)
    }.flatMapLatest { (query, category) ->
        if (query.isNotBlank()) {
            repository.searchMedicines(query)
        } else {
            repository.getMedicinesByCategory(category)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteMedicines: StateFlow<List<MedicineEntity>> = repository.getFavoriteMedicines()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedMedicine = MutableStateFlow<MedicineEntity?>(null)
    val selectedMedicine: StateFlow<MedicineEntity?> = _selectedMedicine.asStateFlow()

    fun onMedicineSearchChange(query: String) {
        _medicineSearchQuery.value = query
    }

    fun onCategorySelect(category: String) {
        _selectedCategory.value = category
    }

    fun selectMedicine(medicine: MedicineEntity?) {
        _selectedMedicine.value = medicine
    }

    fun toggleFavorite(medicine: MedicineEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(medicine.id, medicine.isFavorite)
        }
    }

    // --- Symptoms ---
    private val _symptomSearchQuery = MutableStateFlow("")
    val symptomSearchQuery: StateFlow<String> = _symptomSearchQuery.asStateFlow()

    val symptomList: StateFlow<List<SymptomEntity>> = _symptomSearchQuery.flatMapLatest { query ->
        repository.searchSymptoms(query)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedSymptom = MutableStateFlow<SymptomEntity?>(null)
    val selectedSymptom: StateFlow<SymptomEntity?> = _selectedSymptom.asStateFlow()

    fun onSymptomSearchChange(query: String) {
        _symptomSearchQuery.value = query
    }

    fun selectSymptom(symptom: SymptomEntity?) {
        _selectedSymptom.value = symptom
    }

    // --- Pharmacies & Location ---
    private val _pharmacyFilter = MutableStateFlow("All") // "All", "Open Now", "24 Hours", "Nearest"
    val pharmacyFilter: StateFlow<String> = _pharmacyFilter.asStateFlow()

    private val _userLatitude = MutableStateFlow<Double?>(null)
    val userLatitude: StateFlow<Double?> = _userLatitude.asStateFlow()

    private val _userLongitude = MutableStateFlow<Double?>(null)
    val userLongitude: StateFlow<Double?> = _userLongitude.asStateFlow()

    private val _isLocating = MutableStateFlow(false)
    val isLocating: StateFlow<Boolean> = _isLocating.asStateFlow()

    private val _locationName = MutableStateFlow("Yeola / Dhanore, Nashik")
    val locationName: StateFlow<String> = _locationName.asStateFlow()

    val pharmacyList: StateFlow<List<PharmacyWithDistance>> = combine(
        _pharmacyFilter,
        _userLatitude,
        _userLongitude
    ) { filter, lat, lng ->
        Triple(filter, lat, lng)
    }.flatMapLatest { (filter, lat, lng) ->
        repository.getPharmacies(filter, lat, lng)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setPharmacyFilter(filter: String) {
        _pharmacyFilter.value = filter
    }

    fun updateUserLocation(lat: Double, lng: Double, label: String = "GPS Live Location") {
        _userLatitude.value = lat
        _userLongitude.value = lng
        _locationName.value = label
        _isLocating.value = false
    }

    fun setLocating(locating: Boolean) {
        _isLocating.value = locating
    }

    fun manualSearchLocation(query: String) {
        _locationName.value = query
        // If searching a known hub or manual town:
        when {
            query.contains("nashik", ignoreCase = true) -> {
                updateUserLocation(19.9975, 73.7898, "Nashik City")
            }
            query.contains("yeola", ignoreCase = true) -> {
                updateUserLocation(20.0425, 74.4891, "Yeola, Nashik")
            }
            query.contains("dhanore", ignoreCase = true) -> {
                updateUserLocation(20.0425, 74.4891, "Dhanore (MIP Campus)")
            }
            query.contains("pune", ignoreCase = true) -> {
                updateUserLocation(18.5204, 73.8567, "Pune Region")
            }
            query.contains("mumbai", ignoreCase = true) -> {
                updateUserLocation(19.0760, 72.8777, "Mumbai Region")
            }
            else -> {
                // Keep default region Yeola/Dhanore
                updateUserLocation(20.0425, 74.4891, query)
            }
        }
    }

    // --- Ask a Pharmacist ---
    val pharmacistQuestions: StateFlow<List<PharmacistQuestionEntity>> = repository.getQuestions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _submissionSuccess = MutableStateFlow(false)
    val submissionSuccess: StateFlow<Boolean> = _submissionSuccess.asStateFlow()

    fun submitQuestion(
        name: String,
        age: Int,
        question: String,
        medicineName: String = "",
        photoUri: String? = null,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            repository.submitQuestion(name, age, question, medicineName, photoUri)
            _submissionSuccess.value = true
            onSuccess()
        }
    }

    fun resetSubmissionStatus() {
        _submissionSuccess.value = false
    }

    // --- User Profile & Authentication ---
    val userProfile: StateFlow<UserEntity?> = repository.getLoggedInUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private val _authSuccessMessage = MutableStateFlow<String?>(null)
    val authSuccessMessage: StateFlow<String?> = _authSuccessMessage.asStateFlow()

    fun clearAuthMessages() {
        _authError.value = null
        _authSuccessMessage.value = null
    }

    fun signUp(name: String, email: String, pass: String, age: String, onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            _authError.value = "Please fill in all required fields."
            return
        }
        viewModelScope.launch {
            val result = repository.signUp(name, email, pass, age)
            result.onSuccess {
                _authError.value = null
                _authSuccessMessage.value = "Account created successfully!"
                onSuccess()
            }.onFailure {
                _authError.value = it.message ?: "Sign up failed."
            }
        }
    }

    fun logIn(email: String, pass: String, onSuccess: () -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            _authError.value = "Please enter both email and password."
            return
        }
        viewModelScope.launch {
            val result = repository.logIn(email, pass)
            result.onSuccess {
                _authError.value = null
                _authSuccessMessage.value = "Logged in successfully!"
                onSuccess()
            }.onFailure {
                _authError.value = it.message ?: "Login failed."
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            repository.logOut()
            _authSuccessMessage.value = "Logged out."
        }
    }

    fun updateUserProfile(
        name: String,
        age: String,
        allergies: String,
        medicines: String,
        healthInfo: String
    ) {
        viewModelScope.launch {
            val current = userProfile.value
            if (current != null) {
                repository.updateCurrentUser(
                    current.copy(
                        name = name,
                        age = age,
                        allergies = allergies,
                        medicines = medicines,
                        healthInformation = healthInfo
                    )
                )
            } else {
                repository.saveUserProfile(
                    UserEntity(
                        name = name,
                        age = age,
                        allergies = allergies,
                        medicines = medicines,
                        healthInformation = healthInfo,
                        isLoggedIn = true
                    )
                )
            }
        }
    }

    // --- Emergency Dialog State ---
    private val _showEmergencyHelp = MutableStateFlow(false)
    val showEmergencyHelp: StateFlow<Boolean> = _showEmergencyHelp.asStateFlow()

    fun setEmergencyHelpVisible(visible: Boolean) {
        _showEmergencyHelp.value = visible
    }
}
