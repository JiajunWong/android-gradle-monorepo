package com.sopherwang.mall.features.product_details.add_cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sopherwang.mall.features.product_details.R
import com.sopherwang.mall.libraries.network.models.ProductAttribute

class AddCartBottomSheetAttributeAdapter() :
    RecyclerView.Adapter<AddCartBottomSheetAttributeAdapter.ViewHolder>() {
    private val attributeList: MutableList<ProductAttribute> = mutableListOf()
    private val attributeMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    fun updateAttributes(attributeList: List<ProductAttribute>) {
        this.attributeList.clear()
        this.attributeList.addAll(attributeList)

        attributeMap.clear()
        attributeList.forEach {
            if (attributeMap.containsKey(it.name)) {
                attributeMap[it.name]?.addAll(it.inputList.split(","))
            } else {
                attributeMap[it.name] = it.inputList.split(",").toMutableList()
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_add_cart_attribute, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productAttribute = attributeList[position]
        holder.attributeNameTextView.text = productAttribute.name

        holder.attributesContainer.isSelectionRequired = true
        holder.attributesContainer.isSingleSelection = productAttribute.selectType == 1
        holder.attributesContainer.removeAllViews()
        attributeMap[productAttribute.name]?.forEach {
            val chip = LayoutInflater.from(holder.attributesContainer.context)
                .inflate(R.layout.chip_attribute, holder.attributesContainer, false) as Chip
            chip.text = it
            holder.attributesContainer.addView(chip)
        }
    }

    override fun getItemCount() = attributeMap.size

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val attributeNameTextView: TextView =
            item.findViewById(R.id.list_item_add_cart_attribute_name)
        val attributesContainer: ChipGroup =
            item.findViewById(R.id.list_item_add_cart_attribute_container)
    }
}
