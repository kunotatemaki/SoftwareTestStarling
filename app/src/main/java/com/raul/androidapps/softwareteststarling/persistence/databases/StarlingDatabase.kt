package com.raul.androidapps.softwareteststarling.persistence.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raul.androidapps.softwareteststarling.persistence.daos.AccountDao
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.utils.DatabasePopulateTool
import com.raul.androidapps.softwareteststarling.persistence.utils.DbConverters
import com.raul.androidapps.softwareteststarling.persistence.utils.PersistenceConstants
import com.raul.androidapps.softwareteststarling.preferences.PreferencesConstants
import com.raul.androidapps.softwareteststarling.preferences.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [(AccountEntity::class)], exportSchema = false, version = 1)
@TypeConverters(DbConverters::class)
abstract class StarlingDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    companion object {

        @Volatile
        private var INSTANCE: StarlingDatabase? = null

        fun getInstance(
            context: Context,
            preferenceManager: PreferencesManager,
            databasePopulateTool: DatabasePopulateTool
        ): StarlingDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(
                    context,
                    preferenceManager,
                    databasePopulateTool
                ).also { INSTANCE = it }
            }

        private fun buildDatabase(
            context: Context,
            preferenceManager: PreferencesManager,
            databasePopulateTool: DatabasePopulateTool
        ) =
            Room.databaseBuilder(
                context,
                StarlingDatabase::class.java, PersistenceConstants.DATABASE_NAME
            )
                //.addMigrations()    //no migrations, version 1
                .fallbackToDestructiveMigration()
                // prepopulate the database after onCreate was called
                //Uncomment if populate on start is needed
                /*.addCallback(object : RoomDatabase.Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        if (!preferenceManager.getBooleanFromPreferences(PreferencesConstants.PROPERTY_DB_POPULATED)) {
                            //load fist the small file to get main cities ready earlier (for old devices)
                            GlobalScope.launch(Dispatchers.IO) {
                                //populate the db here
                                databasePopulateTool.populateDb()
                            }
                        }
                    }
                })*/
                .build()

    }
}
