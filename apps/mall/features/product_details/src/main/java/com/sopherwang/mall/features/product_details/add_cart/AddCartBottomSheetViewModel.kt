package com.sopherwang.mall.features.product_details.add_cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sopherwang.libraries.network.common.models.Resource
import com.sopherwang.mall.libraries.network.models.AddCartItemRequest
import com.sopherwang.mall.libraries.network.models.ProductAttribute
import com.sopherwang.mall.libraries.repositories.CartRepository

class AddCartBottomSheetViewModel @ViewModelInject constructor(private val cartRepository: CartRepository): ViewModel() {
    private val attributeList: MutableList<ProductAttribute> = mutableListOf()
    private val customerInputs: MutableMap<String, String> = mutableMapOf()
    private var productId: Int = 0

    private val addCartItemRequestData = MutableLiveData<AddCartItemRequest>()
    val addCartItemResponseData: LiveData<Resource<Int>> = addCartItemRequestData.switchMap {
        cartRepository.addCartItem(it)
    }

    fun updateAttributeList(attributeList: List<ProductAttribute>) {
        this.attributeList.clear()
        this.attributeList.addAll(attributeList)
    }

    fun getCustomerInputAttributeList(): List<ProductAttribute> {
        return attributeList.filter { it.selectType > 0 && it.inputType == 1}
    }

    fun updateCustomerInput(key: String, value: String) {
        customerInputs[key] = value
    }

    fun getCustomerInputs(): Map<String, String> {
        return customerInputs
    }

    fun updateAddCartRequest(addCartItemRequest: AddCartItemRequest) {
        addCartItemRequestData.value = addCartItemRequest
    }

    fun updateProductId(id: Int) {
        this.productId = id
    }

    fun getProductId(): Int{
        return productId
    }
}
