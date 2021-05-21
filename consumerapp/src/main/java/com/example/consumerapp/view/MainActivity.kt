package com.example.consumerapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.databinding.ActivityMainBinding
import com.example.consumerapp.view.adapter.MainAdapter
import com.example.consumerapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityBinding: ActivityMainBinding
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        adapter = MainAdapter(ArrayList()) { userModel ->
            val fragment = DetailFragment.newInstance(userModel)
            fragment.show(supportFragmentManager, "userDetail")
        }

        activityBinding.mainRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapter
        }

        mainViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MainViewModel::class.java)
        activityBinding.progress.visibility = View.VISIBLE

        mainViewModel.userLists.observe(this) { users ->
            if (!users.isNullOrEmpty()) {
                activityBinding.progress.visibility = View.GONE
                adapter.setData(users)
            } else {
                activityBinding.apply {
                    progress.visibility = View.GONE
                    errLayout.visibility = View.VISIBLE
                }
            }
        }
    }
}