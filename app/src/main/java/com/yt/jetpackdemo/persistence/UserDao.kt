package com.yt.jetpackdemo.persistence

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.11 15:08
 * @since 1.0.0
 */
/**
 * 数据表的访问对象
 * data access object for the users table.
 */
@Dao
interface UserDao {

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM users WHERE userid = :id")
    fun getUserById(id: String): Flowable<User>

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Completable


    @Query("SELECT * FROM users")
    fun queryAll(): Flowable<List<User>>


    @Query("select count(*) from users")
    fun getUserCount(): Flowable<Int>

    @Delete
    fun delete(user: User): Completable

    @Update
    fun update(user: User): Completable

    /**
     * Delete all users.
     */
    @Query("DELETE FROM Users")
    fun deleteAllUsers()
}