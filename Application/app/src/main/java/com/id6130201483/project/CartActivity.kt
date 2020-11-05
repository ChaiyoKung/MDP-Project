package com.id6130201483.project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.id6130201483.project.adapter.OrderDetailAdapter
import com.id6130201483.project.api.CartActivityAPI
import com.id6130201483.project.dataclass.*
import com.id6130201483.project.parcelable.CustomerParcel
import com.id6130201483.project.savedata.CustomerStore
import com.id6130201483.project.savedata.OrderDetailStore
import kotlinx.android.synthetic.main.activity_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CartActivity : AppCompatActivity() {
    var cusParcel: CustomerParcel? = null
    var cid: Int? = null
    var orderDetailList = arrayListOf<ProductInCart>()
    val cartAPI = CartActivityAPI.create()
    var cartTotal: Int = 0
    var transCost: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val actionBar = supportActionBar
        actionBar!!.title = "รถเข็น"
        actionBar.setDisplayHomeAsUpEnabled(true)

        cusParcel = intent.getParcelableExtra("cusData")
        cid = CustomerStore.id

        rev_product_list.layoutManager = LinearLayoutManager(applicationContext)

        btn_pay.setOnClickListener {
            clickPay()
        }

        layout_transport.setOnClickListener {
            clickTransport()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callNowOrderID()
        callOrderDetail()
        callCustomerOrderTransport()
        super.onResume()
    }

    private fun getMyDateNow(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return current.format(formatter)
    }

    private fun callOrderDetail() {
        orderDetailList.clear()
        cartTotal = 0

        if (cid != null) {
            cartAPI.selectOrderDetail(cid!!).enqueue(object : Callback<List<ProductInCart>> {
                override fun onResponse(
                    call: Call<List<ProductInCart>>,
                    response: Response<List<ProductInCart>>
                ) {
                    if (response.isSuccessful) {
                        if(response.body()?.size == 0) btn_pay.isEnabled = false
                        response.body()?.forEach {
                            orderDetailList.add(
                                ProductInCart(
                                    it.product_id,
                                    it.product_image,
                                    it.product_name,
                                    it.product_price,
                                    it.order_detail_product_amount,
                                    it.product_total
                                )
                            )

                            cartTotal += it.product_total
                        }

                        rev_product_list.adapter =
                            OrderDetailAdapter(orderDetailList, applicationContext)

                        tv_total.text = "รวม ${cartTotal + transCost} บาท"
                    }
                }

                override fun onFailure(call: Call<List<ProductInCart>>, t: Throwable) {
                    Toast.makeText(this@CartActivity, "ไม่พบรายการสินค้า", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            })
        }
    }

    private fun callNowOrderID() {
        if (cid != null) {
            cartAPI.getNowOrderID(cid!!).enqueue(object : Callback<NowOrderID> {
                override fun onResponse(call: Call<NowOrderID>, response: Response<NowOrderID>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        OrderDetailStore.oid = body?.order_id.toString().toInt()
                    }
                }

                override fun onFailure(call: Call<NowOrderID>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    private fun callCustomerOrderTransport() {
        if (cid != null) {
            cartAPI.getCustomerOrderTransport(cid!!)
                .enqueue(object : Callback<CustomerOrderTransport> {
                    override fun onResponse(
                        call: Call<CustomerOrderTransport>,
                        response: Response<CustomerOrderTransport>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()

                            tv_address.text = body?.cus_address
                            tv_transport_name.text = body?.transport_name
                            tv_transport_price.text = "${body?.transport_cost} บาท"

                            transCost = body?.transport_cost.toString().toInt()

                            tv_total.text =
                                "รวม ${cartTotal + transCost} บาท"
                        }
                    }

                    override fun onFailure(call: Call<CustomerOrderTransport>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
        }
    }

    private fun clickPay() {
        cartAPI.changeOrderStatus(cid!!, OrderDetailStore.oid, getMyDateNow(), 2)
            .enqueue(object : Callback<OrderStatus> {
                override fun onResponse(call: Call<OrderStatus>, response: Response<OrderStatus>) {
                    if (response.isSuccessful) {
                        createNewOrder(cid!!, getMyDateNow())
                    }
                }

                override fun onFailure(call: Call<OrderStatus>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun createNewOrder(cid: Int, oDate: String) {
        cartAPI.createNewOrder(cid, oDate).enqueue(object : Callback<CreateNewOrder> {
            override fun onResponse(
                call: Call<CreateNewOrder>,
                response: Response<CreateNewOrder>
            ) {
                if (response.isSuccessful) {
                    finish()
                }
            }

            override fun onFailure(call: Call<CreateNewOrder>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun clickTransport() {
        val intent = Intent(this, TransportActivity::class.java)
        intent.putExtra("total", cartTotal)
        startActivity(intent)
    }
}