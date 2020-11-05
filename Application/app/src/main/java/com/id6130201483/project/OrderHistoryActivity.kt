package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.id6130201483.project.adapter.EmpShowCusOrderAdapter
import com.id6130201483.project.api.OrderHasTransportAPI
import com.id6130201483.project.dataclass.Order
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.activity_order_has_transport.*
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.activity_order_history.rev_cus_order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderHistoryActivity : AppCompatActivity() {
    var orderLists = arrayListOf<Order>()
    val api = OrderHasTransportAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        val actionBar = supportActionBar
        actionBar!!.title = "ประวัติการสั่งซื้อ"
        actionBar.setDisplayHomeAsUpEnabled(true)

        rev_cus_order.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callOrderHistory(CustomerStore.id!!)
        super.onResume()
    }

    private fun callOrderHistory(cid: Int) {
        orderLists.clear()

        api.selectOrderHistory(cid).enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        orderLists.add(
                            Order(
                                it.order_id.toString().toInt(),
                                it.order_date,
                                it.order_track ?: "",
                                it.cus_id.toString().toInt(),
                                it.transport_id.toString().toInt(),
                                it.status_id.toString().toInt()
                            )
                        )
                    }

                    rev_cus_order.adapter =
                        EmpShowCusOrderAdapter(orderLists, this@OrderHistoryActivity)
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}