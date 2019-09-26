package com.yt.jetpackdemo.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * description
 *
 * @author tianyu
 * @create 2019.09.24 17:57
 * @since 1.0.0
 */
@Dao
interface BreakfastTicketDao {

    @Insert
    fun insert(breakfastTicket: BreakfastTicket): Completable

    @Query("SELECT * FROM table_breakfast_ticket WHERE room_no = :roomNo  LIMIT 1")
    fun queryTicketByRoomNo(roomNo: String):  Maybe<BreakfastTicket>


    @Query("DELETE FROM table_breakfast_ticket WHERE room_no = :roomNo")
    fun deleteTicketByRoomNo(roomNo: String): Completable


    @Delete
    fun delete(coupon: BreakfastTicket): Completable


    @Query("DELETE FROM table_breakfast_ticket")
    fun deleteAll(): Completable


}