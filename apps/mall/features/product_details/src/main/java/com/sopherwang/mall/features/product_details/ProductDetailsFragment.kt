package com.sopherwang.mall.features.product_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.sopherwang.libraries.data_layer.product.ProductViewModel
import com.sopherwang.libraries.network.common.models.Status
import com.sopherwang.mall.libraries.network.models.Brand
import com.sopherwang.mall.libraries.network.models.Product
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    companion object {
        fun newInstance() = ProductDetailsFragment()
    }

    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    private lateinit var productImage: ImageView
    private lateinit var priceTextView: TextView
    private lateinit var originalPriceTextView: TextView
    private lateinit var productNameTextView: TextView
    private lateinit var attributeRecyclerView: RecyclerView
    private lateinit var brandcontainer: MaterialCardView
    private lateinit var brandLogoImageView: ImageView
    private lateinit var brandNameTextView: TextView
    private lateinit var brandDescriptionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        productImage = view.findViewById(R.id.fragment_product_details_image)
        priceTextView = view.findViewById(R.id.fragment_product_details_price)
        originalPriceTextView = view.findViewById(R.id.fragment_product_details_original_price)
        productNameTextView = view.findViewById(R.id.fragment_product_details_name)
        attributeRecyclerView =
            view.findViewById(R.id.fragment_product_details_attribute_recycler_view)
        brandcontainer = view.findViewById(R.id.fragment_product_details_brand_container)
        brandLogoImageView = view.findViewById(R.id.fragment_product_details_brand_logo)
        brandNameTextView = view.findViewById(R.id.fragment_product_details_brand_name)
        brandDescriptionTextView =
            view.findViewById(R.id.fragment_product_details_brand_description)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeProductClickLiveData()
        subscribeRequestDetailsLiveData()
    }

    private fun subscribeProductClickLiveData() {
        productViewModel.productLiveData.observe(viewLifecycleOwner, Observer { product ->
            Timber.tag(javaClass.simpleName).d("Product ${product.name} has clicked")
            productDetailsViewModel.updateProductId(product.id)
            updateViewByProduct(product)
        })
    }

    private fun subscribeRequestDetailsLiveData() {
        productDetailsViewModel.productDetailsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Status.LOADING -> {
                        Timber.tag(javaClass.simpleName).d("productDetailsLiveData loading.")
                    }
                    Status.SUCCESS -> {
                        Timber.tag(javaClass.simpleName).d("productDetailsLiveData success.")
                        it.data?.product?.let { product ->
                            updateViewByProduct(product)
                        }
                        it.data?.brand?.let { brand ->
                            updateViewByBrand(brand)
                        }
                    }
                    Status.ERROR -> {
                        Timber.tag(javaClass.simpleName)
                            .d("productDetailsLiveData error = ${it.message}.")
                    }
                }
            })
    }

    private fun updateViewByProduct(product: Product) {
        Glide.with(this).load(product.pic).into(productImage)
        priceTextView.text = product.price.toString()
        originalPriceTextView.text = getString(R.string.fragment_product_original_price, product.originalPrice)
        productNameTextView.text = product.name
    }

    private fun updateViewByBrand(brand: Brand) {
        brandcontainer.visibility = View.VISIBLE
        Glide.with(this).load(brand.logo).into(brandLogoImageView)
        brandNameTextView.text = brand.name
        brandDescriptionTextView.text = brand.brandStory
    }
}
