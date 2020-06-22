package me.sparker0i.drinkwater.ui.composer.main.drink

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BasicGridItem
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drink_water_fragment.*
import kotlinx.coroutines.launch
import me.sparker0i.drinkwater.R
import me.sparker0i.drinkwater.data.entity.WaterLog
import me.sparker0i.drinkwater.ui.base.ScopedFragment
import me.sparker0i.drinkwater.ui.composer.main.drink.adapter.WaterLogAdapter
import me.sparker0i.drinkwater.utils.Utils
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
        val amounts = viewModel.amounts.await()
        var list = ArrayList<BasicGridItem>()

        amounts.observe(this, Observer { x ->
            list = ArrayList<BasicGridItem>()
            x.forEach { item ->
                list.add(BasicGridItem(Utils.getResId(item.icon, R.drawable::class.java), item.amount.toString()))
            }
        })

        add_new_button.setOnClickListener{
            Log.i("FAB", "Yes")
            MaterialDialog(context!!, BottomSheet()).show {
                lifecycleOwner(this@DrinkWaterFragment)
                gridItems(list) {_, index, item ->
                    Toast.makeText(context, "Selected item ${item.title} at index $index", Toast.LENGTH_SHORT).show()
                }
            }
        }

        water_log_recycler_view.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL) //GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        waterLogs.observe(this, Observer{wLs ->
            water_log_recycler_view.adapter = WaterLogAdapter(context, waterLogs as LiveData<List<WaterLog>>)
        })
    }
}