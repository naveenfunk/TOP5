package com.example.top5.features.favorites.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.top5.databinding.ItemTopListBinding
import com.example.top5.features.favorites.ui.models.FavoriteListItem
import com.example.top5.utils.setOnClick

class FavoritesListAdapter(
    private val onFavoriteClick: (favorite: FavoriteListItem) -> Unit,
    private val onDeleteClick: ((favorite: FavoriteListItem) -> Unit)? = null,
) : ListAdapter<FavoriteListItem, FavoritesListAdapter.FavoriteHolder>(FavoriteItemDiffCallback()) {

    inner class FavoriteHolder(private val itemMovieListBinding: ItemTopListBinding) :
        ViewHolder(itemMovieListBinding.root) {

        fun bind(favoriteListItem: FavoriteListItem) {
            itemMovieListBinding.title.text = favoriteListItem.title
            itemMovieListBinding.emoji.text = favoriteListItem.emoji

            itemMovieListBinding.delete.visibility =
                if (onDeleteClick == null) View.GONE else View.VISIBLE
            itemMovieListBinding.delete.setOnClick {
                onDeleteClick?.invoke(favoriteListItem)
            }

            itemMovieListBinding.root.setOnClick { onFavoriteClick(favoriteListItem) }
        }
    }

    class FavoriteItemDiffCallback : DiffUtil.ItemCallback<FavoriteListItem>() {
        override fun areItemsTheSame(
            oldItem: FavoriteListItem,
            newItem: FavoriteListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavoriteListItem,
            newItem: FavoriteListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            ItemTopListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }
}