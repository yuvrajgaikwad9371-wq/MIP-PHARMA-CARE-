package com.example.mippharmacare.data.local

import com.example.mippharmacare.data.model.MedicineEntity
import com.example.mippharmacare.data.model.PharmacyEntity
import com.example.mippharmacare.data.model.SymptomEntity
import com.example.mippharmacare.data.model.UserEntity

object InitialData {

    val sampleUser = UserEntity(
        id = 1,
        email = "patient@mippharma.com",
        password = "password123",
        name = "Yuvraj Gaikwad",
        age = "22",
        allergies = "None reported",
        medicines = "Vitamin C supplement",
        healthInformation = "MIP College Yeola - General Wellness",
        isLoggedIn = true
    )

    val initialMedicines = listOf(
        // 1. Pain & Fever
        MedicineEntity(
            medicineName = "Paracetamol (Acetaminophen)",
            activeIngredient = "Paracetamol / Acetaminophen 500mg - 650mg",
            category = "Pain & Fever",
            generalUses = "Relief of mild to moderate pain (headaches, muscular aches, toothaches) and reduction of fever.",
            precautions = "Do not exceed recommended maximum daily dose (max 4,000mg in 24 hours for healthy adults). Avoid combining with other paracetamol-containing combination cold/flu products. Consult doctor if you have liver impairment or chronic alcohol intake.",
            sideEffects = "Generally well-tolerated at therapeutic dosages. Rare allergic skin rash. Severe liver toxicity occurs in overdose.",
            contraindications = "Severe hepatic impairment or active liver failure, known hypersensitivity to paracetamol.",
            interactions = "Warfarin (heavy regular use may enhance anticoagulant effect), Isoniazid, chronic heavy alcohol consumption.",
            storage = "Store in a cool, dry place below 30°C, protected from light and out of reach of children.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "WHO Model Formulary & Indian Pharmacopoeia"
        ),
        MedicineEntity(
            medicineName = "Ibuprofen",
            activeIngredient = "Ibuprofen 200mg - 400mg",
            category = "Pain & Fever",
            generalUses = "Non-steroidal anti-inflammatory drug (NSAID) for relief of inflammatory pain, headache, dental pain, and dysmenorrhea.",
            precautions = "Always take with food or milk to prevent gastric irritation. Not recommended during late pregnancy or for patients with history of peptic ulcers or asthma sensitive to NSAIDs.",
            sideEffects = "Dyspepsia, heartburn, nausea, gastrointestinal discomfort, mild dizziness.",
            contraindications = "Active gastric ulcer, severe heart failure, advanced renal failure, third trimester of pregnancy.",
            interactions = "Aspirin, other NSAIDs, anticoagulants (blood thinners), ACE inhibitors, diuretics.",
            storage = "Store in airtight containers below 25°C, away from moisture.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "British National Formulary (BNF)"
        ),

        // 2. Cold & Cough
        MedicineEntity(
            medicineName = "Dextromethorphan Cough Syrup",
            activeIngredient = "Dextromethorphan Hydrobromide 10mg / 5ml",
            category = "Cold & Cough",
            generalUses = "Temporary suppression of dry, non-productive irritating cough.",
            precautions = "Not for chronic cough associated with smoking, asthma, or cough with excessive phlegm. Avoid taking with MAO inhibitors.",
            sideEffects = "Mild drowsiness, dizziness, mild nausea.",
            contraindications = "Patients taking MAOIs within 14 days, severe respiratory depression, productive cough needing expectoration.",
            interactions = "Monoamine oxidase inhibitors (MAOIs), SSRIs, sedatives.",
            storage = "Store at room temperature between 15°C - 30°C. Protect from freezing.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "FDA MedWatch & National Health Portal"
        ),
        MedicineEntity(
            medicineName = "Guaifenesin Expectorant",
            activeIngredient = "Guaifenesin 100mg / 5ml",
            category = "Cold & Cough",
            generalUses = "Helps loosen phlegm and thin bronchial secretions to make coughs more productive.",
            precautions = "Drink plenty of water to help thin mucus. Consult a physician if cough lasts more than 7 days.",
            sideEffects = "Mild stomach upset, nausea, headache.",
            contraindications = "Known hypersensitivity to guaifenesin.",
            interactions = "No clinically significant drug interactions documented.",
            storage = "Store at room temperature 20°C - 25°C in original container.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "US Pharmacopeia (USP)"
        ),

        // 3. Allergy
        MedicineEntity(
            medicineName = "Cetirizine",
            activeIngredient = "Cetirizine Hydrochloride 10mg",
            category = "Allergy",
            generalUses = "Relief of allergy symptoms such as sneezing, runny nose, itching eyes, urticaria (hives), and allergic rhinitis.",
            precautions = "May cause mild sedation in some individuals; exercise caution when driving or operating machinery. Moderate alcohol consumption.",
            sideEffects = "Somnolence (sleepiness), fatigue, dry mouth, headache.",
            contraindications = "Severe end-stage renal impairment (CrCl < 10 ml/min), hypersensitivity to hydroxyzine.",
            interactions = "CNS depressants, alcohol, sedatives (may enhance drowsiness).",
            storage = "Store below 25°C in a dry environment.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "WHO Essential Medicines List"
        ),
        MedicineEntity(
            medicineName = "Fexofenadine",
            activeIngredient = "Fexofenadine Hydrochloride 120mg / 180mg",
            category = "Allergy",
            generalUses = "Non-sedating antihistamine for seasonal allergic rhinitis and chronic idiopathic urticaria.",
            precautions = "Avoid taking with fruit juices (such as grapefruit, orange, apple) as they can significantly reduce absorption.",
            sideEffects = "Headache, nausea, dizziness (minimal sedation compared to first-generation).",
            contraindications = "Known allergy to fexofenadine or components.",
            interactions = "Aluminium/Magnesium antacids (take 2 hours apart), erythromycin, ketoconazole.",
            storage = "Store at 20°C - 25°C, excursions permitted between 15°C and 30°C.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "FDA Drug Safety Guide"
        ),

        // 4. Acidity
        MedicineEntity(
            medicineName = "Pantoprazole",
            activeIngredient = "Pantoprazole Gastro-resistant 40mg",
            category = "Acidity",
            generalUses = "Proton pump inhibitor (PPI) for GERD (acid reflux), erosive esophagitis, and gastric hypersecretion.",
            precautions = "Best taken 30-60 minutes before breakfast. Prolonged use requires medical supervision regarding vitamin B12 and magnesium levels.",
            sideEffects = "Headache, diarrhea, nausea, abdominal discomfort, flatulence.",
            contraindications = "Concomitant administration with rilpivirine, severe hypersensitivity to benzimidazoles.",
            interactions = "Ketoconazole, Methotrexate, Clopidogrel, Atazanavir.",
            storage = "Store in original blister pack below 30°C to protect from moisture.",
            prescriptionStatus = "Prescription Only (Rx)",
            source = "National Formulary of India (NFI)"
        ),
        MedicineEntity(
            medicineName = "Antacid Suspension (Aluminium & Magnesium Hydroxide)",
            activeIngredient = "Aluminium Hydroxide + Magnesium Hydroxide + Simethicone",
            category = "Acidity",
            generalUses = "Rapid symptomatic relief of heartburn, sour stomach, acid indigestion, and gas.",
            precautions = "Take 1-2 hours after meals and at bedtime. Do not take within 2 hours of other oral medications.",
            sideEffects = "Magnesium may cause mild diarrhea; aluminium may cause constipation (balanced formulation).",
            contraindications = "Severe renal failure (risk of aluminium/magnesium toxicity).",
            interactions = "Tetracyclines, fluoroquinolones, iron supplements (drastically binds and reduces absorption).",
            storage = "Keep bottle tightly closed, protect from freezing. Shake well before use.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "British Pharmacopoeia (BP)"
        ),

        // 5. Skin Care
        MedicineEntity(
            medicineName = "Calamine Lotion",
            activeIngredient = "Calamine (Zinc oxide with Ferric oxide) & Zinc Oxide",
            category = "Skin Care",
            generalUses = "Soothing topical agent for minor skin irritations, insect bites, poison ivy, sunburn, and itching.",
            precautions = "For external use only. Avoid contact with eyes, mucous membranes, or open broken wounds.",
            sideEffects = "Very rare localized skin irritation or dryness.",
            contraindications = "Open oozing wounds, severe extensive burns.",
            interactions = "No known topical drug interactions.",
            storage = "Store below 30°C. Shake well before applying.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "Indian Pharmacopoeia (IP)"
        ),
        MedicineEntity(
            medicineName = "Clotrimazole Topical Cream 1%",
            activeIngredient = "Clotrimazole 1% w/w",
            category = "Skin Care",
            generalUses = "Broad-spectrum antifungal for superficial fungal skin infections (athlete's foot, ringworm, jock itch).",
            precautions = "Apply thinly to clean dry affected area. Continue use for prescribed duration even after symptoms subside.",
            sideEffects = "Mild stinging, erythema, localized burning or irritation.",
            contraindications = "Hypersensitivity to clotrimazole or imidazole antifungals.",
            interactions = "None significant when applied topically.",
            storage = "Store below 25°C. Do not freeze.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "WHO Essential Topical Medicines"
        ),

        // 6. Infection
        MedicineEntity(
            medicineName = "Amoxicillin (Antibiotic - Prescription Only)",
            activeIngredient = "Amoxicillin Trihydrate 500mg",
            category = "Infection",
            generalUses = "Penicillin antibiotic for susceptible bacterial infections. NEVER for viral colds, flu, or self-treatment!",
            precautions = "CRITICAL: Must ONLY be taken on written prescription from a licensed doctor after proper clinical diagnosis. Complete the full prescribed course to prevent antimicrobial resistance.",
            sideEffects = "Diarrhea, nausea, skin rash, fungal superinfection (thrush). Severe anaphylaxis in penicillin allergic individuals.",
            contraindications = "Confirmed severe allergy/anaphylaxis to penicillin, ampicillin, or beta-lactams.",
            interactions = "Oral contraceptives (may reduce efficacy), Warfarin, Allopurinol (increased rash incidence).",
            storage = "Store below 25°C in dry conditions.",
            prescriptionStatus = "Prescription Only (Rx)",
            source = "WHO Global Antibiotic Guidelines"
        ),

        // 7. Cardiovascular
        MedicineEntity(
            medicineName = "Amlodipine",
            activeIngredient = "Amlodipine Besylate 5mg",
            category = "Cardiovascular",
            generalUses = "Calcium channel blocker for management of essential hypertension and chronic stable angina.",
            precautions = "Requires ongoing physician monitoring of blood pressure. Do not stop abruptly without doctor advice. Report peripheral ankle swelling.",
            sideEffects = "Peripheral edema (ankle swelling), headache, flushing, fatigue, dizziness upon standing.",
            contraindications = "Severe hypotension, cardiogenic shock, unstable heart failure post myocardial infarction.",
            interactions = "Simvastatin (limit simvastatin dose), CYP3A4 inhibitors (ketoconazole, diltiazem), grapefruit juice.",
            storage = "Store below 25°C away from direct sunlight.",
            prescriptionStatus = "Prescription Only (Rx)",
            source = "AHA/ACC Clinical Practice Guidelines"
        ),

        // 8. Diabetes
        MedicineEntity(
            medicineName = "Metformin",
            activeIngredient = "Metformin Hydrochloride 500mg - 1000mg",
            category = "Diabetes",
            generalUses = "First-line oral antihyperglycemic biguanide for type 2 diabetes mellitus.",
            precautions = "Take with or immediately after meals to reduce GI disturbance. Doctor must monitor renal (kidney) function periodically. Stop before iodinated radiocontrast procedures.",
            sideEffects = "Diarrhea, nausea, metallic taste in mouth, abdominal bloating. Rare: lactic acidosis.",
            contraindications = "Severe renal impairment (eGFR < 30 mL/min), acute metabolic acidosis, severe hypoxemia.",
            interactions = "Iodinated contrast media, alcohol (increases risk of lactic acidosis), cimetidine.",
            storage = "Store at 20°C - 25°C away from high humidity.",
            prescriptionStatus = "Prescription Only (Rx)",
            source = "American Diabetes Association (ADA) Standards of Care"
        ),

        // 9. Mental Health
        MedicineEntity(
            medicineName = "Escitalopram",
            activeIngredient = "Escitalopram Oxalate 10mg",
            category = "Mental Health",
            generalUses = "Selective Serotonin Reuptake Inhibitor (SSRI) prescribed for major depressive disorder and anxiety disorders.",
            precautions = "Requires strict psychiatrist/physician supervision. Takes 2-4 weeks for full therapeutic effect. Never discontinue abruptly; taper under medical advice.",
            sideEffects = "Nausea, insomnia, sexual dysfunction, mild tremor, fatigue.",
            contraindications = "Concomitant use of MAOIs, congenital long QT syndrome, pimozide.",
            interactions = "MAOIs, St. John's Wort, NSAIDs (increased gastrointestinal bleeding risk), QT-prolonging drugs.",
            storage = "Store at controlled room temperature 15°C to 30°C.",
            prescriptionStatus = "Prescription Only (Rx)",
            source = "NICE Clinical Guidelines"
        ),

        // 10. Other
        MedicineEntity(
            medicineName = "Oral Rehydration Salts (ORS)",
            activeIngredient = "Sodium chloride, Potassium chloride, Sodium citrate, Dextrose (WHO formula)",
            category = "Other",
            generalUses = "Prevention and treatment of dehydration caused by acute diarrhea, vomiting, or excessive heat sweating.",
            precautions = "Dissolve contents of one sachet in exactly 1 liter of clean drinking water. Do not boil already dissolved solution. Discard unused portion after 24 hours.",
            sideEffects = "Extremely safe when diluted in specified volume of water. Vomiting if ingested too rapidly.",
            contraindications = "Severe intractable vomiting, complete intestinal obstruction, severe shock requiring IV resuscitation.",
            interactions = "No significant adverse pharmacological interactions.",
            storage = "Keep unmixed powder in moisture-proof packet in a cool, dry place.",
            prescriptionStatus = "OTC (Over-The-Counter)",
            source = "WHO / UNICEF Diarrheal Disease Control"
        )
    )

