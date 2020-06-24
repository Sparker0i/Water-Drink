package me.sparker0i.drinkwater.ui.composer.main.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.sparker0i.drinkwater.R

class DrinkLogFragment : Fragment() {

    companion object {
        fun newInstance() = DrinkLogFragment()
    }

    private lateinit var viewModel: DrinkLogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drink_log_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DrinkLogViewModel::class.java)
        // TODO: Use the ViewModel
    }

}