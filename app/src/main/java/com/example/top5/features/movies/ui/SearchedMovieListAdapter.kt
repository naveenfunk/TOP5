package com.example.top5.features.movies.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.top5.databinding.ItemMovieListBinding
import com.example.top5.features.movies.ui.models.SearchMovieItem
import com.example.top5.utils.setOnClick

class SearchedMovieListAdapter(
    private val onMovieClick: (movie: SearchMovieItem) -> Unit,
) : ListAdapter<SearchMovieItem, SearchedMovieListAdapter.MovieHolder>(SearchedMovieItemDiffCallback()) {

    inner class MovieHolder(private val itemMovieListBinding: ItemMovieListBinding) :
        ViewHolder(itemMovieListBinding.root) {

        fun bind(movie: SearchMovieItem) {
            itemMovieListBinding.movieTitle.text = movie.title
            itemMovieListBinding.addMovie.visibility = View.GONE
            itemMovieListBinding.delete.visibility = View.GONE

            itemMovieListBinding.root.setOnClick { onMovieClick(movie) }
            Glide.with(itemMovieListBinding.root).load(movie.imageUrl)
                .into(itemMovieListBinding.movieImage)
        }
    }

    class SearchedMovieItemDiffCallback : DiffUtil.ItemCallback<SearchMovieItem>() {
        override fun areItemsTheSame(oldItem: SearchMovieItem, newItem: SearchMovieItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchMovieItem,
            newItem: SearchMovieItem
        ): Boolean {
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