    val initialSymptoms = listOf(
        SymptomEntity(
            symptomName = "Headache",
            iconEmoji = "🤕",
            description = "Pain or discomfort in the head, scalp, or neck area. Most common are tension headaches, dehydration, or mild stress-related aches.",
            commonAssociations = "Mental stress, eye strain, dehydration, skipped meals, lack of sleep, or sinus congestion.",
            selfCare = "Rest in a quiet, dimly lit room. Stay hydrated by sipping water. Apply a cool cloth or gentle warm compress to your forehead or neck. Practice gentle neck stretches and avoid screen glare.",
            otcInformation = "Paracetamol (Acetaminophen) or Ibuprofen may provide temporary relief when used strictly according to package instructions. Never exceed maximum limits.",
            warningSigns = "Sudden 'thunderclap' severe headache, headache accompanied by high fever, stiff neck, confusion, seizure, visual loss, or following head trauma.",
            whenToConsultDoctor = "Seek emergency medical care immediately for sudden explosive pain, neurological changes, or stiff neck. Consult a physician if headaches occur more than twice a week.",
            source = "International Headache Society & Mayo Clinic"
        ),
        SymptomEntity(
            symptomName = "Fever",
            iconEmoji = "🌡️",
            description = "A temporary increase in body temperature, typically above 38°C (100.4°F), often part of the immune system's natural defense against infection.",
            commonAssociations = "Viral infections (common cold, flu), bacterial illnesses, immunizations, or heat exhaustion.",
            selfCare = "Rest adequately. Drink plentiful fluids (water, clear broths, oral rehydration). Wear light, breathable clothing. Keep room well-ventilated and comfortable.",
            otcInformation = "Paracetamol (Acetaminophen) is widely used as a fever reducer. Ibuprofen is an alternative for adults. Never give Aspirin to children or teenagers due to Reye's syndrome risk.",
            warningSigns = "Fever over 39.5°C (103°F) in adults, fever lasting over 3 days, difficulty breathing, confusion, persistent vomiting, rash, or any fever in infants under 3 months.",
            whenToConsultDoctor = "Infants under 3 months need immediate doctor evaluation. Adults should consult a doctor if fever exceeds 3 days or is accompanied by chest pain or shortness of breath.",
            source = "CDC Fever Guidance & Indian Academy of Pediatrics"
        ),
        SymptomEntity(
            symptomName = "Cold",
            iconEmoji = "🤧",
            description = "A mild viral upper respiratory infection causing nasal congestion, sneezing, sore throat, and mild tiredness.",
            commonAssociations = "Rhinoviruses, coronaviruses, adenovirus, seasonal temperature and humidity fluctuations.",
            selfCare = "Steam inhalation, saline nasal drops or rinses, warm honey-lemon water (for individuals over 1 year), generous rest, and high fluid intake.",
            otcInformation = "Saline nasal sprays, oral antihistamines (e.g. Cetirizine), and decongestant sprays (limit topical decongestants to max 3 days to prevent rebound congestion). NOTE: Antibiotics do NOT cure viral colds!",
            warningSigns = "Difficulty breathing, chest pain, wheezing, high unremitting fever, or cold symptoms persisting past 10-14 days without improvement.",
            whenToConsultDoctor = "Consult a doctor if earache develops, sinus pain worsens severely, or if you have pre-existing asthma or COPD.",
            source = "American Academy of Family Physicians (AAFP)"
        ),
        SymptomEntity(
            symptomName = "Cough",
            iconEmoji = "😷",
            description = "A protective reflex that helps clear your airways of irritants, mucus, or foreign particles.",
            commonAssociations = "Post-viral respiratory irritation, bronchitis, postnasal drip, acid reflux, or environmental irritants.",
            selfCare = "Keep throat moist with warm fluids, herbal teas, or honey (1 teaspoon for adults and kids >1 yr). Use a cool-mist humidifier in the bedroom.",
            otcInformation = "Guaifenesin can help thin sticky secretions (productive cough). Dextromethorphan can soothe dry tickly coughs. Avoid OTC cough medications in young children without pediatrician guidance.",
            warningSigns = "Coughing up blood or rust-colored phlegm, shortness of breath, unexplained weight loss, night sweats, or cough lasting longer than 3 weeks.",
            whenToConsultDoctor = "Consult a physician promptly if cough lasts longer than 3 weeks or if accompanied by breathlessness or chest discomfort.",
            source = "British Thoracic Society (BTS)"
        ),
        SymptomEntity(
            symptomName = "Acidity & Heartburn",
            iconEmoji = "🫃",
            description = "A burning sensation in the lower chest or sour regurgitation caused by gastric acid rising into the esophagus.",
            commonAssociations = "Spicy or fatty meals, lying down immediately after eating, caffeine, carbonated drinks, smoking, or stress.",
            selfCare = "Eat smaller, more frequent meals. Avoid lying down for at least 2-3 hours after meals. Elevate the head of your bed 6 inches. Avoid trigger foods (excess chili, citrus, alcohol).",
            otcInformation = "Liquid antacid gels (aluminium/magnesium hydroxide) offer rapid localized buffering. H2-blockers or short-course PPIs (under pharmacist guidance).",
            warningSigns = "Difficulty or pain when swallowing, unexplained weight loss, vomiting blood or coffee-ground material, black tarry stools, or pain radiating to the jaw/arm (could be cardiac).",
            whenToConsultDoctor = "Consult a doctor if heartburn occurs more than twice a week for several weeks, or if you experience difficulty swallowing.",
            source = "American College of Gastroenterology (ACG)"
        ),
        SymptomEntity(
            symptomName = "Nausea / Vomiting",
            iconEmoji = "🤢",
            description = "An unpleasant feeling of stomach unease with an involuntary urge to vomit.",
            commonAssociations = "Food poisoning, gastroenteritis (stomach bug), motion sickness, early pregnancy, or migraine.",
            selfCare = "Sip small amounts of clear fluids (diluted oral rehydration solution, water, ginger tea) every 15 minutes. Avoid solid, spicy, or fatty foods until settling.",
            otcInformation = "Oral Rehydration Salts (ORS) are fundamental to prevent electrolyte loss. Avoid heavy medications unless prescribed.",
            warningSigns = "Inability to keep liquids down for 24 hours, signs of severe dehydration (sunken eyes, no urination for 8 hours, extreme dizziness), vomit containing blood or dark particles.",
            whenToConsultDoctor = "Seek urgent care if vomiting persists over 24 hours in adults (or 12 hours in young children) or is accompanied by severe abdominal pain.",
            source = "NHS UK & WHO Guidance on Gastrointestinal Illness"
        ),
        SymptomEntity(
            symptomName = "Diarrhea",
            iconEmoji = "💩",
            description = "Frequent, loose, or watery bowel movements, typically resulting in rapid fluid and mineral loss.",
            commonAssociations = "Contaminated food or water, viral/bacterial gastroenteritis, food intolerance, or antibiotic-associated changes in gut flora.",
            selfCare = "The single most important self-care step is hydration with Oral Rehydration Solution (ORS), coconut water, or rice broth. Eat bland foods (bananas, rice, applesauce, toast - BRAT diet).",
            otcInformation = "ORS (WHO formula) is safest and essential. Do NOT self-prescribe antibiotics or anti-motility agents (like loperamide) if fever or bloody stool is present.",
            warningSigns = "Blood or mucus in stool, high fever, signs of severe dehydration, symptoms continuing past 48 hours without easing.",
            whenToConsultDoctor = "Consult a doctor immediately if stool contains visible blood or if diarrhea lasts more than 2 days in an adult.",
            source = "WHO Diarrheal Disease Factsheet"
        ),
        SymptomEntity(
            symptomName = "Allergy & Hives",
            iconEmoji = "🌸",
            description = "An exaggerated immune response causing sneezing, watery eyes, itchy skin patches, or raised red welts (urticaria).",
            commonAssociations = "Pollen, dust mites, pet dander, insect bites, certain foods, or seasonal environmental allergens.",
            selfCare = "Identify and avoid known allergen triggers. Apply cool compresses to itchy skin. Wear loose cotton clothing. Avoid hot showers that aggravate itchiness.",
            otcInformation = "Non-drowsy second-generation antihistamines (Cetirizine, Loratadine, Fexofenadine). Calamine lotion applied topically.",
            warningSigns = "EMERGENCY (Anaphylaxis): Swelling of lips, tongue, or throat; difficulty breathing or swallowing; feeling faint or dizziness. Call 108/112 or emergency immediately!",
            whenToConsultDoctor = "Seek immediate emergency treatment for airway swelling or difficulty breathing. Consult a doctor if hives last more than a few days.",
            source = "World Allergy Organization (WAO)"
        ),
        SymptomEntity(
            symptomName = "Minor Skin Irritation",
            iconEmoji = "🧴",
            description = "Mild localized redness, itching, or dryness of the skin from friction, heat, detergents, or insect contact.",
            commonAssociations = "Contact dermatitis, heat rash, dry skin, insect bites, soap or fragrance sensitivities.",
            selfCare = "Wash gently with mild fragrance-free soap and lukewarm water. Apply gentle unscented moisturizers or petroleum jelly. Avoid scratching to prevent secondary bacterial infection.",
            otcInformation = "Calamine lotion for soothing relief. Mild 1% hydrocortisone cream for short-term localized itching (avoid on face or broken skin unless advised).",
            warningSigns = "Rapidly spreading redness, warmth, pus or yellowish crusting, red streaks extending from the area, or accompanying fever.",
            whenToConsultDoctor = "Consult a dermatologist or general physician if the rash spreads quickly, oozes pus, or fails to improve within 5-7 days.",
            source = "American Academy of Dermatology (AAD)"
        ),
        SymptomEntity(
            symptomName = "Sleep Problems (Insomnia)",
            iconEmoji = "😴",
            description = "Difficulty falling asleep, staying asleep, or waking up unrefreshed despite having adequate opportunity for sleep.",
            commonAssociations = "Screen use before bed, irregular schedule, stress/anxiety, excessive caffeine or late meals, noise.",
            selfCare = "Maintain a consistent sleep-wake schedule 7 days a week. Keep bedroom cool, quiet, and dark. Avoid screens and caffeine 4-6 hours prior to bedtime. Engage in relaxing reading or deep breathing.",
            otcInformation = "Herbal chamomile tea. Long-term sleeping pills should NEVER be taken without thorough medical evaluation.",
            warningSigns = "Severe chronic insomnia lasting months, loud snoring accompanied by gasping or pauses in breathing (possible sleep apnea), daytime microsleep while driving.",
            whenToConsultDoctor = "Consult a physician or sleep specialist if sleep problems impair daily functioning, concentration, or mood.",
            source = "National Sleep Foundation & American Academy of Sleep Medicine"
        )
    )

