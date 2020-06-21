package me.sparker0i.drinkwater.ui.composer.main.drink.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_water_log.view.*
import me.sparker0i.drinkwater.R
import me.sparker0i.drinkwater.data.entity.WaterLog
import me.sparker0i.drinkwater.utils.Utils

class WaterLogAdapter(
    private val context: Context?,
    var waterLogs: LiveData<List<WaterLog>>
): RecyclerView.Adapter<WaterLogAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_water_log, parent, false))
    }

    override fun getItemCount(): Int {
        return waterLogs.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(waterLogs.value!![position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(waterLog: WaterLog) {
            itemView.water_amount.text = waterLog.amount.toString()
            Glide.with(itemView).load(Utils.getResId(waterLog.icon, R.drawable::class.java)).into(itemView.water_container)
        }
    }
}