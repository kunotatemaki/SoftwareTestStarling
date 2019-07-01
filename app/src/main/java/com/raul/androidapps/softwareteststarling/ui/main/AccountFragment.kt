package com.raul.androidapps.softwareteststarling.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.databinding.AccountFragmentBinding
import com.raul.androidapps.softwareteststarling.extensions.getValueWithTwoDecimalsPrecissionInStringFormat
import com.raul.androidapps.softwareteststarling.model.Feed
import com.raul.androidapps.softwareteststarling.ui.NetworkViewModel
import com.raul.androidapps.softwareteststarling.ui.common.BaseFragment

class AccountFragment : BaseFragment() {

    private lateinit var binding: AccountFragmentBinding

    private lateinit var viewModel: AccountViewModel
    private lateinit var networkViewModel: NetworkViewModel
    private lateinit var adapter: FeedsAdapter

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
        adapter = FeedsAdapter(resourcesManager = resourcesManager, starlingBindingComponent = starlingBindingComponent)
        binding.feedContainer.feedList.adapter = adapter
        binding.resources = resourcesManager

        viewModel.accounts.observe(this.viewLifecycleOwner, Observer {
            it?.let { list ->
                //we are only reading the first account -> this app only handles one account per user
                list.firstOrNull()?.let { accountEncrypted ->
                    val identifiers = accountEncrypted.identifiers
                    binding.identifiers = identifiers.firstOrNull()?.toAccountIdentifierUnencrypted(encryption)
                    accountEncrypted.balance.firstOrNull()?.toAccountBalance()?.amount?.let { amount ->
                        val value = amount.minorUnits.toFloat() / 100
                        binding.balance =
                            "${value.getValueWithTwoDecimalsPrecissionInStringFormat()} ${amount.currency}"
                    }
                    if(accountEncrypted.feeds.isNotEmpty()) {
                        val feeds = accountEncrypted.feeds.map { it.toAccountFeedUnencrypted(encryption) }
                        adapter.updateItems(feeds)
                    }
                    binding.executePendingBindings()
                }
            }
        })
    }

}
