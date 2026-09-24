package com.example.mippharmacare.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mippharmacare.data.model.MedicineEntity
import com.example.mippharmacare.data.model.PharmacistQuestionEntity
import com.example.mippharmacare.data.model.PharmacyEntity
import com.example.mippharmacare.data.model.SymptomEntity
import com.example.mippharmacare.data.model.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        MedicineEntity::class,
        SymptomEntity::class,
        PharmacyEntity::class,
        UserEntity::class,
        PharmacistQuestionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MIPPharmaDatabase : RoomDatabase() {

    abstract fun pharmaDao(): MIPPharmaDao

    companion object {
        @Volatile
        private var INSTANCE: MIPPharmaDatabase? = null

        fun getInstance(context: Context): MIPPharmaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MIPPharmaDatabase::class.java,
                    "mip_pharma_care.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Populate initial educational database asynchronously
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = getInstance(context).pharmaDao()
                                dao.insertMedicines(InitialData.initialMedicines)
                                dao.insertSymptoms(InitialData.initialSymptoms)
                                dao.insertPharmacies(InitialData.initialPharmacies)
                                dao.saveUserProfile(InitialData.sampleUser)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
