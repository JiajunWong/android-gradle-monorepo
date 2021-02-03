package com.sopherwang.mall.features.product_details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sopherwang.libraries.network.common.models.Resource
import com.sopherwang.mall.libraries.network.models.ProductDetailsData
import com.sopherwang.mall.libraries.repositories.ProductDetailsRepository

class ProductDetailsViewModel @ViewModelInject constructor(private val productDetailsRepository: ProductDetailsRepository) :
    ViewModel() {

    private val productIdLiveData = MutableLiveData<Int>()

    val productDetailsLiveData: LiveData<Resource<ProductDetailsData>> =
        productIdLiveData.switchMap {
            productDetailsRepository.getProductDetails(it)
        }

    fun updateProductId(id: Int) {
        productIdLiveData.value = id
    }
}
