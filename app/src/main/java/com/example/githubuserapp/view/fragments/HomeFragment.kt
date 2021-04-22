package com.example.githubuserapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.GithubAdapter
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.utils.ShowState
import com.example.githubuserapp.utils.State
import com.example.githubuserapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    companion object {
        const val stateId = 1
    }

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var githubAdapter: GithubAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var showState = ShowState(stateId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(HomeViewModel::class.java)
        homeBinding.emptyText.text = resources.getString(R.string.empty_state)
        githubAdapter = GithubAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                HomeFragmentDirections.action(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        }
        homeBinding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = githubAdapter
        }

        homeBinding.searchView.apply {
            queryHint = "Masukkan username github terlebih dahulu ..."
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.setSearch(query)
                    homeBinding.searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }
        observeHome()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    private fun observeHome() {
        homeViewModel.searchResult.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.state) {
                    State.SUCCESS -> {
                        resource.data?.let { users ->
                            if (!users.isNullOrEmpty()) {
                                showState.success(homeBinding, null)
                                githubAdapter.setData(users)
                            } else {
                                showState.error(homeBinding, null, null)
                            }
                        }
                    }
                    State.LOADING -> showState.loading(homeBinding, null)
                    State.ERROR -> showState.error(homeBinding, null, it.message)
                }
            }
        })
    }

}