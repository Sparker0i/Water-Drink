package me.sparker0i.drinkwater.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.sparker0i.drinkwater.data.entity.Amount
import me.sparker0i.drinkwater.data.entity.WaterLog

interface WaterRepository {
    var waterLog: MutableLiveData<WaterLog>
    var amount: MutableLiveData<Amount>

    suspend fun getWaterLogs(): LiveData<out List<WaterLog>>
    suspend fun getAmounts(): LiveData<out List<Amount>>

    fun addWaterLog(waterLog: WaterLog)
    fun addAmount(amount: Amount)
}