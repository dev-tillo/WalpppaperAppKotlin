package com.example.walpppaperappkotlin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.adapters.HomeAdapter
import com.example.walpppaperappkotlin.adapters.PaginationAdapter
import com.example.walpppaperappkotlin.databinding.FragmentBlankBinding
import com.example.walpppaperappkotlin.models.Result
import com.example.walpppaperappkotlin.models.UnSplashUsers
import com.example.walpppaperappkotlin.retrofit.ApiClient
import com.example.walpppaperappkotlin.ui.fragments.tayyorlar.NetworkHelper
import com.example.walpppaperappkotlin.ui.fragments.tayyorlar.PaginationScrollListener
import com.example.walpppaperappkotlin.utils.SplashResource
import com.example.walpppaperappkotlin.utils.SplashViewModel
import com.example.walpppaperappkotlin.utils.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val ARG_PARAM1 = "param1"

class BlankFragment : Fragment() {

    private var param1: String? = null

    private lateinit var fragment: FragmentBlankBinding
    private lateinit var adapter: PaginationAdapter
    private var url: String = ""
    private var PAGE = 1
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var list: ArrayList<Result>
    private var isLoading = false
    private var isLastPage = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_PARAM1) as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentBlankBinding.inflate(inflater, container, false)

        fragment.apply {
            list = ArrayList()

            adapter =
                PaginationAdapter(requireContext(), list, object : PaginationAdapter.onItemClicked {
                    override fun onCliked(url: Result) {
                        val bundle = Bundle()
                        bundle.putSerializable("image", url)
                        bundle.putInt("key", 1)
                        findNavController().navigate(R.id.imageFragment, bundle)
                    }
                })
            val gridLayoutManager = GridLayoutManager(requireContext(), 3)
            recycler.layoutManager = gridLayoutManager
            recycler.adapter = adapter

            val networkHelper = NetworkHelper(requireContext())

            splashViewModel = ViewModelProvider(this@BlankFragment,
                ViewModelFactory(networkHelper))[SplashViewModel::class.java]

            splashViewModel.fetchSplash(url, PAGE).observe(viewLifecycleOwner, {
                when (it) {
                    is SplashResource.Loading -> {
                        recycler.visibility = View.GONE
                    }
                    is SplashResource.Success -> {
                        recycler.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "page $PAGE", Toast.LENGTH_SHORT).show()
                        adapter.addAll(it.unsplash.results)

                        if (PAGE != 6) {
                            adapter.addLoading()
                        } else {
                            isLastPage = true
                        }
                    }
                    is SplashResource.Error -> {
                        recycler.visibility = View.GONE
                        val textview = TextView(requireContext())
                        val layoutParam = ConstraintLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParam.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                        layoutParam.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                        layoutParam.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                        layoutParam.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                        textview.text = it.message
                        textview.layoutParams = layoutParam
                    }
                }
            })

            fragment.recycler.addOnScrollListener(object :
                PaginationScrollListener(gridLayoutManager) {
                override fun loadMoreItems() {
                    isLoading = true
                    PAGE++
                    getNext(PAGE)
                }

                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

            })
        }
        return fragment.root
    }

    fun getNext(page: Int) {
        fragment.apply {
            val networkHelper = NetworkHelper(requireContext())
            splashViewModel = ViewModelProvider(this@BlankFragment,
                ViewModelFactory(networkHelper))[SplashViewModel::class.java]
            splashViewModel.fetchSplash(url, page).observe(viewLifecycleOwner, {
                when (it) {
                    is SplashResource.Loading -> {
                    }
                    is SplashResource.Success -> {
                        isLoading = false
                        Toast.makeText(requireContext(), "page $PAGE", Toast.LENGTH_SHORT).show()
                        adapter.addAll(it.unsplash.results)

                        if (PAGE != 6) {
                            adapter.addLoading()
                        } else {
                            isLastPage = true
                        }
                    }
                    is SplashResource.Error -> {

                    }
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}