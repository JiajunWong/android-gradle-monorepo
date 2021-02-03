package com.sopherwang.mall.features.tab_main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sopherwang.libraries.data_layer.product.ProductViewModel
import com.sopherwang.libraries.network.common.models.Resource
import com.sopherwang.libraries.network.common.models.Status
import com.sopherwang.mall.features.tab_main.advertise_banner.AdvertiseBannerAdapter
import com.sopherwang.mall.features.tab_main.product_grid.ProductGridAdapter
import com.sopherwang.mall.libraries.network.models.HomeContentData
import com.sopherwang.mall.libraries.network.models.Product
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment(), ProductGridAdapter.OnProductItemClickListener {
    private val mainFragmentViewModel: MainFragmentViewModel by viewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var advertiseBannerAdapter: AdvertiseBannerAdapter
    private lateinit var newProductRecyclerView: RecyclerView
    private lateinit var newProductAdapter: ProductGridAdapter
    private lateinit var popularProductRecyclerView: RecyclerView
    private lateinit var popularProductAdapter: ProductGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        tabLayout = view.findViewById(R.id.fragment_main_tablayout)
        viewPager2 = view.findViewById(R.id.fragment_main_advertise_banner)
        newProductRecyclerView = view.findViewById(R.id.fragment_main_new_product_list)
        popularProductRecyclerView = view.findViewById(R.id.fragment_main_popular_product_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdvertiseBanner()
        context?.let {
            setupNewProductList(it)
            setupPopularProductList(it)
        }

        mainFragmentViewModel.messageList.observe(
            viewLifecycleOwner,
            Observer { resource: Resource<HomeContentData> ->
                when (resource.status) {
                    Status.LOADING -> {
                        // TODO: show loading
                        Timber.tag(javaClass.simpleName).d("Fetch content loading")
                    }
                    Status.SUCCESS -> {
                        Timber.tag(javaClass.simpleName).d("Fetch content success")
                        resource.data?.advertiseList?.let { advertises ->
                            advertiseBannerAdapter.setAdvertiseList(advertises)
                        }
                        resource.data?.newProductList?.let { newProducts ->
                            newProductAdapter.addProducts(newProducts)
                        }
                        resource.data?.hotProductList?.let { popularProducts ->
                            popularProductAdapter.addProducts(popularProducts)
                        }
                    }
                    Status.ERROR -> {
                        // TODO: show error
                        Timber.tag(javaClass.simpleName).d("Fetch content error = ${resource.message}")
                    }
                }
            })
    }

    private fun setupAdvertiseBanner() {
        advertiseBannerAdapter = AdvertiseBannerAdapter(this)
        viewPager2.adapter = advertiseBannerAdapter
        TabLayoutMediator(tabLayout, viewPager2)
        { tab, position -> }.attach()
    }

    private fun setupNewProductList(context: Context) {
        newProductAdapter = ProductGridAdapter(context, this)
        newProductRecyclerView.adapter = newProductAdapter
        val gridLayoutManager = GridLayoutManager(context, 2)
        newProductRecyclerView.layoutManager = gridLayoutManager
        newProductRecyclerView.isNestedScrollingEnabled = true
    }

    private fun setupPopularProductList(context: Context) {
        popularProductAdapter = ProductGridAdapter(context, this)
        popularProductRecyclerView.adapter = popularProductAdapter
        val gridLayoutManager = GridLayoutManager(context, 2)
        popularProductRecyclerView.layoutManager = gridLayoutManager
        popularProductRecyclerView.isNestedScrollingEnabled = true
    }

    override fun onClicked(product: Product) {
        Timber.tag(javaClass.simpleName).d("Product ${product.name} has clicked")
        productViewModel.updateProduct(product)
    }
}
