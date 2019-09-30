package com.yt.jetpackdemo.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yt.jetpackdemo.advance.Converters
import java.util.*

/**
 * description
 * 使用记录
 * @author tianyu
 * @create 2019.06.11 15:02
 * @since 1.0.0
 */
@Entity(tableName = "table_usage_record")
@TypeConverters(Converters::class)
data class UsageRecord(
    /**
     * id
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    /**
     * 核销人姓名
     */
    @ColumnInfo(name = "name")
    var name: String,

    /**
     * 核销人身份证
     */
    @ColumnInfo(name = "id_number")
    var idNumber: String,

    /**
     * 现场照片
     */
    @ColumnInfo(name = "live_photo")
    var livePhoto: String,

    /**
     * 房间号
     */
    @ColumnInfo(name = "room_no")
    var roomNo: String,

    /**
     * 房间号
     */
    @ColumnInfo(name = "cuserroomdayid")
    var cuserRoomDayId: String,

    /**
     * 是否已上传
     */
    @ColumnInfo(name = "is_upload")
    var isUpload: Boolean,


    @ColumnInfo(name = "update_time")
    var updateTime: Date = Date(),

    @ColumnInfo(name = "create_time")
    var createTime: Date = Date()
) {
    constructor(
        id: Int, name: String, idNumber: String, livePhoto: String, roomNo: String, cuserRoomDayId: String

    ) : this(id, name, idNumber, livePhoto, roomNo, cuserRoomDayId, false, Date(), Date())
}
