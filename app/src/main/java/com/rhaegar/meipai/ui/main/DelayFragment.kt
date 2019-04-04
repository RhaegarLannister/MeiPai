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
import com.rhaegar.meipai.databinding.FragmentDelayBinding
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
class DelayFragment: BaseFragment() {

    lateinit var fragmentDelayBinding:FragmentDelayBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentDelayBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_delay,container,false)
        fragmentDelayBinding.lifecycleOwner=this
        fragmentDelayBinding.viewModel=mViewModel

        /*mViewModel.spaceTime.observe(this, Observer {
            mViewModel.textToFloat(it,mViewModel.spaceTime)
        })

        mViewModel.takeNumbers.observe(this, Observer {
            L.e("到了这里")
            mViewModel.textToInt(it,mViewModel.takeNumbers)
        })

        mViewModel.buttonTimes.observe(this, Observer {
            mViewModel.textToFloat(it,mViewModel.buttonTimes)
        })*/



        return fragmentDelayBinding.root

    }

}