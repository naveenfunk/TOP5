package com.example.top5.features.favorites.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.top5.databinding.FragmentProfileBinding
import com.example.top5.navigation.domain.Navigator
import com.example.top5.utils.setOnClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    private val adapter = FavoritesListAdapter(
        onFavoriteClick = { favorite -> navigator.navigateFromHomeToTopFive(favorite.id) },
        onDeleteClick = { favorite -> viewModel.deleteFavorite(favorite) }
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

        binding.userHeader.root.visibility = View.VISIBLE
        binding.findHeader.root.visibility = View.GONE

        binding.userHeader.logout.setOnClick {
            viewModel.logout()
            navigator.navigateFromHomeToLogin()
        }
        binding.userHeader.createList.setOnClick {
            navigator.navigateFromHomeToTopFive("")
        }
        binding.userHeader.findUser.setOnClick {
            navigator.navigateFromHomeToViewableProfile()
        }

        binding.topList.adapter = adapter

        viewModel.favoritesList.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
    }
}