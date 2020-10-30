package com.id6130201483.project.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_item_layout.view.*

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val product_image = view.product_image
    val product_name = view.product_name
    val product_price = view.product_price
}