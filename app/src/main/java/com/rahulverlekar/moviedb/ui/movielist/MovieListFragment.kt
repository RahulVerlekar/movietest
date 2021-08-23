package com.rahulverlekar.moviedb.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rahulverlekar.moviedb.R
import com.rahulverlekar.moviedb.base.BaseFragment
import com.rahulverlekar.moviedb.base.BaseViewModel
import com.rahulverlekar.moviedb.databinding.MovieListFragmentBinding
import com.rahulverlekar.moviedb.utils.BaseEvent
import com.rahulverlekar.moviedb.ext.ListItem
import com.rahulverlekar.moviedb.ext.RecyclerViewCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<MovieListFragmentBinding>(R.layout.movie_list_fragment),
    MovieItemCallback {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private val viewModel: MovieListViewModel by viewModels()

    override fun getVM(): BaseViewModel = viewModel

    override fun attachBinding() {
        binding.lifecycleOwner = this
        binding.handler = this
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            val listItems = movies.map {
                MovieItem(it.id, it.name, it.poster, it.releaseDate.toString())
            }
            binding.rvMovies.addDataSource(listItems, R.layout.item_movie_list, this)
        })
    }

    override fun handleEvent(event: BaseEvent) {
        when (event) {
            is MovieListViewModel.NavToMovieDetail -> {
//                if (!event.isConsumed) {
//                    event.consume()
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                        event.id
                    )
                )
//                }
            }
        }
    }


    override fun movieClicked(item: MovieItem) {
        viewModel.onMovieClicked(item)
    }
}

data class MovieItem(val id: Int, val title: String, val image: String, val releaseDate: String) :
    ListItem {

}

interface MovieItemCallback : RecyclerViewCallback {
    fun movieClicked(item: MovieItem)
}
