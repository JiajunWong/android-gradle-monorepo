package com.sopherwang.mall.features.tab_main.product_grid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sopherwang.mall.features.tab_main.R
import com.sopherwang.mall.libraries.network.models.Product
import java.math.BigDecimal

class ProductGridAdapter(private val context: Context, private val onProductItemClickListener: OnProductItemClickListener) :
    RecyclerView.Adapter<ProductGridAdapter.ViewHolder>() {
    private val productList: MutableList<Product> = mutableListOf()

    fun addProducts(productList: List<Product>) {
        this.productList.clear()
        this.productList.addAll(productList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_item_main_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        with(holder) {
            nameTextView.text = product.name
            priceTextView.text = getPriceLabel(product.price)
            soldTextView.text =
                context.getString(R.string.main_page_list_item_has_sold, product.sale)
            Glide.with(context).load(product.pic).into(imageView)

            holder.itemView.setOnClickListener {
                onProductItemClickListener.onClicked(product)
            }
        }
    }

    override fun getItemCount() = productList.size

    private fun getPriceLabel(price: Int): String {
        val priceDecimal = BigDecimal(price)
        val divider = BigDecimal(100)
        val realPrice = priceDecimal.divide(divider)
        return context.getString(R.string.main_page_list_item_price_label, realPrice)
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val imageView: ImageView = item.findViewById(R.id.list_item_main_product_image)
        val nameTextView: TextView = item.findViewById(R.id.list_item_main_product_name)
        val priceTextView: TextView = item.findViewById(R.id.list_item_main_product_price)
        val soldTextView: TextView = item.findViewById(R.id.list_item_main_product_sold)
    }

    interface OnProductItemClickListener {
        fun onClicked(product: Product);
    }
}
