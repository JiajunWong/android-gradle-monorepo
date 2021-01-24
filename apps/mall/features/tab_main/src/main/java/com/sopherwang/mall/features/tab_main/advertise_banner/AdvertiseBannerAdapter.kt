package com.sopherwang.mall.features.tab_main.advertise_banner

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sopherwang.mall.libraries.network.models.Advertise

class AdvertiseBannerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val advertiseList: MutableList<Advertise> = mutableListOf()

    fun setAdvertiseList(advertiseList: List<Advertise>) {
        this.advertiseList.clear()
        this.advertiseList.addAll(advertiseList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = advertiseList.size

    override fun createFragment(position: Int) = AdvertiseBannerFragment(advertiseList[position])
}