package com.example.mvvm_dagger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_dagger.databinding.FragmentMainBinding
import com.example.mvvm_dagger.di.Injectable
import javax.inject.Inject

/**
 * create by silladus 2020/7/21
 * github:https://github.com/silladus
 * des:
 */
class MainFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel.apply {
                liveData.observe(viewLifecycleOwner, Observer { s ->
                    tvContent.text = s
                })

                tvContent.text = "request data from remote!"
                getData()
            }

        }
        return binding.root
    }
}