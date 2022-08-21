package com.example.dictionarycardswipe.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dictionarycardswipe.R
import com.example.dictionarycardswipe.databinding.FragmentDetailBinding
import com.example.dictionarycardswipe.databinding.FragmentMainBinding
import com.example.dictionarycardswipe.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<MainViewModel>()
        binding = FragmentDetailBinding.bind(view)

        var bundle_data = requireArguments().get("acronym_details") as ArrayList<String>

        binding.textView.text = bundle_data[0]

    }
}