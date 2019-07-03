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
    private lateinit var accountUid: String
    private lateinit var currency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val bundle = SaveFragmentArgs.fromBundle(this)
            accountUid = bundle.accountUid
            currency = bundle.currency
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
        viewModel.getPotentialSavingAsObservable().observe(this.viewLifecycleOwner, Observer {
            it?.let {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    binding.potentialSaving.text = viewModel.getSavingsFromListAsString(it, currency)
                    checkButton()
                }
            }
        })
        viewModel.getPotentialSavingsForAccount(accountUid)

        binding.saveButton.setOnClickListener {
            viewModel.getPotentialSavingAsObservable().value?.let { feeds ->
                (activity as? MainActivity)?.getViewModel()
                    ?.sendToGoal(accountUid, feeds.map { it.feedItemUid }, viewModel.getSavingsAmount(), currency)
            }
        }

        //for hiding the button
        (activity as? MainActivity)?.getViewModel()?.getNetworkCallInProgress()
            ?.observe(this.viewLifecycleOwner, Observer {
                it?.let {
                    checkButton()
                }
            })
    }

    private fun checkButton(){
        binding.saveButton.isEnabled = (activity as? MainActivity)?.getViewModel()?.getNetworkCallInProgress()?.value != true && viewModel.getSavingsAmount() > 0
    }


}
