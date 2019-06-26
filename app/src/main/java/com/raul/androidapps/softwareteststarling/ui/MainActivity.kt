package com.raul.androidapps.softwareteststarling.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.raul.androidapps.softwareteststarling.R
import com.raul.androidapps.softwareteststarling.ui.common.StarlingViewModelFactory
import com.raul.androidapps.softwareteststarling.databinding.MainActivityBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var networkViewModel: NetworkViewModel

    @Inject
    protected lateinit var viewModelFactory: StarlingViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        networkViewModel = ViewModelProviders.of(this,viewModelFactory ).get(NetworkViewModel::class.java)
        networkViewModel.getAccountsAsync()
    }

}
