package com.d121211069.submissionandroidfundamental.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.d121211069.submissionandroidfundamental.ui.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var usernameFrag: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowFragment.ARG_NAME, usernameFrag)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}