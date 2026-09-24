package com.example

import com.example.mippharmacare.data.local.InitialData
import com.example.mippharmacare.data.repository.PharmaRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MIPPharmaCareUnitTest {

    @Test
    fun testInitialMedicinesSeededCorrectly() {
        val medicines = InitialData.initialMedicines
        assertTrue("Medicines should not be empty", medicines.isNotEmpty())
        assertTrue("Should contain Pain & Fever medicines", medicines.any { it.category == "Pain & Fever" })
        assertTrue("Should contain Cold & Cough medicines", medicines.any { it.category == "Cold & Cough" })
        assertTrue("Should contain Allergy medicines", medicines.any { it.category == "Allergy" })
        assertTrue("Should contain Infection category", medicines.any { it.category == "Infection" })
    }

    @Test
    fun testInitialSymptomsContainWarningSigns() {
        val symptoms = InitialData.initialSymptoms
        assertTrue("Symptoms should not be empty", symptoms.isNotEmpty())
        symptoms.forEach { symptom ->
            assertTrue("Symptom warning signs must not be empty", symptom.warningSigns.isNotBlank())
            assertTrue("Symptom whenToConsultDoctor must not be empty", symptom.whenToConsultDoctor.isNotBlank())
            assertTrue("Symptom selfCare must not be empty", symptom.selfCare.isNotBlank())
        }
    }

    @Test
    fun testCalculateDistanceFormula() {
        // Yeola campus to Yeola station road (~0.4km)
        val dist = PharmaRepository.calculateDistanceKm(20.0425, 74.4891, 20.0450, 74.4920)
        assertTrue("Distance should be positive and small", dist in 0.1..2.0)
    }

    @Test
    fun testInitialPharmaciesList() {
        val pharmacies = InitialData.initialPharmacies
        assertTrue("Pharmacies should not be empty", pharmacies.isNotEmpty())
        val campusPharmacy = pharmacies.firstOrNull { it.pharmacyName.contains("Campus") }
        assertNotNull("Should contain Matoshri Campus Pharmacy", campusPharmacy)
    }
}
