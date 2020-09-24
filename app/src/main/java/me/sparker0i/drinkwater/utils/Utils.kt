package me.sparker0i.drinkwater.utils

import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    val sdf = SimpleDateFormat("HH:mm")

    fun getResId(resName: String?, c: Class<*>): Int {
        return try {
            val idField: Field = c.getDeclaredField(resName!!)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    fun formatTimestampToTime(timestamp: Long): String {
        return sdf.format(Date(timestamp))
    }
}