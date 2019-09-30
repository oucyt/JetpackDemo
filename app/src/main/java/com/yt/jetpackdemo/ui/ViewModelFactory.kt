package com.yt.jetpackdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yt.jetpackdemo.persistence.BreakfastTicketDao
import com.yt.jetpackdemo.persistence.UsageRecordDao
import com.yt.jetpackdemo.persistence.UserDao

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.11 15:01
 * @since 1.0.0

 * Factory for ViewModels
 */
class ViewModelFactory(
    private val dataSource: UserDao,
    private val breakfastTicketDao: BreakfastTicketDao,
    private val usageRecordDao:  UsageRecordDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dataSource,breakfastTicketDao,usageRecordDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}