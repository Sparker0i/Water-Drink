package me.sparker0i.drinkwater.ui.composer.main.drink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.sparker0i.drinkwater.data.repository.WaterRepository

class DrinkWaterViewModelFactory(
    private val waterRepository: WaterRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinkWaterViewModel(waterRepository) as T
    }
}