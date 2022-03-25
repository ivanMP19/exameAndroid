package com.ivan.marin.exameandroid.ui.viewModels

import androidx.lifecycle.ViewModel
import com.ivan.marin.exameandroid.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository):ViewModel() {
    val movies = repository.getMovies()
}