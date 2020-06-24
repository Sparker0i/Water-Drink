package me.sparker0i.drinkwater.ui.composer.main.drink

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import kotlinx.android.synthetic.main.drink_water_fragment.*
import kotlinx.coroutines.launch
import me.sparker0i.drinkwater.R
import me.sparker0i.drinkwater.data.entity.AdaptedAmount
import me.sparker0i.drinkwater.data.entity.WaterLog
import me.sparker0i.drinkwater.ui.base.ScopedFragment
import me.sparker0i.drinkwater.ui.common.FabExtendingOnScrollListener
import me.sparker0i.drinkwater.ui.composer.main.drink.adapter.WaterLogAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime
import java.util.*

class DrinkWaterFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: DrinkWaterViewModelFactory by instance<DrinkWaterViewModelFactory>()
    private lateinit var viewModel: DrinkWaterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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
        val waterLogs = viewModel.waterLogs(Date()).await()
        val amounts = viewModel.amounts.await()

        var amountDialog = MaterialDialog(context!!, BottomSheet(LayoutMode.WRAP_CONTENT))
        amountDialog = amountDialog.lifecycleOwner(this@DrinkWaterFragment)
        amountDialog.setTitle(R.string.add_water_log)

        amounts.observe(this, Observer { y ->
            amountDialog = amountDialog.gridItems(y.map{x -> AdaptedAmount(x.amount, x.icon)}) { _, _, item ->
                viewModel.addWaterLog(WaterLog(item.amount.toDouble(), System.currentTimeMillis(), item.icon))
            }
            amountDialog = amountDialog.title(R.string.add_water_log)
        })

        water_log_recycler_view.addOnScrollListener(FabExtendingOnScrollListener(add_new_button))

        add_new_button.setOnClickListener{
            amountDialog.show()
        }

        waterLogs.observe(this, Observer{wLs ->
            Log.i("Dated", wLs!!.size.toString())
            water_log_recycler_view.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            water_log_recycler_view.adapter = WaterLogAdapter(context, waterLogs)
        })
    }


}