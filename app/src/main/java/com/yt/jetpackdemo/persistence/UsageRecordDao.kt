package com.yt.jetpackdemo.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yt.jetpackdemo.persistence.UsageRecord
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * description
 *
 * @author tianyu
 * @create 2019.09.25 10:42
 * @since 1.0.0
 */
@Dao
interface UsageRecordDao {


    @Insert
    fun insert(usageRecord: UsageRecord): Completable


    @Update
    fun update(usageRecord: UsageRecord): Completable


    /**
     * 查询使用记录
     */
    @Query("select * from table_usage_record where id_number=:idNumber AND create_time >= :start AND create_time <= :end")
    fun queryUsageRecordByIdNumber(idNumber: String, start: Long, end: Long): Maybe<UsageRecord>


    @Query("select count(*) from table_usage_record where create_time >= :start AND create_time <= :end")
    fun getAllCount(start: Long, end: Long): Maybe<Int>

    @Query("select count(*) from table_usage_record  where is_upload = false")
    fun getUploadCount(/*start: Long, end: Long*/): Maybe<Int>

    @Query("select * from table_usage_record  where is_upload = false")
    fun getUnUnload(): Maybe<List<UsageRecord>>

    @Query("select name|| '&&' ||id_number from table_usage_record group by name")
    fun testOne(): Maybe<List<String>>
}