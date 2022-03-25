package com.ivan.marin.exameandroid.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ivan.marin.exameandroid.R
import com.ivan.marin.exameandroid.databinding.FragmentMovieListBinding
import com.ivan.marin.exameandroid.ui.adapters.MovieRecyclerView
import com.ivan.marin.exameandroid.ui.interfaces.MovieItemListener
import com.ivan.marin.exameandroid.ui.viewModels.MovieListViewModel
import com.ivan.marin.exameandroid.utils.Constantes.ID_MOVIE
import com.ivan.marin.exameandroid.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MovieListFragment : Fragment(), MovieItemListener {
    private lateinit var mBinding:FragmentMovieListBinding

    private val viewModel : MovieListViewModel by viewModels()
    private lateinit var mAdapter : MovieRecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMovieListBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView(){
        mAdapter = MovieRecyclerView(this)
        val mGridLayout = GridLayoutManager(requireContext(),resources.getInteger(R.integer.main_columns))

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter=mAdapter
        }

    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner, {
            when (it.status) {
                NetworkState.Status.SUCCESS -> {
                    mBinding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) mAdapter.setItems(ArrayList(it.data))
                }
                NetworkState.Status.FAILED ->
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()

                NetworkState.Status.RUNNING ->
                    mBinding.progressBar.visibility = View.VISIBLE
                else -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClickedMovie(MovieId: Int) {
        findNavController().navigate(
            R.id.action_nav_home_movieListFragment_detailsMovieFragment,
            bundleOf(ID_MOVIE to MovieId)
        )
    }

}