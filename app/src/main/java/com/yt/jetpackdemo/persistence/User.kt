package com.yt.jetpackdemo.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.11 15:02
 * @since 1.0.0
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "userid")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "username")
    var userName: String
)