package me.sparker0i.drinkwater.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.sparker0i.drinkwater.data.db.WaterDao
import me.sparker0i.drinkwater.data.entity.Amounts
import me.sparker0i.drinkwater.data.entity.WaterLog

class WaterRepositoryImpl(
    private val waterDao: WaterDao
): WaterRepository {
    override var waterLog = MutableLiveData<WaterLog>()
    override var amounts = MutableLiveData<Amounts>()

    init {
        waterLog.observeForever{wL ->
            persistWaterLog(wL)
        }
        amounts.observeForever{amt ->
            persistAmount(amt)
        }
    }

    override fun addWaterLog(waterLog: WaterLog) {
        this@WaterRepositoryImpl.waterLog.postValue(waterLog)
    }

    override fun addAmount(amount: Amounts) {
        this@WaterRepositoryImpl.amounts.postValue(amount)
    }

    override suspend fun getWaterLogs(): LiveData<out List<WaterLog>> {
        return withContext(Dispatchers.IO) {
            return@withContext waterDao.getWaterLog()
        }
    }

    override suspend fun getAmounts(): LiveData<out List<Amounts>> {
        return withContext(Dispatchers.IO) {
            return@withContext waterDao.getAmounts()
        }
    }

    private fun persistAmount(amount: Amounts) {
        GlobalScope.launch(Dispatchers.IO) {
            waterDao.insertAmount(amount)
        }
    }

    private fun persistWaterLog(waterLog: WaterLog) {
        GlobalScope.launch(Dispatchers.IO) {
            waterDao.insertWaterLog(waterLog)
        }
    }
}