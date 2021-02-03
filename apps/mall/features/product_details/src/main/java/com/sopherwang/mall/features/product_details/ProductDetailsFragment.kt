package com.sopherwang.mall.features.product_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sopherwang.libraries.network.common.models.Status
import timber.log.Timber

class ProductDetailsFragment : Fragment() {
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()

    private lateinit var productImage: ImageView
    private lateinit var priceTextView: TextView
    private lateinit var originalPriceTextView: TextView
    private lateinit var productNameTextView: TextView
    private lateinit var attributeRecyclerView: RecyclerView
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
        brandLogoImageView = view.findViewById(R.id.fragment_product_details_brand_logo)
        brandNameTextView = view.findViewById(R.id.fragment_product_details_brand_name)
        brandDescriptionTextView =
            view.findViewById(R.id.fragment_product_details_brand_description)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDetailsViewModel.productDetailsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Status.LOADING -> {
                        Timber.tag(javaClass.simpleName).d("productDetailsLiveData loading.")
                    }
                    Status.SUCCESS -> {
                        Timber.tag(javaClass.simpleName).d("productDetailsLiveData success.")
                    }
                    Status.ERROR -> {
                        Timber.tag(javaClass.simpleName)
                            .d("productDetailsLiveData error = ${it.message}.")
                    }
                }
            })
    }
}
