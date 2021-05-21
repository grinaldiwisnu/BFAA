package com.example.githubuserapp.views.fragments

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
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.utils.ShowState
import com.example.githubuserapp.utils.State
import com.example.githubuserapp.viewmodel.HomeViewModel
import com.example.githubuserapp.views.adapter.GithubAdapter

class HomeFragment : Fragment(), ShowState {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var githubAdapter: GithubAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(HomeViewModel::class.java)
        homeBinding.emptyText.text = resources.getString(R.string.empty_state)
        githubAdapter = GithubAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                HomeFragmentDirections.detailsAction(username),
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
                                homeSuccess(homeBinding)
                                githubAdapter.setData(users)
                            } else {
                                homeError(homeBinding, "Tidak ada user ditemukan")
                            }
                        }
                    }
                    State.LOADING -> homeLoading(homeBinding)
                    State.ERROR -> homeError(homeBinding, it.message)
                }
            }
        })
    }

    override fun homeLoading(homeBinding: FragmentHomeBinding): Int? {
        homeBinding.apply {
            emptyText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }
        return super.homeLoading(homeBinding)
    }

    override fun homeSuccess(homeBinding: FragmentHomeBinding): Int? {
        homeBinding.apply {
            emptyText.visibility = View.GONE
            progressBar.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        }
        return super.homeSuccess(homeBinding)
    }

    override fun homeError(homeBinding: FragmentHomeBinding, message: String?): Int? {
        homeBinding.apply {
            emptyText.visibility = View.VISIBLE
            emptyText.text = message
            progressBar.visibility = View.GONE
            recyclerview.visibility = View.GONE
        }
        return super.homeError(homeBinding, message)
    }

}