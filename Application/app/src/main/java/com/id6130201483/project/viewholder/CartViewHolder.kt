package com.id6130201483.project.viewholder

import android.app.Activity
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.id6130201483.project.CartActivity
import com.id6130201483.project.api.CartActivityAPI
import com.id6130201483.project.dataclass.OrderDetail
import com.id6130201483.project.dataclass.ProductAmountUpdate
import com.id6130201483.project.savedata.OrderDetailStore
import kotlinx.android.synthetic.main.cart_item_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    val order_detail_image = view.iv_order_detail_image
    val order_detail_name = view.tv_order_detail_name
    val order_detail_price = view.tv_order_detail_price
    val order_detail_amount = view.tv_order_detail_amount
    val order_detail_total = view.tv_order_detail_total

    val menu = view.setOnCreateContextMenuListener(this)

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val delete = menu?.add(0, 1, 1, "Delete")
        delete?.setOnMenuItemClickListener(clickMenu)
    }

    private val clickMenu = MenuItem.OnMenuItemClickListener { item ->
        when (item.itemId) {
            1 -> {
                deleteItemInOrderDetail(OrderDetailStore.oid, OrderDetailStore.pid)
            }
        }
        true
    }

    private fun deleteItemInOrderDetail(oid: Int, pid: Int) {
        CartActivityAPI.create().deleteItemInOrderDetail(oid, pid)
            .enqueue(object : Callback<OrderDetail> {
                override fun onResponse(call: Call<OrderDetail>, response: Response<OrderDetail>) {
                    if (response.isSuccessful) {
                        returnProductAmount(pid, OrderDetailStore.oamt)
                    }
                }

                override fun onFailure(call: Call<OrderDetail>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun returnProductAmount(pid: Int, oamt: Int) {
        CartActivityAPI.create().returnProductAmount(pid, oamt)
            .enqueue(object : Callback<ProductAmountUpdate> {
                override fun onResponse(
                    call: Call<ProductAmountUpdate>,
                    response: Response<ProductAmountUpdate>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(itemView.context, "ลบรายการสำเร็จ", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ProductAmountUpdate>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}