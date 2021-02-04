package com.sopherwang.mall.features.product_details.add_cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sopherwang.mall.features.product_details.R
import com.sopherwang.mall.libraries.network.models.ProductDetailsData

class AddCartBottomSheetFragment(private val productDetailsData: ProductDetailsData) :
    BottomSheetDialogFragment() {

    companion object {
        fun newInstance(productDetailsData: ProductDetailsData) =
            AddCartBottomSheetFragment(productDetailsData)
    }

    private lateinit var productImageView: ImageView
    private lateinit var productNameTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productAttributeList: RecyclerView

    private val addCartBottomSheetAttributeAdapter = AddCartBottomSheetAttributeAdapter()
    private val addCartBottomSheetViewModel: AddCartBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_cart, container, false).apply {
            productImageView = findViewById(R.id.fragment_add_cart_thumbnail)
            productNameTextView = findViewById(R.id.fragment_add_cart_name)
            productPriceTextView = findViewById(R.id.fragment_add_cart_price)
            productAttributeList = findViewById(R.id.fragment_add_cart_attribute_list)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAttributeList.adapter = addCartBottomSheetAttributeAdapter
        productAttributeList.layoutManager = LinearLayoutManager(context)

        productDetailsData.product?.let {
            Glide.with(this).load(it.pic).into(productImageView)
            productNameTextView.text = it.name
            productPriceTextView.text = getString(R.string.fragment_product_details_price, it.price)
        }
        productDetailsData.productAttributeList?.let {
            addCartBottomSheetViewModel.updateAttributeList(it)
            addCartBottomSheetAttributeAdapter.updateAttributes(addCartBottomSheetViewModel.getCustomerInputAttributeList())
        }
    }
}