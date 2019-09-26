package com.yt.jetpackdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.yt.jetpackdemo.persistence.MyDatabase
import com.yt.jetpackdemo.persistence.User
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.12 20:26
 * @since 1.0.0
 */
/**
 * Test the implementation of [UserDao]
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MyDatabase

    @Before
    fun initDb() {
        // 使用内存数据库，因为测试后数据会被清除
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            MyDatabase::class.java
        )
            // 这个方法就是为了方便测试
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    /**
     * 测试用例：查询 empty table
     */
    @Test
    fun getUsersWhenNoUserInserted() {
        database.userDao().getUserById("123")
            .test()
            .assertNoValues()
    }

    /**
     * 用例：插值并查询
     */
    @Test
    fun insertAndGetUser() {
        // When inserting a new user in the data source
        database.userDao().insert(USER).blockingAwait()

        // When subscribing to the emissions of the user
        database.userDao().getUserById(USER.id)
            .test()
            // assertValue asserts that there was only one emission of the user
            .assertValue {
                it.id == USER.id && it.userName == USER.userName }
    }

    /**
     * 用例：更新并查询
     */
    @Test
    fun updateAndGetUser() {
        // Given that we have a user in the data source
        database.userDao().insert(USER).blockingAwait()

        // When we are updating the name of the user
        val updatedUser = User(USER.id, "new username", Date())
        database.userDao().insert(updatedUser).blockingAwait()

        // When subscribing to the emissions of the user
        database.userDao().getUserById(USER.id)
            .test()
            // assertValue asserts that there was only one emission of the user
            .assertValue { it.id == USER.id && it.userName == "new username" }
    }

    /**
     * 用例：删除并查询
     */
    @Test
    fun deleteAndGetUser() {
        // Given that we have a user in the data source
        database.userDao().insert(USER).blockingAwait()

        //When we are deleting all users
        database.userDao().deleteAllUsers()
        // When subscribing to the emissions of the user
        database.userDao().getUserById(USER.id)
            .test()
            // check that there's no user emitted
            .assertNoValues()
    }

    companion object {
        private val USER = User("id", "username", Date())
    }
}
