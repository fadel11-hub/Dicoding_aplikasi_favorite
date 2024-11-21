package com.example.dicodingaplikasi.ui.finished

import FinishedAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.databinding.FragmentFinishedBinding
import com.example.dicodingaplikasi.ui.DetailEvent.DetailEventActivity


class FinishedFragment : Fragment() {

    private var _binding : FragmentFinishedBinding? = null
    private val binding get() = _binding

    private val finishedViewModel by viewModels<FinishedViewModel>()
    private lateinit var adapter : FinishedAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FinishedAdapter{event -> openEventDetail(event)}
        binding?.rvFinished?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvFinished?.adapter = adapter

        finishedViewModel.isLoading.observe(viewLifecycleOwner) { events ->
            showLoading(events)
        }

        finishedViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }

    }

    private fun openEventDetail(event: ListEventsItem) {
        val intent = Intent(requireContext(), DetailEventActivity::class.java)
        intent.putExtra(DetailEventActivity.EXTRA_PHOTO, event.imageLogo)
        intent.putExtra(DetailEventActivity.EXTRA_NAME, event.name)
        intent.putExtra(DetailEventActivity.EXTRA_OWNER, event.ownerName)
        intent.putExtra(DetailEventActivity.EXTRA_LOCATION, event.cityName)
//      Data yang hanya di tampilkan event detail
        intent.putExtra(DetailEventActivity.EXTRA_DESCRIPTION, event.description)
        intent.putExtra(DetailEventActivity.EXTRA_BEGIN_TIME, event.beginTime)
        intent.putExtra(DetailEventActivity.EXTRA_REGISTRANTS, event.registrants)
        intent.putExtra(DetailEventActivity.EXTRA_QUOTA, event.quota)
        intent.putExtra(DetailEventActivity.EXTRA_SUMMARY, event.summary)
        intent.putExtra(DetailEventActivity.EXTRA_LINK, event.link)
        intent.putExtra(DetailEventActivity.EXTRA_ID, event.id)
        startActivity(intent)
    }


    private fun showLoading(isLoading: Boolean){
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}