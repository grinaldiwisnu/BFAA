package com.example.githubuserapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentFollowBinding
import com.example.githubuserapp.utils.ShowState
import com.example.githubuserapp.utils.State
import com.example.githubuserapp.utils.TypeView
import com.example.githubuserapp.viewmodel.FollowViewModel
import com.example.githubuserapp.views.adapter.GithubAdapter

class FollowFragment : Fragment(), ShowState {
    private lateinit var followBinding: FragmentFollowBinding
    private lateinit var githubAdapter: GithubAdapter
    private lateinit var followViewModel: FollowViewModel
    private lateinit var username: String
    private var type: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME).toString()
            type = it.getString(TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        followBinding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return followBinding.root
    }

    companion object {
        fun newInstance(username: String, type: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                    putString(TYPE, type)
                }
            }

        private const val TYPE = "type"
        private const val USERNAME = "username"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        githubAdapter = GithubAdapter(arrayListOf()) { user, _ ->
            Toast.makeText(context, user, Toast.LENGTH_SHORT).show()
        }

        followBinding.followRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = githubAdapter
        }

        followViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        when (type) {
            resources.getString(R.string.following) -> followViewModel.setFollow(
                username,
                TypeView.FOLLOWING
            )
            resources.getString(R.string.followers) -> followViewModel.setFollow(
                username,
                TypeView.FOLLOWER
            )
            else -> followError(followBinding, "User tidak ditemukan")
        }
        observeFollow()
    }

    private fun observeFollow() {
        followViewModel.dataFollow.observe(viewLifecycleOwner, {
            when (it.state) {
                State.SUCCESS ->
                    if (!it.data.isNullOrEmpty()) {
                        followSuccess(followBinding)
                        githubAdapter.run { setData(it.data) }
                    } else {
                        followError(followBinding, "Tidak dapat mendapatkan data")
                    }
                State.LOADING -> followLoading(followBinding)
                State.ERROR -> followError(followBinding, it.message)
            }
        })
    }

    override fun followLoading(followBinding: FragmentFollowBinding): Int? {
        followBinding.apply {
            emptyText.visibility = View.GONE
            progress.visibility = View.VISIBLE
            followRecyclerView.visibility = View.GONE
        }
        return super.followLoading(followBinding)
    }

    override fun followSuccess(followBinding: FragmentFollowBinding): Int? {
        followBinding.apply {
            emptyText.visibility = View.GONE
            progress.visibility = View.GONE
            followRecyclerView.visibility = View.VISIBLE
        }
        return super.followSuccess(followBinding)
    }

    override fun followError(followBinding: FragmentFollowBinding, message: String?): Int? {
        followBinding.apply {
            emptyText.visibility = View.VISIBLE
            emptyText.text = message
            progress.visibility = View.GONE
            followRecyclerView.visibility = View.GONE
        }
        return super.followError(followBinding, message)
    }
}