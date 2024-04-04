package com.d121211069.submissionandroidfundamental.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d121211069.submissionandroidfundamental.data.remote.response.UserFollowResponseItem
import com.d121211069.submissionandroidfundamental.databinding.ItemUserBinding
import com.d121211069.submissionandroidfundamental.ui.UserDetailActivity

class FollowAdapter : ListAdapter<UserFollowResponseItem, FollowAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserFollowResponseItem) {
            binding.userName.text = item.login
            Glide.with(itemView.context).load(item.avatarUrl).into(binding.userImage)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, UserDetailActivity::class.java)
                intent.putExtra(DATA, item.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val DATA = "data_detail"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserFollowResponseItem, newItem: UserFollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowResponseItem, newItem: UserFollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
