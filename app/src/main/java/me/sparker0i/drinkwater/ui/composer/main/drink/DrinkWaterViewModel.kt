package me.sparker0i.drinkwater.ui.composer.main.drink

import androidx.lifecycle.ViewModel
import me.sparker0i.drinkwater.data.entity.Amount
import me.sparker0i.drinkwater.data.entity.WaterLog
import me.sparker0i.drinkwater.data.repository.WaterRepository
import me.sparker0i.drinkwater.internal.lazyDeferred

class DrinkWaterViewModel(
    private val waterRepository: WaterRepository
) : ViewModel() {
    val waterLogs by lazyDeferred {
        waterRepository.getWaterLogs()
    }

    val amounts by lazyDeferred {
        waterRepository.getAmounts()
    }

    fun addAmount(amount: Amount) {
        waterRepository.addAmount(amount)
    }

    fun addWaterLog(waterLog: WaterLog) {
        waterRepository.addWaterLog(waterLog)
    }
}