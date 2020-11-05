package com.id6130201483.project.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id6130201483.project.EmpProductActivity
import com.id6130201483.project.R
import com.id6130201483.project.dataclass.Product
import com.id6130201483.project.viewholder.EmpMainViewHolder

class EmpMainAdapter(val items: List<Product>, val context: Context) :
    RecyclerView.Adapter<EmpMainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpMainViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.emp_product_item_layout, parent, false)
        val myHolder = EmpMainViewHolder(itemView)

        itemView.setOnClickListener {
            val pos = myHolder.adapterPosition
            val context = parent.context
            val item = items[pos]

            val intent = Intent(context, EmpProductActivity::class.java)
            intent.putExtra("pid", item.product_id)
            context.startActivity(intent)
        }

        return myHolder
    }

    override fun onBindViewHolder(holder: EmpMainViewHolder, position: Int) {
        val item = items[position]

        Glide.with(context).load(item.product_image).into(holder.product_image)
        holder.product_name.text = item.product_name
        holder.product_amount.text = "เหลือ ${item.product_amount}"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}