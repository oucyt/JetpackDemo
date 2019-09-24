package com.yt.jetpackdemo.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * description
 *
 * @author tianyu
 * @create 2019.09.24 17:57
 * @since 1.0.0
 */
@Dao
interface MealCouponDao {

    @Insert
    fun insert(coupon: MealCoupon): Completable

    @Query("SELECT * FROM table_meal_coupon WHERE room_no = :roomNo  LIMIT 1")
    fun queryCouponByRoomNo(roomNo: String): Flowable<MealCoupon>


    @Query("DELETE FROM table_meal_coupon WHERE room_no = :roomNo")
    fun deleteCouponByRoomNo(roomNo: String): Completable


    @Delete
    fun delete(coupon: MealCoupon): Completable


    @Query("DELETE FROM table_meal_coupon")
    fun deleteAll()


}