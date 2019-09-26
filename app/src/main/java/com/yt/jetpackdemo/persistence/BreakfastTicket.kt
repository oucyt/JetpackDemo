package com.yt.jetpackdemo.persistence

import androidx.room.*
import com.yt.jetpackdemo.advance.Converters
import java.util.*

//{
//    "effectiveEndTime": 1569384000000,
//    "effectiveBeginTime": 1569291480000,
//    "roomNo": "0802",
//    "counts": -1,
//    "checkinRoomId": "eba072aff7ed4881b32dd1190df34fc6",
//    "packagesInfo": ""
//}
/**
 * description
 * 餐券表
 * @author tianyu
 * @create 2019.06.11 15:02
 * @since 1.0.0
 */
@Entity(
    tableName = "table_breakfast_ticket",/*定义表名*/
    indices = [Index(value = ["room_no"], unique = true)]/*定义索引*/
)
@TypeConverters(Converters::class)
data class BreakfastTicket(
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
) {
    constructor(
        checkinRoomId: String, counts: Int, roomNo: String

    ) : this(checkinRoomId, counts, roomNo, Date(), Date())
}