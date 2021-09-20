package com.bootcamp.apprepo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.apprepo.data.model.Owner
import com.bootcamp.apprepo.data.model.Repo
import com.bootcamp.apprepo.databinding.ItemRepoBinding
import com.bumptech.glide.Glide

class RepoListAdapter
    : ListAdapter<Repo, RepoListAdapter.ViewHolder>(DiffCallback()) {

    var onAvatarClickListener: (owner: Owner) -> Unit = {}
    var onRepoClickListener: (repo: Repo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemRepoBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Repo){
            binding.tvRepoName.text = item.name
            binding.tvRepoDescription.text = item.description
            binding.tvRepoLanguage.text = item.language
            binding.chipStar.text = item.stargazersCount.toString()

            Glide.with(binding.root.context)
                .load(item.owner.avatarURL).into(binding.ivOwner)

            binding.tvRepoName.setOnClickListener { onRepoClickListener(item) }
            binding.ivOwner.setOnClickListener { onAvatarClickListener(item.owner) }
        }
    }
}

class DiffCallback: DiffUtil.ItemCallback<Repo>(){
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

}