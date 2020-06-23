package me.sparker0i.drinkwater.data.entity

import android.widget.ImageView
import com.afollestad.materialdialogs.bottomsheets.GridItem
import me.sparker0i.drinkwater.R
import me.sparker0i.drinkwater.utils.Utils

class AdaptedAmount(
    amount: Int,
    icon: String
): Amount(amount, icon), GridItem {
    override val title: String
        get() = amount.toString()

    override fun populateIcon(imageView: ImageView) {
        imageView.setImageResource(Utils.getResId(icon, R.drawable::class.java))
    }
}