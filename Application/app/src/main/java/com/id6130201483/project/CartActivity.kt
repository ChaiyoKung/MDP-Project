package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.id6130201483.project.adapter.OrderDetailAdapter
import com.id6130201483.project.api.CartActivityAPI
import com.id6130201483.project.dataclass.ProductInCart
import com.id6130201483.project.parcelable.CustomerParcel
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.activity_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {
    var cusParcel: CustomerParcel? = null
    var cid: Int? = null
    var orderDetailList = arrayListOf<ProductInCart>()
    val cartAPI = CartActivityAPI.create()
    var cartTotal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val actionBar = supportActionBar
        actionBar!!.title = "รถเข็น"
        actionBar.setDisplayHomeAsUpEnabled(true)

        cusParcel = intent.getParcelableExtra("cusData")
        cid = CustomerStore.id

        rev_product_list.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callOrderDetail()
        super.onResume()
    }

    private fun callOrderDetail() {
        orderDetailList.clear()

        if (cid != null) {
            cartAPI.selectOrderDetail(cid!!).enqueue(object : Callback<List<ProductInCart>> {
                override fun onResponse(
                    call: Call<List<ProductInCart>>,
                    response: Response<List<ProductInCart>>
                ) {
                    if (response.isSuccessful) {
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

                        tv_total.text = "รวม $cartTotal บาท"
                    }
                }

                override fun onFailure(call: Call<List<ProductInCart>>, t: Throwable) {
                    Toast.makeText(this@CartActivity, "ไม่พบรายการสินค้า", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }

            })
        }
    }
}