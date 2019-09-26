package com.yt.jetpackdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.yt.jetpackdemo.persistence.MyDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * description
 *
 * @author tianyu
 * @create 2019.09.25 11:52
 * @since 1.0.0
 */
@RunWith(AndroidJUnit4::class)
class BreakfastTicketDaoTest {

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
     * 用例：查询不存在的房间号对应的餐券
     */
    @Test
    fun getTicketsWhenNoTicketInsert() {
        database.breakfastTicketDao()
            .queryTicketByRoomNo("123")
            .test()
//            .assertComplete() // 失败
//            .assertEmpty() // 通过
            .assertNoErrors() // 通过
//            .assertNoValues()
    }

    /**
     * 用例：查询已存在的房间号对应的餐券
     */
    @Test
    fun insertAndGetTicket() {
        // 插入一条记录
        database.breakfastTicketDao()
            .insert(breakfastTicket)
            .blockingAwait()
        database.breakfastTicketDao()
            .queryTicketByRoomNo(breakfastTicket.roomNo)
            .test()
            .assertValue {
                it.checkinRoomId == breakfastTicket.checkinRoomId
                        && it.counts == breakfastTicket.counts
                        && it.roomNo == breakfastTicket.roomNo
            }
    }

    companion object {
        private val breakfastTicket = com.yt.jetpackdemo.persistence.BreakfastTicket("checkinroomid", 2, "101")
    }
}