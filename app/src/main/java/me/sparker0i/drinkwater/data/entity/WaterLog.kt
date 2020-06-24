package me.sparker0i.drinkwater.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime
import java.util.*

@Entity(tableName = "WATER_LOG")
data class WaterLog(
    val amount: Double,
    val createdAt: Long,
    val icon: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}