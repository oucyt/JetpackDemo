package com.yt.jetpackdemo.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.11 15:13
 * @since 1.0.0
 */
/**
 * The Room database that contains the Users table
 */
@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UsersDatabase::class.java, "Sample.db"
            )
                .build()
    }
}