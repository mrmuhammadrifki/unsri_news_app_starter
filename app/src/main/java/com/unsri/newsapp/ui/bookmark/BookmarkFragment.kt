package com.unsri.newsapp.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsri.newsapp.databinding.FragmentNewsBinding
import com.unsri.newsapp.ui.NewsAdapter
import com.unsri.newsapp.ui.ViewModelFactory

class BookmarkFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding

    private lateinit var factory: ViewModelFactory
    private val viewModel: BookmarkViewModel by viewModels {
        factory
    }

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())
        newsAdapter = NewsAdapter { news ->
           if (news.isBookmarked) {
               viewModel.deleteNews(news)
           }
        }

        setupBookmarkedNewsData()
        setupRecyclerView()
    }

    private fun setupBookmarkedNewsData() {
        viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
            if (bookmarkedNews.isNotEmpty()) {
                binding?.progressBar?.visibility = View.GONE
                binding?.ivNodataFound?.visibility = View.GONE
                binding?.tvNoDataFound?.visibility = View.GONE
                newsAdapter.submitList(bookmarkedNews)
            } else {
                binding?.progressBar?.visibility = View.GONE
                newsAdapter.submitList(emptyList())
                binding?.ivNodataFound?.visibility = View.VISIBLE
                binding?.tvNoDataFound?.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}