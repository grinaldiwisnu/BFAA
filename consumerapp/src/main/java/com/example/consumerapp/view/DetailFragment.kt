package com.example.consumerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.consumerapp.databinding.FragmentDetailBinding
import com.example.consumerapp.models.UserModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailFragment : BottomSheetDialogFragment() {
    private lateinit var userModel: UserModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userModel = it.getParcelable(USER)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        binding.data = userModel
        return binding.root
    }


    companion object {
        private const val USER = "USER"

        @JvmStatic
        fun newInstance(userModel: UserModel) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, userModel)
                }
            }
    }
}