package com.example.githubuserapp.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.databinding.ItemUserBinding
import com.example.githubuserapp.models.UserGithub

class GithubAdapter(
    private val listUserGithub: ArrayList<UserGithub>,
    private val clickListener: (String?, View) -> Unit
) : RecyclerView.Adapter<GithubAdapter.CardViewHolder>() {
    inner class CardViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: UserGithub, click: (String?, View) -> Unit) {
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

    fun setData(items: List<UserGithub>) {
        listUserGithub.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}