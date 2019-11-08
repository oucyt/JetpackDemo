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
 * @create 2019.09.29 16:10
 * @since 1.0.0
 */

@RunWith(AndroidJUnit4::class)
class UsageRecordDaoTest {

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


    @Test
    fun getWhenInsert() {

        var start = DateHelper.getToday0Clock().time
        var end = DateHelper.getToday24Clock().time
        database.usageRecordDao()
            .queryUsageRecordByIdNumber("123", start, end)
            .test()
            .assertNoValues()
    }


    @Test
    fun insertAndGet(){

        database.usageRecordDao()
            .insert(usageRecord)
            .blockingAwait()
//
//        database.usageRecordDao()
//            .update(usageRecord)
//            .blockingAwait()

        var start = DateHelper.getToday0Clock().time
        var end = DateHelper.getToday24Clock().time

        database.usageRecordDao()
            .queryUsageRecordByIdNumber("idCard",start,end)
            .test()
            .assertValue {
                it.roomNo == usageRecord.roomNo && !it.isUpload
            }
    }

    @Test
    fun testGroupBy(){

        database.usageRecordDao()
            .insert(usageRecord)
            .blockingAwait()
        database.usageRecordDao()
            .insert(usageRecord)
            .blockingAwait()

        database.usageRecordDao()
            .testOne()
            .test()
            .assertValue {
                it.size==2
            }

    }

    companion object {
        private val usageRecord = UsageRecord(0,"name","idCard","livePhoto", "roomNo","curserRoomDayId")
    }
}