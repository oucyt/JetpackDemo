package com.yt.jetpackdemo

import android.content.Context
import com.yt.jetpackdemo.persistence.MealCouponDao
import com.yt.jetpackdemo.persistence.UserDao
import com.yt.jetpackdemo.persistence.UsersDatabase
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
        val database = UsersDatabase.getInstance(context)
        return database.userDao()
    }

    private fun provideCouponDataSource(context: Context): MealCouponDao {
        val database = UsersDatabase.getInstance(context)
        return database.mealCouponDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val userSource = provideUserDataSource(context)
        val couponSource: MealCouponDao = provideCouponDataSource(context)
        return ViewModelFactory(userSource, couponSource)
    }

}