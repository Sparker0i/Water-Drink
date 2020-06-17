package me.sparker0i.drinkwater.data.repository

import androidx.lifecycle.LiveData
import me.sparker0i.drinkwater.data.entity.WaterLog

interface WaterRepository {
    suspend fun getWaterLogs(): LiveData<out WaterLog>
}