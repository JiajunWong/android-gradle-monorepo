package com.sopherwang.mall.features.product_details.add_cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sopherwang.libraries.network.common.models.Status
import com.sopherwang.mall.features.product_details.R
import com.sopherwang.mall.libraries.network.models.AddCartItemRequest
import com.sopherwang.mall.libraries.network.models.ProductDetailsData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
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
    private lateinit var minusButton: Button
    private lateinit var addButton: Button
    private lateinit var quantityTextView: TextView
    private lateinit var confirmButton: Button

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
            minusButton = findViewById(R.id.fragment_add_cart_quantity_minus)
            addButton = findViewById(R.id.fragment_add_cart_quantity_add)
            quantityTextView = findViewById(R.id.fragment_add_cart_quantity_number)
            confirmButton = findViewById(R.id.fragment_add_cart_button)
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

        minusButton.setOnClickListener {
            if (quantityTextView.text.toString().toInt() > 1) {
                quantityTextView.text = (quantityTextView.text.toString().toInt() - 1).toString()
            }
        }

        addButton.setOnClickListener {
            quantityTextView.text = (quantityTextView.text.toString().toInt() + 1).toString()
        }

        addCartBottomSheetViewModel.addCartItemResponseData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    Timber.tag(javaClass.simpleName).d("addCartItemResponseData loading.")
                }
                Status.SUCCESS -> {
                    Timber.tag(javaClass.simpleName).d("addCartItemResponseData success.")
                }
                Status.ERROR -> {
                    Timber.tag(javaClass.simpleName).e("addCartItemResponseData error = ${it.message}.")
                }
            }
        })
        confirmButton.setOnClickListener {
            productDetailsData.product?.let {
                val quantity = quantityTextView.text.toString().toInt()
                val addCartItemRequest = AddCartItemRequest(it.id, quantity, null)
                addCartBottomSheetViewModel.updateAddCartRequest(addCartItemRequest)
            }
        }
    }
}
