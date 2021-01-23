package com.sopherwang.mall.app_root

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sopherwang.mall.features.tab_category.CategoryFragment
import com.sopherwang.mall.features.tab_main.MainFragment
import com.sopherwang.mall.features.tab_me.MeFragment
import com.sopherwang.mall.features.tab_subject.SubjectFragment

class MainPageViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    companion object {
        private const val FRAGMENT_SIZE = 4
    }

    override fun getItemCount() = FRAGMENT_SIZE

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MainFragment()
            1 -> CategoryFragment()
            2 -> SubjectFragment()
            3 -> MeFragment()
            else -> throw IndexOutOfBoundsException("Exceed the size $FRAGMENT_SIZE")
        }
}
