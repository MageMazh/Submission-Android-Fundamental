package com.d121211069.submissionandroidfundamental.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.d121211069.submissionandroidfundamental.R
import com.d121211069.submissionandroidfundamental.datastore.SettingPreferences
import com.d121211069.submissionandroidfundamental.datastore.dataStore
import com.d121211069.submissionandroidfundamental.factory.SettingViewModelFactory
import com.d121211069.submissionandroidfundamental.viewModel.SettingViewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler().postDelayed({
            val i = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )

            startActivity(i)
            finish()
        }, 5000)
    }
}