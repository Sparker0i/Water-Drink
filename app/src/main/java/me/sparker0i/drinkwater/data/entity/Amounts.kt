package me.sparker0i.drinkwater.data.entity

import androidx.annotation.NonNull
import androidx.room.Entity

@Entity(tableName = "AMOUNTS", primaryKeys = ["amount", "icon"])
data class Amounts(
    @NonNull val amount: Int,
    @NonNull val icon: String
)