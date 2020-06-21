package me.sparker0i.drinkwater.ui.composer.main.drink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.drink_water_fragment.*
import kotlinx.coroutines.launch
import me.sparker0i.drinkwater.R
import me.sparker0i.drinkwater.data.entity.WaterLog
import me.sparker0i.drinkwater.ui.base.ScopedFragment
import me.sparker0i.drinkwater.ui.composer.main.drink.adapter.WaterLogAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DrinkWaterFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: DrinkWaterViewModelFactory by instance<DrinkWaterViewModelFactory>()
    private lateinit var viewModel: DrinkWaterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drink_water_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DrinkWaterViewModel::class.java)

        launch {
            execute()
        }
    }

    private suspend fun execute() {
        val waterLogs = viewModel.waterLogs.await()
        water_log_recycler_view.layoutManager = LinearLayoutManager(context)
        waterLogs.observe(this, Observer{wLs ->
            water_log_recycler_view.adapter = WaterLogAdapter(context, waterLogs as LiveData<List<WaterLog>>)
        })
    }
}