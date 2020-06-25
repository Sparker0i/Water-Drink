package me.sparker0i.drinkwater.ui.composer.main.drink

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import androidx.annotation.AttrRes
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.customListAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.drink_water_fragment.*
import kotlinx.coroutines.launch
import me.sparker0i.drinkwater.R
import me.sparker0i.drinkwater.data.entity.Amount
import me.sparker0i.drinkwater.data.entity.WaterLog
import me.sparker0i.drinkwater.ui.base.ScopedFragment
import me.sparker0i.drinkwater.ui.common.FabExtendingOnScrollListener
import me.sparker0i.drinkwater.ui.composer.main.drink.adapter.AmountAdapter
import me.sparker0i.drinkwater.ui.composer.main.drink.adapter.ClickListener
import me.sparker0i.drinkwater.ui.composer.main.drink.adapter.WaterLogAdapter
import me.sparker0i.drinkwater.ui.composer.main.drink.adapter.onItemClick
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class DrinkWaterFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: DrinkWaterViewModelFactory by instance<DrinkWaterViewModelFactory>()
    private lateinit var viewModel: DrinkWaterViewModel
    private lateinit var picker: MaterialDatePicker<*>

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
        initializePicker()

        launch {
            execute()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_drink_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.pick_date_range -> {
                picker.show(parentFragmentManager, picker.toString())
            }
        }
        return true
    }

    private fun initializePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        picker = MaterialDatePicker.Builder.dateRangePicker()
            .setSelection(Pair(today, today))
            .setTheme(resolveOrThrow(requireContext(), R.attr.materialCalendarTheme))
            .setTitleText(R.string.pick_date_range)
            .build()
    }

    private fun resolveOrThrow(context: Context, @AttrRes attributeResId: Int): Int {
        val typedValue = TypedValue()
        if (context.theme.resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue.data
        }
        throw IllegalArgumentException(
            context.resources.getResourceName(attributeResId)
        )
    }

    private suspend fun execute() {
        val waterLogs = viewModel.waterLogs(Date()).await()
        val amounts = viewModel.amounts.await()

        var amountDialog = MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT))
            .apply {
                lifecycleOwner(this@DrinkWaterFragment)
                setTitle(R.string.add_water_log)
            }

        amounts.observe(this, Observer { y ->
            amountDialog = amountDialog.apply {
                customListAdapter(
                    AmountAdapter(context, amounts).setOnItemClickListener(object : ClickListener<Amount> {
                        override fun onItemClick(item: Amount) {
                            Log.i("Click", "X")
                            viewModel.addWaterLog(WaterLog(item.amount.toDouble(), System.currentTimeMillis(), item.icon))
                        }
                    }),
                    StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
                )
                view.contentLayout.recyclerView?.apply {
                    onItemClick{ _, position, _ ->
                        val item = y[position]
                        viewModel.addWaterLog(WaterLog(item.amount.toDouble(), System.currentTimeMillis(), item.icon))
                        amountDialog.dismiss()
                    }
                    scrollToPosition(y.size - 1)
                }
                title(R.string.add_water_log)
            }
        })

        water_log_recycler_view.addOnScrollListener(FabExtendingOnScrollListener(add_new_button))

        add_new_button.setOnClickListener{
            amountDialog.show()
        }

        waterLogs.observe(this, Observer{wLs ->
            Log.i("Dated", wLs!!.size.toString())
            water_log_recycler_view.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            water_log_recycler_view.adapter = WaterLogAdapter(context, waterLogs)
            water_log_recycler_view.onItemClick{recyclerView, position, v ->
                Log.i("Click", "B")
            }
        })
    }


}