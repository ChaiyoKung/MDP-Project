package com.id6130201483.project.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cart_item_layout.view.*

class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val order_detail_image = view.iv_order_detail_image
    val order_detail_name = view.tv_order_detail_name
    val order_detail_price = view.tv_order_detail_price
    val order_detail_amount = view.tv_order_detail_amount
    val order_detail_total = view.tv_order_detail_total
}