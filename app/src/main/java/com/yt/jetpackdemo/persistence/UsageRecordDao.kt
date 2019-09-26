package com.yt.jetpackdemo.persistence

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Completable

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

}