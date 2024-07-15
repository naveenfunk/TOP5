package com.example.top5.features.favorites.ui.viewableProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.top5.databinding.FragmentProfileBinding
import com.example.top5.features.favorites.ui.profile.FavoritesListAdapter
import com.example.top5.navigation.domain.Navigator
import com.example.top5.utils.setOnClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewableProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ViewableProfileViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    private val adapter = FavoritesListAdapter(
        onFavoriteClick = { favorite -> navigator.navigateFromViewableProfileToTopFive(favorite.id) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userHeader.root.visibility = View.GONE
        binding.findHeader.root.visibility = View.VISIBLE

        binding.topList.adapter = adapter

        viewModel.favoritesList.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
        }
        binding.findHeader.searchButton.setOnClick {
            viewModel.findByEmail(binding.findHeader.searchQuery.text.toString())
        }
    }
}