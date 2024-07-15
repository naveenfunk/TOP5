package com.example.top5.features.favorites.ui.topFive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.top5.databinding.ItemMovieListBinding
import com.example.top5.features.favorites.domain.models.TopFiveType
import com.example.top5.features.favorites.ui.models.MovieListItem
import com.example.top5.utils.setOnClick

class MovieListAdapter(
    private val onMovieDeleteClick: (movie: MovieListItem) -> Unit,
    private val onMovieAddClick: (rank: Int) -> Unit,
) : ListAdapter<MovieListItem, MovieListAdapter.MovieHolder>(MovieItemDiffCallback()) {

    inner class MovieHolder(private val itemMovieListBinding: ItemMovieListBinding) :
        ViewHolder(itemMovieListBinding.root) {

        fun bind(movie: MovieListItem) {
            itemMovieListBinding.movieTitle.visibility =
                if (movie.topFiveType == TopFiveType.CREATE_TOP_FIVE) View.GONE else View.VISIBLE
            movie.title?.let {
                itemMovieListBinding.movieTitle.text = it
            }
            itemMovieListBinding.addMovie.visibility =
                if (movie.topFiveType == TopFiveType.CREATE_TOP_FIVE) View.VISIBLE else View.GONE
            itemMovieListBinding.delete.visibility =
                if (movie.topFiveType == TopFiveType.EDITABLE_TOP_FIVE) View.VISIBLE else View.GONE
            itemMovieListBinding.movieImage.visibility =
                if (movie.topFiveType == TopFiveType.CREATE_TOP_FIVE) View.GONE else View.VISIBLE

            itemMovieListBinding.delete.setOnClick { onMovieDeleteClick(movie) }
            itemMovieListBinding.addMovie.setOnClick {
                onMovieAddClick(adapterPosition + 1)
            }
            movie.imageUrl?.let {
                Glide.with(itemMovieListBinding.root).load(it)
                    .into(itemMovieListBinding.movieImage)
            }
        }
    }

    class MovieItemDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {
        override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            ItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }
}