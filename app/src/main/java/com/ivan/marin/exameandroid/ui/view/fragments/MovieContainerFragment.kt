package com.ivan.marin.exameandroid.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivan.marin.exameandroid.R
import com.ivan.marin.exameandroid.databinding.FragmentMovieContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieContainerFragment : Fragment() {
    lateinit var binding: FragmentMovieContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieContainerBinding.inflate(inflater,container,false)
        return binding.root
    }

}