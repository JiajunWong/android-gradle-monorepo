package com.sopherwang.mall.features.product_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sopherwang.mall.libraries.network.models.ProductAttribute

class ProductDetailsAttributeAdapter :
    RecyclerView.Adapter<ProductDetailsAttributeAdapter.ViewHolder>() {

    private val attributeList = mutableListOf<ProductAttribute>()

    fun updateAttributes(attributeList: List<ProductAttribute>) {
        this.attributeList.clear()
        this.attributeList.addAll(attributeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product_details_attribute, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productAttribute = attributeList[position]
        holder.nameTextView.text = productAttribute.name
        holder.valueTextView.text = productAttribute.inputList
    }

    override fun getItemCount() = attributeList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView =
            view.findViewById(R.id.list_item_product_details_attribute_name)
        val valueTextView: TextView =
            view.findViewById(R.id.list_item_product_details_attribute_value)
    }
}
