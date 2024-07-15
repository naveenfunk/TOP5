package com.example.top5.features.favorites.ui.topFive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.top5.R
import com.example.top5.databinding.FragmentTopFiveBinding
import com.example.top5.features.favorites.ui.models.MovieListItem
import com.example.top5.navigation.domain.Navigator
import com.example.top5.utils.setOnClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TopFiveFragment : Fragment() {

    private lateinit var binding: FragmentTopFiveBinding

    private val topFiveViewModel: TopFiveViewModel by viewModels()

    private val adapter = MovieListAdapter(
        onMovieDeleteClick = { movie: MovieListItem -> topFiveViewModel.deleteMovie(movie) },
        onMovieAddClick = { rank: Int -> navigateToSearch(rank) }
    )

    private fun navigateToSearch(rank: Int) {
        topFiveViewModel.getFavoriteId()
            ?.let { navigator.navigateFromTopFiveToSearchMovie(it, rank) }
    }

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopFiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.adapter = adapter

        topFiveViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            adapter.submitList(movieList)
        }
        topFiveViewModel.favoriteDat.observe(viewLifecycleOwner) { favorite ->
            binding.listTitle.setText(favorite.title ?: "")
            binding.createList.text = getString(R.string.update)
            binding.emoji.text = favorite.emoji
        }

        binding.createList.setOnClick {
            topFiveViewModel.createFavorite(
                binding.emoji.text.toString(),
                binding.listTitle.text.toString()
            )
        }
        topFiveViewModel.isOnlyViewable.observe(viewLifecycleOwner) { viewable ->
            binding.createList.visibility = if (viewable) View.GONE else View.VISIBLE
            binding.listTitle.isEnabled = !viewable
        }
    }

    override fun onResume() {
        super.onResume()
        topFiveViewModel.refreshData()
    }

    companion object {
        const val FAVORITE_ID: String = "fav_id"
        const val MOVIE_RANK: String = "movie_rank"
    }
}