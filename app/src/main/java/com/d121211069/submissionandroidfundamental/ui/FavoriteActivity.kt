package com.d121211069.submissionandroidfundamental.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.d121211069.submissionandroidfundamental.R
import com.d121211069.submissionandroidfundamental.adapter.UsersAdapter
import com.d121211069.submissionandroidfundamental.databinding.ActivityFavoriteBinding
import com.d121211069.submissionandroidfundamental.factory.ViewModelFactory
import com.d121211069.submissionandroidfundamental.viewModel.FavoriteViewModel
import com.d121211069.submissionandroidfundamental.util.Result

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            onBackPressed()
        }

        val adapter = UsersAdapter()
        binding.rvUser.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        favoriteViewModel.getAllFavoriteUsers().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        if (result.data.isEmpty()) binding.favText.text = "You don't have a list of user favorites yet"
                        else binding.favText.text = ""

                        binding?.progressBar?.visibility = View.GONE
                        val userData = result.data
                        adapter.submitList(userData)
                    }

                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            this, "Terjadi kesalahan" + result.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}