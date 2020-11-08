package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.id6130201483.project.adapter.EmpOrderDetailAdapter
import com.id6130201483.project.adapter.OrderDetailAdapter
import com.id6130201483.project.api.EmpAPI
import com.id6130201483.project.api.ProfileFragmentAPI
import com.id6130201483.project.dataclass.CustomerOrder
import com.id6130201483.project.dataclass.Order
import com.id6130201483.project.dataclass.OrderDetailProduct
import com.id6130201483.project.dataclass.ProductInCart
import com.id6130201483.project.savedata.CustomerStore
import com.id6130201483.project.savedata.UserTypeStore
import kotlinx.android.synthetic.main.activity_emp_order_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpOrderDetailActivity : AppCompatActivity() {
    var oid: Int = 0
    var orderDetailList = arrayListOf<ProductInCart>()
    val api = EmpAPI.create()
    var orderTotal: Int = 0
    val cid: Int = CustomerStore.id!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_order_detail)

        val actionBar = supportActionBar
        actionBar!!.title = "รายละเอียดคำสั่งซื้อ"
        actionBar.setDisplayHomeAsUpEnabled(true)

        oid = intent.getIntExtra("oid", 0)

        rev_order_detail.layoutManager = LinearLayoutManager(applicationContext)

        // Is employee
        if (UserTypeStore.type == "emp") {
            // Hide customer button
            btn_getProduct.visibility = View.INVISIBLE

            btn_save.setOnClickListener {
                clickSave()
            }

            edt_order_track.addTextChangedListener {
                if (edt_order_track.text.isNotEmpty() && edt_order_track.text.isNotBlank()) {
                    btn_save.isEnabled = true
                } else {
                    btn_save.isEnabled = !btn_save.isEnabled
                }
            }
        } else {
            // Is customer
            // Hide employee button
            btn_save.visibility = View.INVISIBLE

            // Disable order track EditText
            edt_order_track.isEnabled = false

            btn_getProduct.isEnabled = false

            btn_getProduct.setOnClickListener {
                clickGetProduct()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callOrder(oid)
        callOrderDetail(oid)
        super.onResume()
    }

    private fun callOrder(oid: Int) {
        api.selectCustomerOrder(oid).enqueue(object : Callback<CustomerOrder> {
            override fun onResponse(call: Call<CustomerOrder>, response: Response<CustomerOrder>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    tv_order_id.text = "คำสั่งซื้อ: ${body?.order_id.toString()}"
                    tv_cus_name.text = "${body?.cus_fname} ${body?.cus_lname}"
                    tv_cus_address.text = body?.cus_address.toString()
                    tv_cus_tel.text = body?.cus_tel.toString()
                    tv_order_transport_name.text =
                        "${body?.transport_name}   +${body?.transport_cost} บาท"

                    orderTotal += body?.transport_cost.toString().toInt()


                    if (!body?.order_track.isNullOrEmpty()) {
                        edt_order_track.setText(body?.order_track)
                        btn_getProduct.isEnabled = true
                    } else {
                        btn_save.isEnabled = false
                    }

                    if (body?.status_id.toString().toInt() == 4) {
                        edt_order_track.isEnabled = false
                        btn_save.visibility = View.INVISIBLE
                        btn_getProduct.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<CustomerOrder>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun callOrderDetail(oid: Int) {
        orderDetailList.clear()

        api.selectOrderDetailProduct(oid).enqueue(object : Callback<List<OrderDetailProduct>> {
            override fun onResponse(
                call: Call<List<OrderDetailProduct>>,
                response: Response<List<OrderDetailProduct>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        orderDetailList.add(
                            ProductInCart(
                                it.product_id.toString().toInt(),
                                it.product_image,
                                it.product_name,
                                it.product_price.toString().toInt(),
                                it.order_detail_product_amount.toString().toInt(),
                                it.product_sum.toString().toInt()
                            )
                        )

                        orderTotal += it.product_sum.toString().toInt()
                    }

                    tv_order_total.text = "รวม $orderTotal บาท"

                    rev_order_detail.adapter =
                        EmpOrderDetailAdapter(orderDetailList, this@EmpOrderDetailActivity)
                }
            }

            override fun onFailure(call: Call<List<OrderDetailProduct>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun clickSave() {
        api.updateOrderTrack(oid, edt_order_track.text.toString(), 3)
            .enqueue(object : Callback<Order> {
                override fun onResponse(call: Call<Order>, response: Response<Order>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EmpOrderDetailActivity,
                            "อัปเดท Track number สำเร็จ",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        finish()
                    }
                }

                override fun onFailure(call: Call<Order>, t: Throwable) {
                    println("FAIL")
                }
            })
    }

    private fun clickGetProduct() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("ยืนยันการได้รับสินค้า")
            setMessage("คุณได้รับสินค้าแล้วหรือไม่")
            setPositiveButton("ใช่") { _, _ -> updateOrderStatus(oid, 4) }
            setNegativeButton("ไม่ใช่") { _, _ -> }
            show()
        }
    }

    private fun updateOrderStatus(oid: Int, status_id: Int) {
        ProfileFragmentAPI.create().updateOrderTrack(oid, status_id)
            .enqueue(object : Callback<Order> {
                override fun onResponse(call: Call<Order>, response: Response<Order>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EmpOrderDetailActivity,
                            "ขอบคุณที่สั่งซื้อสินค้าของเรา",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<Order>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}