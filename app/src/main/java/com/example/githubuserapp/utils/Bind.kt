package com.example.githubuserapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.R

@BindingAdapter("avatar")
fun avatar(imageView: ImageView, avatar: String) =
    Glide.with(imageView)
        .load(avatar)
        .apply(
            RequestOptions.circleCropTransform()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
        )
        .into(imageView)