package com.raul.androidapps.softwareteststarling.ui.save

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.databinding.MainActivityBinding
import com.raul.androidapps.softwareteststarling.databinding.SaveFragmentBinding
import com.raul.androidapps.softwareteststarling.di.interfaces.CustomScopes
import com.raul.androidapps.softwareteststarling.ui.MainActivity
import com.raul.androidapps.softwareteststarling.ui.common.BaseFragment

class SaveFragment : BaseFragment(){

    companion object {
        fun newInstance() = SaveFragment()
    }

    private lateinit var viewModel: SaveViewModel
    private lateinit var binding: SaveFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.save_fragment, container, false, starlingBindingComponent)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SaveViewModel::class.java)
        (activity as? MainActivity)?.setBackArrow(true)
    }


}
