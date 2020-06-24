package me.sparker0i.drinkwater.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.sparker0i.drinkwater.data.entity.Amount
import me.sparker0i.drinkwater.data.entity.WaterLog
import org.threeten.bp.OffsetDateTime

@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertWaterLog(waterLog: WaterLog)
    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertAmount(amount: Amount)

    @Query("SELECT * FROM WATER_LOG") fun getWaterLog(): LiveData<List<WaterLog>>
    @Query("SELECT * FROM AMOUNTS") fun getAmounts(): LiveData<List<Amount>>
    @Query("SELECT * FROM WATER_LOG WHERE createdAt BETWEEN :start AND :end") fun getWaterLog(start: Long, end: Long): LiveData<List<WaterLog>>
}