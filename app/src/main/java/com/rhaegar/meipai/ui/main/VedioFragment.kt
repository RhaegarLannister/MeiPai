package com.rhaegar.meipai.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rhaegar.meipai.R
import com.rhaegar.meipai.base.BaseFragment
import com.rhaegar.meipai.databinding.FragmentVedioBinding
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
class VedioFragment: BaseFragment() {

    lateinit var fragmentVedioBinding:FragmentVedioBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentVedioBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_vedio,container,false)
        fragmentVedioBinding.viewModel=mViewModel
        fragmentVedioBinding.lifecycleOwner=this

        mViewModel.slideSpeed.observe(this, Observer {

        })

        mViewModel.slideSpeed.observe(this, Observer {

        })


        return fragmentVedioBinding.root

    }

}