package me.sparker0i.drinkwater.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.sparker0i.drinkwater.data.entity.Amount
import me.sparker0i.drinkwater.data.entity.WaterLog

@Database(
    entities = [Amount::class, WaterLog::class],
    version = 1,
    exportSchema = true
)
abstract class WaterDb: RoomDatabase() {
    abstract val waterDao: WaterDao

    companion object {
        @Volatile private var instance: WaterDb? = null

        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, WaterDb::class.java, "app.db")
                .createFromAsset("app.db")
                .build()
    }
}