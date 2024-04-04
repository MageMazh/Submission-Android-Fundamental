package com.d121211069.submissionandroidfundamental.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.d121211069.submissionandroidfundamental.R
import com.d121211069.submissionandroidfundamental.databinding.ActivitySettingBinding
import com.d121211069.submissionandroidfundamental.datastore.SettingPreferences
import com.d121211069.submissionandroidfundamental.datastore.dataStore
import com.d121211069.submissionandroidfundamental.factory.SettingViewModelFactory
import com.d121211069.submissionandroidfundamental.viewModel.SettingViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
                binding.switchTheme.text = "dark mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
                binding.switchTheme.text = "light mode"
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            onBackPressed()
        }
    }
}