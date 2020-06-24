package me.sparker0i.drinkwater

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import me.sparker0i.drinkwater.data.db.WaterDao
import me.sparker0i.drinkwater.data.db.WaterDb
import me.sparker0i.drinkwater.data.repository.WaterRepository
import me.sparker0i.drinkwater.data.repository.WaterRepositoryImpl
import me.sparker0i.drinkwater.ui.composer.main.drink.DrinkWaterViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WaterApplication: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy{
        import(androidXModule(this@WaterApplication))
        AndroidThreeTen.init(this@WaterApplication)

        bind() from singleton { WaterDb(instance()) }
        bind<WaterDao>() with singleton { instance<WaterDb>().waterDao }
        bind<WaterRepository>() with singleton { WaterRepositoryImpl(instance()) }
        bind<DrinkWaterViewModelFactory>() with provider { DrinkWaterViewModelFactory(instance()) }
    }
}