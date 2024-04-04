package com.d121211069.submissionandroidfundamental.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.d121211069.submissionandroidfundamental.adapter.FollowAdapter
import com.d121211069.submissionandroidfundamental.data.remote.response.UserFollowResponseItem
import com.d121211069.submissionandroidfundamental.databinding.FragmentFollowBinding
import com.d121211069.submissionandroidfundamental.viewModel.FollowViewModel
import com.google.android.material.snackbar.Snackbar

class FollowFragment : Fragment() {

    private lateinit var followViewModel: FollowViewModel
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    var position = 0
    var username = ""

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_NAME).toString()
        }

        followViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFollowingReview?.layoutManager = layoutManager

        if (position == 1) {
            if (followViewModel.username.value == null) {
                followViewModel.fetchUserFollower(username)
                followViewModel.setUsername(username)
            }
            followViewModel.fetchUserFollower(username)

            followViewModel.listUserFollower.observe(viewLifecycleOwner) { user ->
                setUserFollowerData(user)
            }
            followViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            if (followViewModel.username.value == null) {
                followViewModel.fetchUserFollowing(username)
                followViewModel.setUsername(username)
            }

            followViewModel.listUserFollowing.observe(viewLifecycleOwner) { user ->
                setUserFollowingData(user)
            }
            followViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followViewModel.snackbarText.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    requireActivity().window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setUserFollowerData(listUsers: List<UserFollowResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(listUsers)
        binding?.rvFollowingReview?.adapter = adapter
    }

    private fun setUserFollowingData(listUsers: List<UserFollowResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(listUsers)
        binding?.rvFollowingReview?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}