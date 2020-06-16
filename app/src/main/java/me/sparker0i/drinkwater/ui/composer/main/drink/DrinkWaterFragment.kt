package me.sparker0i.drinkwater.ui.composer.main.drink

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import me.sparker0i.drinkwater.R

class DrinkWaterFragment : Fragment() {

    companion object {
        fun newInstance() = DrinkWaterFragment()
    }

    private lateinit var viewModel: DrinkWaterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drink_water_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DrinkWaterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}