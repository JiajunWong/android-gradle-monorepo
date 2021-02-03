package com.sopherwang.libraries.data_layer.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopherwang.mall.libraries.network.models.Product

class ProductViewModel: ViewModel() {

    private val _productLiveData = MutableLiveData<Product>()

    val productLiveData:LiveData<Product> get() = _productLiveData

    fun updateProduct(product: Product) {
        _productLiveData.value = product
    }
}
