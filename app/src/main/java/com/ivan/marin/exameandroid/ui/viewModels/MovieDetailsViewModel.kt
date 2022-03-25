package com.ivan.marin.exameandroid.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ivan.marin.exameandroid.data.entities.MovieModel
import com.ivan.marin.exameandroid.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()

    private val _character = _id.switchMap { id ->
        repository.getMovie(id)
    }
    val movie: LiveData<MovieModel> = _character


    fun start(id: Int) {
        _id.value = id
    }
}