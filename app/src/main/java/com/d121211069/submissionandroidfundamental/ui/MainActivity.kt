package com.d121211069.submissionandroidfundamental.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.d121211069.submissionandroidfundamental.R
import com.d121211069.submissionandroidfundamental.adapter.UsersAdapter
import com.d121211069.submissionandroidfundamental.data.remote.response.UserItem
import com.d121211069.submissionandroidfundamental.databinding.ActivityMainBinding
import com.d121211069.submissionandroidfundamental.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel.listUser.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.searchUsers(searchView.text.toString())
                    false
                }
        }

        mainViewModel.snackbarText.observe(this, {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu2 -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun setUserData(listUsers: List<UserItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(listUsers)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}