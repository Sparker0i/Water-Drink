package me.sparker0i.drinkwater.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.sparker0i.drinkwater.data.db.WaterDao
import me.sparker0i.drinkwater.data.entity.Amount
import me.sparker0i.drinkwater.data.entity.WaterLog

class WaterRepositoryImpl(
    private val waterDao: WaterDao
): WaterRepository {
    override var waterLog = MutableLiveData<WaterLog>()
    override var amount = MutableLiveData<Amount>()

    init {
        waterLog.observeForever{wL ->
            persistWaterLog(wL)
        }
        amount.observeForever{ amt ->
            persistAmount(amt)
        }
    }

    override fun addWaterLog(waterLog: WaterLog) {
        this@WaterRepositoryImpl.waterLog.postValue(waterLog)
    }

    override fun addAmount(amount: Amount) {
        this@WaterRepositoryImpl.amount.postValue(amount)
    }

    override suspend fun getWaterLogs(): LiveData<List<WaterLog>> {
        return withContext(Dispatchers.IO) {
            return@withContext waterDao.getWaterLog()
        }
    }

    override suspend fun getAmounts(): LiveData<List<Amount>> {
        return withContext(Dispatchers.IO) {
            return@withContext waterDao.getAmounts()
        }
    }

    private fun persistAmount(amount: Amount) {
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