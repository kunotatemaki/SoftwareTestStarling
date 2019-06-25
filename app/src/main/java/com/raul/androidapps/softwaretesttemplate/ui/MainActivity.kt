package com.raul.androidapps.softwaretesttemplate.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.raul.androidapps.softwaretesttemplate.R
import com.raul.androidapps.softwaretesttemplate.databinding.MainActivityBinding
import com.raul.androidapps.softwaretesttemplate.ui.main.MainFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

    }

}
