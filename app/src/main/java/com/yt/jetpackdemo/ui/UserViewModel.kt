package com.yt.jetpackdemo.ui

import androidx.lifecycle.ViewModel
import com.yt.jetpackdemo.persistence.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import java.util.*

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.11 15:33
 * @since 1.0.0
 */
/**
 * View Model for the [UserActivity]
 */
class UserViewModel(
    private val dataSource: UserDao,
    private val ticketSource: BreakfastTicketDao,
    private val usageRecordDao: UsageRecordDao
) : ViewModel() {


    fun insertTicket(ticket: BreakfastTicket): Completable {
        return ticketSource.insert(ticket)
    }

    fun queryTicketByRoom(roomNo: String): Maybe<BreakfastTicket> {
        return ticketSource.queryTicketByRoomNo(roomNo, 0L, 1569728011055)
    }

    fun deleteAllTicket(): Completable {
        return ticketSource.deleteAll()
    }

    fun insert(user: User): Completable {
        return dataSource.insert(user)
    }


    fun insertRecord(usage: UsageRecord): Completable {
        return usageRecordDao.insert(usage)
    }


    fun queryAll(): Flowable<List<User>> {
        return dataSource.queryAll()
    }

    fun delete(user: User): Completable {
        return dataSource.delete(user)
    }

    fun update(user: User): Completable {
        return dataSource.update(user)
    }

    /**
     * 查询总记录数
     */
    fun userCount(): Flowable<Int> {
        return dataSource.getUserCount()
    }

    /**
     * Get the user name of the user.
     * @return a [Flowable] that will emit every time the user name has been updated.
     */
    // for every emission of the user, get the user name
    fun userName(): Flowable<String> {
        return dataSource.getUserById(USER_ID)
            .map { user -> user.userName }
    }

    /**
     * Update the user name.
     * @param userName the new user name
     * *
     * @return a [Completable] that completes when the user name is updated
     */
    fun updateUserName(userName: String): Completable {
        val user = User(USER_ID, userName, Date())
        return dataSource.insert(user)
    }

    fun testGroupBy(): Maybe<List<String>> {
        return usageRecordDao.testOne()
    }


    companion object {
        // using a hardcoded value for simplicity
        const val USER_ID = "1"
    }
}
