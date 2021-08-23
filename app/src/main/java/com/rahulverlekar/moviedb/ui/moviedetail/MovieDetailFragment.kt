package com.rahulverlekar.moviedb.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.rahulverlekar.domain.model.movie.Review
import com.rahulverlekar.moviedb.R
import com.rahulverlekar.moviedb.base.BaseFragment
import com.rahulverlekar.moviedb.base.BaseViewModel
import com.rahulverlekar.moviedb.databinding.MovieDetailFragmentBinding
import com.rahulverlekar.moviedb.ui.movielist.MovieItem
import com.rahulverlekar.moviedb.ui.movielist.MovieItemCallback
import com.rahulverlekar.moviedb.utils.BaseEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailFragmentBinding>(R.layout.movie_detail_fragment), MovieItemCallback {

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    private val viewModel: MovieDetailViewModel by viewModels()

    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun attachBinding() {
        binding.lifecycleOwner = this
        binding.handler = this
        binding.vm = viewModel
    }

    override fun getVM(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated(args.movieId)
        viewModel.similar.observe(viewLifecycleOwner, Observer {movies ->
            val listItems = movies.map {
                MovieItem(it.id, it.name, it.poster, it.releaseDate.toString())
            }
            binding.rvSimilarMovies.addDataSource(listItems, R.layout.item_movie_related, this, RecyclerView.HORIZONTAL)
        })
    }

    override fun handleEvent(event: BaseEvent) {
        when(event) {
            is ShowAllReviewEvent -> {
                showAllReviews(event.review)
            }
        }
    }

    override fun movieClicked(item: MovieItem) {

    }

    fun showAllReviews(review: List<Review>) {
        val bottomSheet = BottomReviewFragment()
        bottomSheet.listener = object : BottomSheetInterface {
            override fun getOptions(): List<ReviewItem> {
                return review.map {
                    ReviewItem(it.id, it.content, it.author)
                }
            }

            override fun onSelected(item: ReviewItem) {
                bottomSheet.dismiss()
            }

            override fun getTitle(): String {
                return "Reviews"
            }
        }

        bottomSheet.show(
            requireActivity().supportFragmentManager,
            "ModalBottomSheet"
        )
    }
}