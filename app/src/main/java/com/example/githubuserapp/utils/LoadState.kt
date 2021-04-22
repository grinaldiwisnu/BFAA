package com.example.githubuserapp.utils

import android.view.View
import com.example.githubuserapp.databinding.FragmentFollowBinding
import com.example.githubuserapp.databinding.FragmentHomeBinding

class ShowState(private val stateId: Int) {

    fun loading(homeBinding: FragmentHomeBinding?, followBinding: FragmentFollowBinding?) {
        when (stateId) {
            1 -> {
                homeBinding?.emptyText?.visibility = View.GONE
                homeBinding?.progressBar?.visibility = View.VISIBLE
                homeBinding?.recyclerview?.visibility = View.GONE
            }
            2 -> {
                followBinding?.emptyText?.visibility = View.GONE
                followBinding?.progress?.visibility = View.VISIBLE
                followBinding?.followRecyclerView?.visibility = View.GONE
            }
        }
    }

    fun success(homeBinding: FragmentHomeBinding?, followBinding: FragmentFollowBinding?) {
        when (stateId) {
            1 -> {
                homeBinding?.emptyText?.visibility = View.GONE
                homeBinding?.progressBar?.visibility = View.GONE
                homeBinding?.recyclerview?.visibility = View.VISIBLE
            }

            2 -> {
                followBinding?.emptyText?.visibility = View.GONE
                followBinding?.progress?.visibility = View.GONE
                followBinding?.followRecyclerView?.visibility = View.VISIBLE
            }
        }
    }

    fun error(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        message: String?
    ) {
        when (stateId) {
            1 -> {
                homeBinding?.emptyText?.visibility = View.VISIBLE
                homeBinding?.emptyText?.text = message ?: "User tidak ditemukan"
                homeBinding?.progressBar?.visibility = View.GONE
                homeBinding?.recyclerview?.visibility = View.GONE
            }
            2 -> {
                followBinding?.emptyText?.visibility = View.VISIBLE
                followBinding?.emptyText?.text = message ?: "User tidak ditemukan"
                followBinding?.progress?.visibility = View.GONE
                followBinding?.followRecyclerView?.visibility = View.GONE
            }
        }
    }
}