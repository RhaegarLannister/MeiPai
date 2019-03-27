package com.rhaegar.meipai.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rhaegar.meipai.R
import com.rhaegar.meipai.base.BaseFragment
import com.rhaegar.meipai.databinding.FragmentStartPositionBinding
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
class StartPositionFragment: BaseFragment() {

    lateinit var fragmentStartPositionBinding:FragmentStartPositionBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentStartPositionBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_start_position,container,false)
        fragmentStartPositionBinding.lifecycleOwner=this
        fragmentStartPositionBinding.viewModel=mViewModel
        return fragmentStartPositionBinding.root

    }

}