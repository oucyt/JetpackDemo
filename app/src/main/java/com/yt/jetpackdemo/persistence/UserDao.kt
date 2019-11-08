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
 *
 * 1.@Query提供的返回类型:Publisher,Flowable,Observable
 * 2.@Insert,@Update,@Delete提供的返回类型:Complete,Single和Maybe
 * 3.由于 RxJava 响应流不允许空值，如果确实没有查询到记录就不会分发事件,可以通过Flowable<T[]> or Flowable<List<T>>代替Flowable<T> or Publisher<T>解决这个限制。
 * 4.Flowable<T> and Publisher<T>会在数据变更时触发响应，如果你不想订阅数据变化，那么使用 Maybe<T> or Single<T>.返回值如果使用Single<T>，当查询结果为空时，Room会抛出EmptyResultSetException异常。
 * 5.INSERT会返回 void or Long。返回的 long 值代表新增记录所在 rowid。当插入多条记录时，并不会返回多个 rowid,所以如果想要返回 long 就不要执行插入多条记录
 * 6.UPDATE or DELETE 返回 void or int。返回的 int 代表本次查询影响到记录条数
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert2(user: User): Long


    @Query("SELECT * FROM users")
    fun queryAll(): Flowable<List<User>>


    @Query("select count(*) from users")
    fun getUserCount(): Flowable<Int>

    @Delete
    fun delete(user: User): Completable
    @Delete
    fun delete2(user: User): Int

    @Update
    fun update(user: User): Completable

    @Update
    fun update2(user: User): Int

    /**
     * Delete all users.
     */
    @Query("DELETE FROM Users")
    fun deleteAllUsers()
}