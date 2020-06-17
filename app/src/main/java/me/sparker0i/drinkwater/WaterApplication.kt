package me.sparker0i.drinkwater

import android.app.Application
import me.sparker0i.drinkwater.data.db.WaterDb
import me.sparker0i.drinkwater.data.repository.WaterRepository
import me.sparker0i.drinkwater.data.repository.WaterRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class WaterApplication: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy{
        import(androidXModule(this@WaterApplication))

        bind() from singleton { WaterDb(instance()) }
        bind() from singleton { instance<WaterDb>().waterDao() }
        bind<WaterRepository>() with singleton { WaterRepositoryImpl(instance()) }
    }
}