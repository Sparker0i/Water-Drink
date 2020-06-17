package me.sparker0i.drinkwater.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.sparker0i.drinkwater.data.entity.WaterLog

@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun upsert(waterLog: WaterLog)

    @Query("SELECT * FROM WATER_LOG") fun getWaterLog(): LiveData<WaterLog>
}