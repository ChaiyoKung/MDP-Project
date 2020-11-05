package com.id6130201483.project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id6130201483.project.R
import com.id6130201483.project.dataclass.ProductInCart
import com.id6130201483.project.viewholder.CartViewHolder

class EmpOrderDetailAdapter(val items: List<ProductInCart>, val context: Context) :
    RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false)
        val myHolder = CartViewHolder(viewItem)

        return myHolder
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        Glide.with(context).load(item.product_image).into(holder.order_detail_image)
        holder.order_detail_name.text = item.product_name
        holder.order_detail_price.text = item.product_price.toString()
        holder.order_detail_amount.text = "x ${item.order_detail_product_amount}"
        holder.order_detail_total.text = item.product_total.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}