package com.tanyareznikova.gifapptinkoff.view.adapter

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tanyareznikova.gifapptinkoff.view.fragments.HotGifFragment
import com.tanyareznikova.gifapptinkoff.view.fragments.LatestGifFragment
import com.tanyareznikova.gifapptinkoff.view.fragments.TopGifFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when(position) {
            0-> {
                LatestGifFragment()
            }
            1-> {
                TopGifFragment()
            }
            2-> {
                HotGifFragment()
            }
            else-> {
                Fragment()
            }
        }

    }


}