package com.id6130201483.project.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.id6130201483.project.EmpOrderDetailActivity
import com.id6130201483.project.R
import com.id6130201483.project.dataclass.Order
import kotlinx.android.synthetic.main.emp_cus_order_item_layout.view.*


class EmpShowCusOrderAdapter(val items: List<Order>, val context: Context) :
    RecyclerView.Adapter<EmpShowCusOrderAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val order_id = view.tv_order_id
        val order_track = view.tv_order_track
        val order_status = view.iv_order_status
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.emp_cus_order_item_layout, parent, false)
        val holder = ViewHolder(itemView)

        itemView.setOnClickListener {
            val pos = holder.adapterPosition
            val context = parent.context
            val item = items[pos]

            val intent = Intent(context, EmpOrderDetailActivity::class.java)
            intent.putExtra("oid", item.order_id)
            context.startActivity(intent)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.order_id.text = "คำสั่งซื้อ: ${item.order_id}"
        holder.order_track.text = "Track: ${item.order_track}"
        when (item.status_id.toString().toInt()) {
            // Status id = 3: กำลังจัดส่ง
            3 -> holder.order_status.setImageDrawable(context.getDrawable(R.drawable.delivery_outline))
            // Status id = 4: จัดส่งเรียบร้อย
            4 -> holder.order_status.setImageDrawable(context.getDrawable(R.drawable.check))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}