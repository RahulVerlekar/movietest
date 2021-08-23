package com.rahulverlekar.moviedb.ui.moviedetail

import androidx.lifecycle.MutableLiveData
import com.rahulverlekar.domain.model.movie.CreditDetails
import com.rahulverlekar.domain.model.movie.Movie
import com.rahulverlekar.domain.model.movie.MovieDetail
import com.rahulverlekar.domain.model.movie.Review
import com.rahulverlekar.domain.usecase.movies.MovieUseCase
import com.rahulverlekar.moviedb.base.BaseViewModel
import com.rahulverlekar.moviedb.utils.BaseEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(val movieUseCase: MovieUseCase) : BaseViewModel() {

    val movie: MutableLiveData<MovieDetail> = MutableLiveData()
    val reviews: MutableLiveData<List<Review>> = MutableLiveData()
    val credit: MutableLiveData<CreditDetails> = MutableLiveData()
    val similar: MutableLiveData<List<Movie>> = MutableLiveData()

    init {

    }

    fun onViewCreated(movieId: Int) {
        launch {
            try {
                val review = movieUseCase.getReviews(movieId)
                reviews.postValue(review)
                val similarRes = movieUseCase.getSimilar(movieId)
                similar.postValue(similarRes)
                val data = movieUseCase.getMovie(movieId)
                movie.postValue(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onShowAllReviews() {
        sendEvent(ShowAllReviewEvent(reviews.value ?: emptyList()))
    }

}

data class ShowAllReviewEvent(val review: List<Review>) : BaseEvent()