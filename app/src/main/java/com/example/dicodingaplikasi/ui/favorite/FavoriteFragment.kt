package com.example.dicodingaplikasi.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingaplikasi.databinding.FragmentFavoriteBinding
import com.example.dicodingaplikasi.ui.ViewModelFactory
import com.example.dicodingaplikasi.data.local.repository.Result
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.ui.DetailEvent.DetailEventActivity


class FavoriteFragment : Fragment() {

    private var favoriteName: String? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        favoriteName = arguments?.getString(ARG_FAVORITE)
//
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: FavoriteViewModel by viewModels { factory }

        val favoriteAdapter = FavoriteAdapter(onFavoriteClick = { event ->
            val intent = Intent(requireContext(), DetailEventActivity::class.java).apply {
                putExtra(DetailEventActivity.EXTRA_PHOTO, event.mediaCover)
                putExtra(DetailEventActivity.EXTRA_NAME, event.name)
                putExtra(DetailEventActivity.EXTRA_OWNER, "")
                putExtra(DetailEventActivity.EXTRA_LOCATION, "")
                putExtra(DetailEventActivity.EXTRA_DESCRIPTION, "")
                putExtra(DetailEventActivity.EXTRA_BEGIN_TIME, "")
                putExtra(DetailEventActivity.EXTRA_REGISTRANTS, 0)
                putExtra(DetailEventActivity.EXTRA_QUOTA, 0)
                putExtra(DetailEventActivity.EXTRA_SUMMARY, "")
                putExtra(DetailEventActivity.EXTRA_LINK, "")
                putExtra(DetailEventActivity.EXTRA_ID, event.id)
            }

            startActivity(intent)
        })

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        viewModel.getFavoriteEvents()
        viewModel.getFavoriteEvents().observe(viewLifecycleOwner) { events ->
            favoriteAdapter.submitList(
                events.map {
                    ListEventsItem(
                        summary = "",
                        mediaCover = "",
                        registrants = 0,
                        imageLogo = it.mediaCover ?: "",
                        link = "",
                        description = "",
                        ownerName = "",
                        cityName = "",
                        quota = 0,
                        name = it.name,
                        id = it.id,
                        beginTime = "",
                        endTime = "",
                        category = ""
                    )
                }
            )

        }

//        if (favoriteName == TAB_EVENT) {
//            viewModel.getFavorite().observe(viewLifecycleOwner, { result ->
//                if (result != null) {
//                    when (result) {
//                        is Result.Loading -> {
//                            binding?.progressBar?.visibility = View.VISIBLE
//                        }
//                        is Result.Success -> {
//                            binding?.progressBar?.visibility = View.GONE
//                            val favoriteData = result.data
//                            favoriteAdapter.submitList(favoriteData)
//                        }
//                        is Result.Error -> {
//                            binding?.progressBar?.visibility = View.GONE
//                            Toast.makeText(
//                                context,
//                                "Terjadi kesalahan" + result.error,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//            })
//        } else if (favoriteName == TAB_FAVORITE) {
//            viewModel.getFavorite().observe(viewLifecycleOwner) { saveFavorited ->
//                binding?.progressBar?.visibility = View.GONE
//                favoriteAdapter.submitList(saveFavorited)
//            }
//        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Menghapus referensi binding saat view dihancurkan untuk mencegah memory leaks
    }

//    companion object {
//        const val ARG_FAVORITE = "favorite_name"
//        const val TAB_EVENT = "event"
//        const val TAB_FAVORITE = "favorite"
//    }
}