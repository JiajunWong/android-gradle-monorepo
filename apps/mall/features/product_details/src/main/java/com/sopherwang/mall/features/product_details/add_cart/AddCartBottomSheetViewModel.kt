package com.sopherwang.mall.features.product_details.add_cart

import androidx.lifecycle.ViewModel
import com.sopherwang.mall.libraries.network.models.ProductAttribute

class AddCartBottomSheetViewModel: ViewModel() {
    private val attributeList: MutableList<ProductAttribute> = mutableListOf()
    private val customerInputs: MutableMap<String, String> = mutableMapOf()

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
}