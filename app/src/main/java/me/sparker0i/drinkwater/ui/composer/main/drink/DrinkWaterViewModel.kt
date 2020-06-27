package me.sparker0i.drinkwater.ui.composer.main.drink

import android.util.Log
import androidx.lifecycle.ViewModel
import me.sparker0i.drinkwater.data.entity.Amount
import me.sparker0i.drinkwater.data.entity.WaterLog
import me.sparker0i.drinkwater.data.repository.WaterRepository
import me.sparker0i.drinkwater.internal.*
import java.util.*

class DrinkWaterViewModel(
    private val waterRepository: WaterRepository
) : ViewModel() {
    val amounts by lazyDeferred {
        waterRepository.getAmounts()
    }

    fun waterLogs() = deferred {
        waterRepository.getWaterLogs()
    }

    fun waterLogs(start: Long, end: Long) = deferred {
        Log.i("IN", "Hello")
        waterRepository.getWaterLogs(
            Date(start).apply { hours = 0; minutes = 0; seconds = 0; }.time,
            Date(end).apply { hours = 23; minutes = 59; seconds = 59 }.time
        )
    }

    fun addAmount(amount: Amount) {
        waterRepository.addAmount(amount)
    }

    fun addWaterLog(waterLog: WaterLog) {
        waterRepository.addWaterLog(waterLog)
    }
}