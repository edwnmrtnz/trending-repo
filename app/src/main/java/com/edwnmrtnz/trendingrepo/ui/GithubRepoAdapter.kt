package com.edwnmrtnz.trendingrepo.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edwnmrtnz.trendingrepo.core.domain.GithubRepo
import com.edwnmrtnz.trendingrepo.databinding.ItemGithubrepoBinding
import com.squareup.picasso.Picasso

class GithubRepoAdapter : ListAdapter<GithubRepo, GithubRepoAdapter.ViewHolder>(DiffUtilCallback) {

    private val placeholder: GradientDrawable = GradientDrawable()
    init {
        placeholder.shape = GradientDrawable.RECTANGLE
        placeholder.setColor(Color.GRAY)
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<GithubRepo>() {
        override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(
        val binding: ItemGithubrepoBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGithubrepoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.tvUserName.text = item.username
        holder.binding.tvRepoName.text = item.repoName
        holder.binding.tvDescription.text = item.description

        if (item.hasDescription()) {
            holder.binding.tvDescription.visibility = View.VISIBLE
            holder.binding.tvDescription.text = item.description ?: ""
        } else {
            holder.binding.tvDescription.visibility = View.GONE
        }

        if (item.hasPrimaryLanguage()) {
            holder.binding.tvPrimaryLanguage.visibility = View.VISIBLE
            holder.binding.tvPrimaryLanguage.text = item.primaryLanguage
        } else {
            holder.binding.tvPrimaryLanguage.visibility = View.GONE
        }

        if (item.starCount > 0) {
            holder.binding.tvStarCount.visibility = View.VISIBLE
            holder.binding.tvStarCount.text = item.starCount.toString()
        } else {
            holder.binding.tvStarCount.visibility = View.GONE
        }

        Picasso
            .get()
            .load(item.avatar)
            .placeholder(placeholder)
            .into(holder.binding.ivAvatar)
    }
}
