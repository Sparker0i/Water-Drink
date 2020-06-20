package me.sparker0i.drinkwater.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WATER_LOG")
data class WaterLog(
    val amount: Double,
    val timestamp: Long,
    val icon: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}