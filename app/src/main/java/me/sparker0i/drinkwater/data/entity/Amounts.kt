package me.sparker0i.drinkwater.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AMOUNTS")
data class Amounts(
    val icon: String,
    @PrimaryKey val amount: Double
)