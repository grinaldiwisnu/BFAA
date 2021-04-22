package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.data.model.SearchResponse
import com.example.githubuserapp.databinding.ItemUserBinding

class GithubAdapter(
    private val listUserGithub: ArrayList<SearchResponse>,
    private val clickListener: (String, View) -> Unit
) : RecyclerView.Adapter<GithubAdapter.CardViewHolder>() {
    inner class CardViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: SearchResponse, click: (String, View) -> Unit) {
            binding.data = githubUser
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                binding.root.transitionName = githubUser.login
            }
            binding.root.setOnClickListener { click(githubUser.login, binding.root) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) =
        holder.bind(listUserGithub[position], clickListener)

    override fun getItemCount(): Int {
        return listUserGithub.size
    }

    fun setData(items: List<SearchResponse>) {
        listUserGithub.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}