package com.d121211069.submissionandroidfundamental.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.d121211069.submissionandroidfundamental.R
import com.d121211069.submissionandroidfundamental.adapter.SectionsPagerAdapter
import com.d121211069.submissionandroidfundamental.data.remote.response.UserDetailResponse
import com.d121211069.submissionandroidfundamental.databinding.ActivityUserDetailBinding
import com.d121211069.submissionandroidfundamental.factory.ViewModelFactory
import com.d121211069.submissionandroidfundamental.viewModel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra(DATA).toString()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (detailViewModel.username.value == null) {
            detailViewModel.fetchUserDetail(username)
            detailViewModel.setUsername(username)
        }

        detailViewModel.user.observe(this) { user ->
            setUserData(user)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.usernameFrag = username.toString()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            onBackPressed()
        }

        detailViewModel.snackbarText.observe(this, {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        detailViewModel.fetchFavoriteUser(username).observe(this) { user ->
            if (user != null) {
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
                binding.fabFav?.setOnClickListener {
                    detailViewModel.delete()
                    Toast.makeText(
                        this, "Successfully removed favorite", Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.fabFav?.setOnClickListener {
                    detailViewModel.insert()
                    Toast.makeText(
                        this, "Successfully added favorite", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setUserData(data: UserDetailResponse) {
        binding.username.text = data.login
        binding.name.text = data.name
        binding.numberOfFollowers.text = "Followers: ${data.followers.toString()}"
        binding.numberOfFollowing.text = "Following: ${data.following.toString()}"
        Glide.with(this)
            .load(data.avatarUrl)
            .into(binding.userDetailImage)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )

        private const val DATA = "data_detail"
    }
}