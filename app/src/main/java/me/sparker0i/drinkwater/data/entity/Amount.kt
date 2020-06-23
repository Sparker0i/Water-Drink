package me.sparker0i.drinkwater.data.entity

import android.widget.ImageView
import androidx.room.Entity
import com.afollestad.materialdialogs.bottomsheets.GridItem
import me.sparker0i.drinkwater.R
import me.sparker0i.drinkwater.utils.Utils

@Entity(tableName = "AMOUNTS", primaryKeys = ["amount", "icon"])
open class Amount(
    val amount: Int,
    val icon: String
)