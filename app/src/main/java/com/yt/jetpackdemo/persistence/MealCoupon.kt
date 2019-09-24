package com.yt.jetpackdemo.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yt.jetpackdemo.advance.Converters
import java.util.*

/**
 * description
 * 餐券表
 * @author tianyu
 * @create 2019.06.11 15:02
 * @since 1.0.0
 */
@Entity(tableName = "table_meal_coupon")
@TypeConverters(Converters::class)
data class MealCoupon(
    /**
     * 入住 id,唯一
     */
    @PrimaryKey
    @ColumnInfo(name = "checkinroom_id")
    val checkinRoomId: String,

    /**
     * 餐券数量
     */
    @ColumnInfo(name = "counts")
    var counts: Int,

    /**
     * 房间号
     */
    @ColumnInfo(name = "room_no")
    var roomNo: String,

    @ColumnInfo(name = "update_time")
    var updateTime: Date,

    @ColumnInfo(name = "create_time")
    var createTime: Date
)