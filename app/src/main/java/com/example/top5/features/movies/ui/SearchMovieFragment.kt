package com.example.top5.features.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.top5.databinding.FragmentSearchMovieBinding
import com.example.top5.navigation.domain.Navigator
import com.example.top5.utils.setOnClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieFragment : Fragment() {

    private lateinit var binding: FragmentSearchMovieBinding

    private val searchMovieViewModel: SearchMovieViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    private val adapter = SearchedMovieListAdapter(
        onMovieClick = { movie ->
            searchMovieViewModel.onMovieClick(
                movie,
                onActionComplete = { navigator.navigateBack() })
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.adapter = adapter

        searchMovieViewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        binding.search.setOnClick {
            searchMovieViewModel.searchMovies(binding.searchQuery.text.toString())
        }
    }
}