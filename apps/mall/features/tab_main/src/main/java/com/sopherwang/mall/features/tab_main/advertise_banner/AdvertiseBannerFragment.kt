package com.sopherwang.mall.features.tab_main.advertise_banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sopherwang.mall.features.tab_main.R
import com.sopherwang.mall.libraries.network.models.Advertise

class AdvertiseBannerFragment(private val advertise: Advertise) : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_advertise_banner, container, false)
        imageView = view.findViewById(R.id.fragment_advertise_banner_image)
        titleView = view.findViewById(R.id.fragment_advertise_banner_name)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView.text = advertise.name

        context?.let {
            Glide.with(it).load(advertise.pic).into(imageView)
        }
    }
}