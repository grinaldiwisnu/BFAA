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

    private lateinit var favoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: GithubAdapter
    private val favoriteViewModel: FavoriteViewModel by navGraphViewModels(R.id.navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Favorite User"
        favoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = GithubAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsDestination(username),
                FragmentNavigatorExtras(iv to username)
            )
        }

        favoriteBinding.favRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        observeFavorite()
    }

    private fun observeFavorite() {
        favoriteLoading(favoriteBinding)
        favoriteViewModel.dataFavorite.observe(viewLifecycleOwner, Observer {
            it?.let { users ->
                if (!users.isNullOrEmpty()){
                    favoriteSuccess(favoriteBinding)
                    favoriteAdapter.setData(users)
                } else {
                    favoriteError(
                        favoriteBinding,
                        "Tidak ada user ditemukan"
                    )
                }
            }
        })
    }

    override fun favoriteLoading(favoriteBinding: FragmentFavoriteBinding): Int? {
        favoriteBinding.apply {
            emptyText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            favRecyclerview.visibility = View.GONE
        }
        return super.favoriteLoading(favoriteBinding)
    }

    override fun favoriteSuccess(favoriteBinding: FragmentFavoriteBinding): Int? {
        this.favoriteBinding.apply {
            emptyText.visibility = View.GONE
            progressBar.visibility = View.GONE
            favRecyclerview.visibility = View.VISIBLE
        }
        return super.favoriteSuccess(favoriteBinding)
    }

    override fun favoriteError(
        favoriteBinding: FragmentFavoriteBinding,
        message: String?
    ): Int? {
        this.favoriteBinding.apply {
            emptyText.visibility = View.GONE
            emptyText.text = message
            progressBar.visibility = View.VISIBLE
            favRecyclerview.visibility = View.GONE
        }
        return super.favoriteError(favoriteBinding, message)
    }
}