package com.rhaegar.meipai.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rhaegar.meipai.R
import com.rhaegar.meipai.databinding.ActivityMainBinding
import com.rhaegar.meipai.databinding.ItemMainTabBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.item_main_tab.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() , HasSupportFragmentInjector {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val mAdapter: MainTabAdapter by lazy { MainTabAdapter(supportFragmentManager) }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.viewModel = mViewModel
        activityMainBinding.adapter = mAdapter
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.vpContent.offscreenPageLimit=3
        activityMainBinding.tabLayout.setupWithViewPager(activityMainBinding.vpContent)
        activityMainBinding.tabLayout.post {
            for (i in 0..2) {
                val inflate = LayoutInflater.from(this).inflate(
                    R.layout.item_main_tab,
                    null
                )
                inflate.iv.setImageResource(mAdapter.icons[i])
                val itemMainTabBinding = DataBindingUtil.bind<ItemMainTabBinding>(inflate)
                itemMainTabBinding?.icon = mAdapter.icons[i]
                activityMainBinding.tabLayout.getTabAt(i)?.customView=inflate
            }
        }


        mViewModel.index.observe(this, Observer {
            if (it==0){
                (activityMainBinding.tabLayout.getTabAt(1)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener true
                }

                (activityMainBinding.tabLayout.getTabAt(2)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener true
                }
            }else{
                (activityMainBinding.tabLayout.getTabAt(1)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener false
                }

                (activityMainBinding.tabLayout.getTabAt(2)?.view as View).setOnTouchListener {_,_->
                    return@setOnTouchListener false
                }
            }
        })

    }





    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
