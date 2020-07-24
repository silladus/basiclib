package com.example.mvvm_hilt.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.example.mvvm_hilt.databinding.FragmentMainBinding
import com.example.mvvm_hilt.di.FragmentScope
import com.example.mvvm_hilt.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import silladus.basic.adapter.GridItemDecoration
import timber.log.Timber
import javax.inject.Inject

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    @FragmentScope
    @Inject
    lateinit var fragmentHashCode: String

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.e("fragmentHashCode:$fragmentHashCode")
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            val adapter = MultiTypeAdapter()
            adapter.register(PokeItemViewBinder())
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.addItemDecoration(GridItemDecoration(2))
            recyclerView.adapter = adapter

            viewModel.apply {
                isLoading.observe(viewLifecycleOwner) {
                    if (it) {
                        toast { "is loading" }
                    } else {
                        toast { "loading done" }
                    }
                }
                liveData.observe(viewLifecycleOwner) { (ret, err) ->

                    if (err != null) {
                        Timber.e(err.message!!)
                        toast { err.message }
                        return@observe
                    }

                    adapter.items = ret.results
                    adapter.notifyDataSetChanged()
                }

                getData(0)
            }

        }
        return binding.root
    }
}