package me.sparker0i.drinkwater.ui.composer.main.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.sparker0i.drinkwater.R

class DrinkReportFragment : Fragment() {

    companion object {
        fun newInstance() = DrinkReportFragment()
    }

    private lateinit var viewModel: DrinkReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drink_report_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DrinkReportViewModel::class.java)
        // TODO: Use the ViewModel
    }

}