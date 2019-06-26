package com.raul.androidapps.softwareteststarling.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.databinding.AccountFragmentBinding
import com.raul.androidapps.softwareteststarling.ui.NetworkViewModel
import com.raul.androidapps.softwareteststarling.ui.common.BaseFragment

class AccountFragment : BaseFragment() {

    private lateinit var binding: AccountFragmentBinding

    private lateinit var viewModel: AccountViewModel
    private lateinit var networkViewModel: NetworkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.account_fragment,
            container,
            false,
            starlingBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)
        networkViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NetworkViewModel::class.java)
        viewModel.accounts.observe({ this.lifecycle }) {
            it?.let { list ->
                val unencryptedList =
                    list.map { encrypted -> encrypted.account.toAccountUnencrypted(encryption) }
                unencryptedList.firstOrNull()?.let { account ->
                    //we are only reading the first account -> this app only handles one account per user
                    networkViewModel.getAccountIdentifiersAsync(account.accountUid)
                }
            }
        }
    }

}
