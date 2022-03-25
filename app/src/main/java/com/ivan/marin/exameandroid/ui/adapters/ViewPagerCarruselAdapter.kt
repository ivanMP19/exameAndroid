package com.ivan.marin.exameandroid.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ivan.marin.exameandroid.ui.view.fragments.MapsFragment
import com.ivan.marin.exameandroid.ui.view.fragments.MovieContainerFragment
import com.ivan.marin.exameandroid.ui.view.fragments.SendImageFragment

class ViewPagerCarruselAdapter (fragmentActivity: FragmentActivity, size : Int, pageSelected : Int) : FragmentStateAdapter(fragmentActivity){
    private var pageSelected : Int? = null
    private var size : Int =  0

    init {
        this.pageSelected = pageSelected
        this.size = size
    }

    override fun getItemCount() = size

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment : Fragment
        when (position){
            0 -> fragment = MovieContainerFragment()
            1 -> fragment = MapsFragment()
            2 -> fragment = SendImageFragment()
        }
        return fragment
    }
}