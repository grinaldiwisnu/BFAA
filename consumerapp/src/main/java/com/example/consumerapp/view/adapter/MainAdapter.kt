package com.example.consumerapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.consumerapp.databinding.ItemUserBinding
import com.example.consumerapp.models.UserModel

class MainAdapter(private val userModels: ArrayList<UserModel>, private val clickListener: (UserModel) -> Unit): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    inner class MainViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userModel: UserModel, click: (UserModel) -> Unit) {
            binding.data = userModel
            binding.root.transitionName = userModel.login
            binding.root.setOnClickListener { click(userModel) }
        }
    }

    fun setData(items: List<UserModel>){
        userModels.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = holder.bind(userModels[position], clickListener)

    override fun getItemCount(): Int = userModels.size
}