    // Pre-seeded verified pharmacies around Yeola & Nashik (MIP College region) and major hubs
    val initialPharmacies = listOf(
        PharmacyEntity(
            pharmacyName = "Matoshri Campus Pharmacy",
            latitude = 20.0425,
            longitude = 74.4891,
            address = "MIP Campus, Dhanore, Yeola, Maharashtra 423401",
            phone = "+91 8793693213",
            openingHours = "8:00 AM - 10:00 PM (Mon - Sun)",
            isOpenNow = true,
            is24Hours = false,
            rating = 4.9f
        ),
        PharmacyEntity(
            pharmacyName = "Yeola City Medical & General Stores",
            latitude = 20.0450,
            longitude = 74.4920,
            address = "Station Road, Near Bus Stand, Yeola, Maharashtra 423401",
            phone = "+91 8793693213",
            openingHours = "24 Hours Open (7 Days)",
            isOpenNow = true,
            is24Hours = true,
            rating = 4.7f
        ),
        PharmacyEntity(
            pharmacyName = "Sanjivani 24/7 Day & Night Pharmacy",
            latitude = 20.0380,
            longitude = 74.4820,
            address = "Opp. Civil Hospital, Yeola-Manmad Road, Yeola 423401",
            phone = "+91 8793693213",
            openingHours = "24 Hours Open",
            isOpenNow = true,
            is24Hours = true,
            rating = 4.8f
        ),
        PharmacyEntity(
            pharmacyName = "Shree Ganesh Medical & Surgical",
            latitude = 20.0410,
            longitude = 74.4950,
            address = "Main Market Chowk, Yeola 423401",
            phone = "+91 8793693213",
            openingHours = "8:30 AM - 11:00 PM",
            isOpenNow = true,
            is24Hours = false,
            rating = 4.6f
        ),
        PharmacyEntity(
            pharmacyName = "Apollo Pharmacy - Nashik Highway Road",
            latitude = 19.9975,
            longitude = 73.7898,
            address = "College Road, Gangapur Naka, Nashik 422005",
            phone = "+91 8793693213",
            openingHours = "24 Hours Open",
            isOpenNow = true,
            is24Hours = true,
            rating = 4.8f
        ),
        PharmacyEntity(
            pharmacyName = "MedPlus Healthcare & Druggists",
            latitude = 20.0020,
            longitude = 73.7745,
            address = "Near Canada Corner, Sharanpur Road, Nashik 422002",
            phone = "+91 8793693213",
            openingHours = "7:00 AM - 11:30 PM",
            isOpenNow = true,
            is24Hours = false,
            rating = 4.7f
        ),
        PharmacyEntity(
            pharmacyName = "Lifeline 24-Hour Emergency Pharmacy",
            latitude = 20.0090,
            longitude = 73.7910,
            address = "Mumbai Naka, Highway Circle, Nashik 422001",
            phone = "+91 8793693213",
            openingHours = "24 Hours Open (Emergency Care)",
            isOpenNow = true,
            is24Hours = true,
            rating = 4.9f
        )
    )
}
