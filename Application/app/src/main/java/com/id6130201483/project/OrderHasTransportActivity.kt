package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.id6130201483.project.adapter.EmpShowCusOrderAdapter
import com.id6130201483.project.api.OrderHasTransportAPI
import com.id6130201483.project.dataclass.Order
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.activity_order_has_transport.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderHasTransportActivity : AppCompatActivity() {
    var orderHasTransportList = arrayListOf<Order>()
    val api = OrderHasTransportAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_has_transport)

        val actionBar = supportActionBar
        actionBar!!.title = "รายการที่กำลังจัดส่ง"
        actionBar.setDisplayHomeAsUpEnabled(true)

        rev_cus_order.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callOrderHasTransport(CustomerStore.id!!)
        super.onResume()
    }

    private fun callOrderHasTransport(cid: Int) {
        orderHasTransportList.clear()

        api.selectCustomerOrderHasTransport(cid).enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        orderHasTransportList.add(
                            Order(
                                it.order_id.toString().toInt(),
                                it.order_date,
                                it.order_track,
                                it.cus_id.toString().toInt(),
                                it.transport_id.toString().toInt(),
                                it.status_id.toString().toInt()
                            )
                        )
                    }

                    rev_cus_order.adapter = EmpShowCusOrderAdapter(
                        orderHasTransportList,
                        this@OrderHasTransportActivity
                    )
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}