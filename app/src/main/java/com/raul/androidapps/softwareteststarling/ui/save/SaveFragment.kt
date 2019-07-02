package com.raul.androidapps.softwareteststarling.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.databinding.SaveFragmentBinding
import com.raul.androidapps.softwareteststarling.ui.MainActivity
import com.raul.androidapps.softwareteststarling.ui.common.BaseFragment

class SaveFragment : BaseFragment() {


    private lateinit var viewModel: SaveViewModel
    private lateinit var binding: SaveFragmentBinding
    private var accountUid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val bundle = SaveFragmentArgs.fromBundle(this)
            accountUid = bundle.accountUid
        }

    }

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
        viewModel.getPotentialSaving().observe(this.viewLifecycleOwner, Observer {
            it?.let {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    binding.potentialSaving.text = viewModel.getSavingsFromList(it).toString()
                }
            }
        })
        viewModel.getPotentialSavingsForAccount(accountUid)

        binding.saveButton.setOnClickListener {
            viewModel.markFeedsAsSaved()
        }
    }


}
