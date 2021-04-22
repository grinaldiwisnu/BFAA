package com.example.githubuserapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.databinding.FragmentDetailBinding
import com.example.githubuserapp.utils.State
import com.example.githubuserapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {
    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var detailViewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    inner class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = tabList.size
        override fun createFragment(position: Int): Fragment =
            FollowFragment.newInstance(username, tabList[position])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setDetail(args.Username)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        detailBinding.lifecycleOwner = viewLifecycleOwner
        detailViewModel.dataDetail.observe(viewLifecycleOwner, {
            if (it.state == State.SUCCESS) {
                detailBinding.data = it.data
            }
        })

        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabList = arrayOf(
            "Followers",
            "Following"
        )

        pagerAdapter = PagerAdapter(tabList, args.Username, this)
        detailBinding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(detailBinding.tabLayout, detailBinding.viewPager) { tab, pos ->
            tab.text = tabList[pos]
        }.attach()
    }
}