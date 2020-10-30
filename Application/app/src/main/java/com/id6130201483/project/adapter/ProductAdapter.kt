package com.id6130201483.project.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id6130201483.project.ProductActivity
import com.id6130201483.project.R
import com.id6130201483.project.dataclass.Product
import com.id6130201483.project.viewholder.ProductViewHolder

class ProductAdapter(val items: List<Product>, val context: Context) :
    RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item_layout, parent, false)
        val myHolder = ProductViewHolder(viewItem)

        viewItem.setOnClickListener {
            val pos = myHolder.adapterPosition
            val context = parent.context
            val item = items[pos]

            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("pid", item.product_id)
            context.startActivity(intent)
        }

        return myHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]

        Glide.with(context).load(item.product_image).into(holder.product_image)
        holder.product_name.text = item.product_name
        holder.product_price.text = "${item.product_price} บาท"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}