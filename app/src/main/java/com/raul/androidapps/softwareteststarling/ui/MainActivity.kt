package com.raul.androidapps.softwareteststarling.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.ui.common.StarlingViewModelFactory
import com.raul.androidapps.softwareteststarling.databinding.MainActivityBinding
import com.raul.androidapps.softwareteststarling.security.Encryption
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var networkViewModel: NetworkViewModel

    @Inject
    protected lateinit var viewModelFactory: StarlingViewModelFactory

    @Inject
    protected lateinit var encryption: Encryption

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        networkViewModel = ViewModelProviders.of(this,viewModelFactory ).get(NetworkViewModel::class.java)

        networkViewModel.getAccountsAsObservable().observe({this.lifecycle}){
            it?.let { list ->
                list.firstOrNull()?.let { account ->
                    //we are only reading the first account -> this app only handles one account per user
                    networkViewModel.getAccountIdentifiersAsync(account.accountUid)
                    networkViewModel.getAccountBalanceAsync(account.accountUid)
                    networkViewModel.getAccountFeedsAsync(account.accountUid, account.defaultCategory)
                }
            }
        }
        networkViewModel.getAccountsAsync()
    }

    fun setBackArrow(visible: Boolean){
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
