package com.id6130201483.project.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.emp_product_item_layout.view.*

class EmpMainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val product_image = view.iv_product_image
    val product_name = view.tv_product_name
    val product_amount = view.tv_product_amount
}