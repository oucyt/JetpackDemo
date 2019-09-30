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
@Database(entities = [User::class, BreakfastTicket::class, UsageRecord::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun breakfastTicketDao(): BreakfastTicketDao

    abstract fun usageRecordDao(): UsageRecordDao

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java, "Sample.db"
            )
//                .addMigrations((object : Migration(1, 2) {
//                    override fun migrate(database: SupportSQLiteDatabase) {
//                        // 升级
////
////                        database.execSQL("CREATE TABLE `second_table` (`id` INTEGER, "
////                                + "`name` TEXT, PRIMARY KEY(`id`))")
////
////                        database.execSQL("alert table first_table "
////                                + "add column age integer")
//                    }
//                }))
//                .fallbackToDestructiveMigration()// 直接删除旧版本数据
                .build()

    }
}