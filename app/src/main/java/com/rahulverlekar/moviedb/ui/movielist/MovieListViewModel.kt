package com.rahulverlekar.moviedb.ui.movielist

import androidx.lifecycle.MutableLiveData
import com.rahulverlekar.domain.model.config.MovieDbConfig
import com.rahulverlekar.domain.model.movie.Movie
import com.rahulverlekar.domain.usecase.movies.MovieUseCase
import com.rahulverlekar.moviedb.base.BaseViewModel
import com.rahulverlekar.moviedb.utils.BaseEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(val api: MovieUseCase) : BaseViewModel() {

    val movies = MutableLiveData<List<Movie>>()

    init {
        fetchMovieList()
    }

    private fun fetchMovieList() {
        launch {
            try {
                val data = api.getMovies()
                movies.postValue(data)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun onMovieClicked(item: MovieItem) {
        sendEvent(NavToMovieDetail(item.id))
    }

    data class ConfigFetched(val config: MovieDbConfig): BaseEvent()
    data class NavToMovieDetail(val id: Int): BaseEvent()
}