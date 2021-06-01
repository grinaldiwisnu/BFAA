package com.example.githubuserapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentFavoriteBinding
import com.example.githubuserapp.utils.ShowState
import com.example.githubuserapp.viewmodel.FavoriteViewModel
import com.example.githubuserapp.views.adapter.GithubAdapter

class FavoriteFragment : Fragment(), ShowState {

    private lateinit var favoriteBindingFragment: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: GithubAdapter
    private val favoriteViewModel: FavoriteViewModel by navGraphViewModels(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Favorite User"
        favoriteBindingFragment = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteBindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = GithubAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsDestination(username),
                FragmentNavigatorExtras((iv to username))
            )
        }

        favoriteBindingFragment.favRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        observeFavorite()
    }

    private fun observeFavorite() {
        favoriteLoading(favoriteBindingFragment)
        favoriteViewModel.dataFavorite.observe(viewLifecycleOwner, Observer {
            it?.let { users ->
                if (!users.isNullOrEmpty()) {
                    favoriteSuccess(favoriteBindingFragment)
                    favoriteAdapter.setData(users)
                } else {
                    favoriteError(
                        favoriteBindingFragment,
                        resources.getString(
                            R.string.not_have,
                            "",
                            resources.getString(R.string.favorite)
                        )
                    )
                }
            }
        })
    }

    override fun favoriteLoading(favoriteBinding: FragmentFavoriteBinding): Int? {
        this.favoriteBindingFragment.apply {
            emptyText.visibility = gone
            progressBar.visibility = visible
            favRecyclerview.visibility = gone
        }
        return super.favoriteLoading(favoriteBinding)
    }

    override fun favoriteSuccess(favoriteBinding: FragmentFavoriteBinding): Int? {
        this.favoriteBindingFragment.apply {
            emptyText.visibility = gone
            progressBar.visibility = gone
            favRecyclerview.visibility = visible
        }
        return super.favoriteSuccess(favoriteBinding)
    }

    override fun favoriteError(
        favoriteBinding: FragmentFavoriteBinding,
        message: String?
    ): Int? {
        favoriteBindingFragment.apply {
            emptyText.visibility = visible
            progressBar.visibility = gone
            favRecyclerview.visibility = gone
        }
        return super.favoriteError(favoriteBinding, message)
    }
}