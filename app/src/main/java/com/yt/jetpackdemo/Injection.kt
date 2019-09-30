package com.yt.jetpackdemo

import android.content.Context
import com.yt.jetpackdemo.persistence.BreakfastTicketDao
import com.yt.jetpackdemo.persistence.MyDatabase
import com.yt.jetpackdemo.persistence.UsageRecordDao
import com.yt.jetpackdemo.persistence.UserDao
import com.yt.jetpackdemo.ui.ViewModelFactory

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.11 14:55
 * @since 1.0.0

 * Enables injection of data sources.
 */
object Injection {

    fun provideUserDataSource(context: Context): UserDao {
        val database = MyDatabase.getInstance(context)
        return database.userDao()
    }

    private fun provideTicketDataSource(context: Context): BreakfastTicketDao {
        val database = MyDatabase.getInstance(context)
        return database.breakfastTicketDao()
    }

    private fun provideRecordDataSource(context: Context): UsageRecordDao {
        val database = MyDatabase.getInstance(context)
        return database.usageRecordDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val userSource = provideUserDataSource(context)
        val breakfastTicketDao = provideTicketDataSource(context)
        val usageRecordDao = provideRecordDataSource(context)
        return ViewModelFactory(userSource, breakfastTicketDao, usageRecordDao)
    }

}