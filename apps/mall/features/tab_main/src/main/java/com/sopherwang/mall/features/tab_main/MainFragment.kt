package com.sopherwang.mall.features.tab_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sopherwang.mall.features.tab_main.advertise_banner.AdvertiseBannerAdapter
import com.sopherwang.mall.libraries.network.models.HomeContentData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val mainFragmentViewModel: MainFragmentViewModel by viewModels()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var advertiseBannerAdapter: AdvertiseBannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        tabLayout = view.findViewById(R.id.fragment_main_tablayout)
        viewPager2 = view.findViewById(R.id.fragment_main_advertise_banner)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        advertiseBannerAdapter = AdvertiseBannerAdapter(this)
        viewPager2.adapter = advertiseBannerAdapter
        TabLayoutMediator(tabLayout, viewPager2)
        { tab, position -> }.attach()

        mainFragmentViewModel.messageList.observe(
            viewLifecycleOwner,
            Observer { data: HomeContentData? ->
                data?.let {
                    it.advertiseList?.let { advertises ->
                        advertiseBannerAdapter.setAdvertiseList(advertises)
                    }
                }
            })
    }
}
