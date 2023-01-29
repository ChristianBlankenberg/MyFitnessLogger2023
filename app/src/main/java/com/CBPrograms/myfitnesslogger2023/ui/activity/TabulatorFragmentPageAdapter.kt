package com.CBPrograms.myfitnesslogger2023.ui.activity

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabulatorFragmentPageAdapter(private val myContext: Context?, fm: FragmentManager) : FragmentPagerAdapter(fm){

    private val fragments = ArrayList<TabulatorChildFragment>()

    fun addFragment(fragment : TabulatorChildFragment, parentContext: Context?)
    {
        fragment.setParentContext(parentContext)
        fragments.add(fragment);
    }

    override fun getCount(): Int {
        return fragments.count();
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position);
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments.get(position).Title.toString()
    }
}