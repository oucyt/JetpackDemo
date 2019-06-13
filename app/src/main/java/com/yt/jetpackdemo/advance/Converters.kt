package com.yt.jetpackdemo.advance

import androidx.room.TypeConverter
import java.util.*

/**
 * description
 * 数据转换
 * @author tianyu
 * @create 2019.06.13 16:01
 * @since 1.0.0
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
