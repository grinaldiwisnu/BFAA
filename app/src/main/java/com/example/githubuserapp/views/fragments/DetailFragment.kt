package com.example.githubuserapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentDetailBinding
import com.example.githubuserapp.models.UserGithub
import com.example.githubuserapp.utils.State
import com.example.githubuserapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator


class DetailFragment : Fragment() {
    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var detailViewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()
    private var isFavorite: Boolean = false
    private lateinit var model: UserGithub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(
            this
        ).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        detailBinding.lifecycleOwner = viewLifecycleOwner
        observeDetail()
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            detailBinding.content.transitionName = args.Username
        }
        detailBinding.fabFavorite.setOnClickListener { addOrRemoveFavorite() }
        val tabList = arrayOf(
            resources.getString(R.string.followers),
            resources.getString(R.string.following)
        )
        pagerAdapter = PagerAdapter(tabList, args.Username!!, this)
        detailBinding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(detailBinding.tabLayout, detailBinding.viewPager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun observeDetail() {
        detailViewModel.data(args.Username).observe(viewLifecycleOwner, {
            if (it.state == State.SUCCESS) {
                model = it.data!!
                detailBinding.data = it.data
            }
        })

        detailViewModel.isFavorite.observe(viewLifecycleOwner, {
            isFavorite = it
            changeFavorite(it)
        })
    }

    private fun addOrRemoveFavorite(){
        if (!isFavorite) {
            val user = model
            detailViewModel.addFavorite(user)
            Toast.makeText(context, "Ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
        } else {
            val user = model
            detailViewModel.removeFavorite(user)
            Toast.makeText(context, "Dihapus dari favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeFavorite(condition: Boolean) {
        if (condition) {
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    inner class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = tabList.size

        override fun createFragment(position: Int): Fragment =
            FollowFragment.newInstance(username, tabList[position])
    }
}