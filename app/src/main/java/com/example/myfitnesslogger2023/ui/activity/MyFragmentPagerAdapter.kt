package com.example.myfitnesslogger2023.ui.activity

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyFragmentPagerAdapter(private val myContext: Context?, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm){

    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    fun addFragment(fragment : Fragment, title : String)
    {
        fragments.add(fragment);
        titles.add(title)
    }

    override fun getCount(): Int {
        return fragments.count();
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position);
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles.get(position)
    }
}