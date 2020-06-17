package me.sparker0i.drinkwater.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.sparker0i.drinkwater.data.db.WaterDao
import me.sparker0i.drinkwater.data.entity.WaterLog

class WaterRepositoryImpl(
    private val waterDao: WaterDao
): WaterRepository {

    override suspend fun getWaterLogs(): LiveData<out WaterLog> {
        return withContext(Dispatchers.IO) {
            return@withContext waterDao.getWaterLog()
        }
    }

    private fun persistWaterLog(data: WaterLog) {
        GlobalScope.launch(Dispatchers.IO) {
            waterDao.upsert(data)
        }
    }
}