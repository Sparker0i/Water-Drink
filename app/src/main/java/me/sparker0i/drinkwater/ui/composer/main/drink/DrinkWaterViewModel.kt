package me.sparker0i.drinkwater.ui.composer.main.drink

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

    fun waterLogs(date: Date) = deferred {
        waterRepository.getWaterLogs(
            date.apply { hours = 0; minutes = 0; seconds = 0; }.time,
            date.apply { hours = 23; minutes = 59; seconds = 59 }.time
        )
    }

    fun addAmount(amount: Amount) {
        waterRepository.addAmount(amount)
    }

    fun addWaterLog(waterLog: WaterLog) {
        waterRepository.addWaterLog(waterLog)
    }
}