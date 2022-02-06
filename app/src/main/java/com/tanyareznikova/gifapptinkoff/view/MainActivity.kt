package com.tanyareznikova.gifapptinkoff.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tanyareznikova.gifapptinkoff.R
import com.tanyareznikova.gifapptinkoff.databinding.ActivityMainBinding
import com.tanyareznikova.gifapptinkoff.utils.APP_ACTIVITY
import com.tanyareznikova.gifapptinkoff.view.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this

        tabLayout = main_tab
        viewPager2 = main_viewpager

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        viewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2){tab, position->
            when(position){
                0-> {
                    tab.text = "Последние"
                }
                1-> {
                    tab.text = "Лучшие"
                }
                2-> {
                    tab.text = "Горячие"
                }
            }
        }.attach()

    }
}