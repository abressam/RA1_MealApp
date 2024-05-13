package com.example.ra1_somativa.feature.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.example.ra1_somativa.feature.data.dao.MealDao
import com.example.ra1_somativa.feature.data.dao.UserDao
import com.example.ra1_somativa.feature.data.model.MealEntity
import com.example.ra1_somativa.feature.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, MealEntity::class], version = 2, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mealDao(): MealDao

    companion object {

        private const val DATABASE_NAME = "user_database"

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `meals` " +
                        "(`mealIdApi` TEXT NOT NULL, " +
                        "`userId` INTEGER NOT NULL, " +
                        "`mealId` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON DELETE CASCADE ON UPDATE NO ACTION)")
            }
        }


        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): UserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class UserDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.userDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao) {
            userDao.deleteAll()

            val user = User("Amanda", "amanda@email.com", "senha123")
            userDao.insert(user)
        }
    }
}