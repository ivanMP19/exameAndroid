package com.ivan.marin.exameandroid.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ivan.marin.exameandroid.R
import com.ivan.marin.exameandroid.data.entities.MovieModel
import com.ivan.marin.exameandroid.databinding.FragmentDetailsMovieBinding
import com.ivan.marin.exameandroid.ui.viewModels.MovieDetailsViewModel
import com.ivan.marin.exameandroid.utils.Constantes
import com.ivan.marin.exameandroid.utils.Constantes.ID_MOVIE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsMovieFragment : Fragment() {

    private lateinit var binding: FragmentDetailsMovieBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ID_MOVIE)?.let { viewModel.start(it) }

        setupObservers()

        binding.ivOnBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_home_detailsMovieFragment_movieListFragment
            )
        }
    }

    private fun setupObservers() {
        viewModel.movie.observe(viewLifecycleOwner, {
            bindCharacter(it)
        })
    }

    private fun bindCharacter(movie: MovieModel) {
        binding.title.text = movie.mOriginalTitle
        binding.releaseDate.text = movie.mReleaseDate
        binding.overview.text = movie.mOverview
        binding.cover.let {
            Glide.with(binding.root)
                .load(Constantes.SMALL_IMAGE_URL_PREFIX +movie.mPosterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(it)
        }
    }

}