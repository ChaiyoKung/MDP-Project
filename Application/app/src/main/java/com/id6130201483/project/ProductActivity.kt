package com.id6130201483.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.id6130201483.project.api.ProductActivityAPI
import com.id6130201483.project.dataclass.OrderDetail
import com.id6130201483.project.dataclass.OrderIDForCustomer
import com.id6130201483.project.dataclass.Product
import com.id6130201483.project.dataclass.ProductAmountUpdate
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.activity_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    val productAPI = ProductActivityAPI.create()
    var image: String? = ""
    var currentProductID: Int? = null
    var currentAmount: Int? = null
    var currentOrderID: Int? = null
    var pid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val actionBar = supportActionBar
        actionBar!!.title = "สินค้า"
        actionBar.setDisplayHomeAsUpEnabled(true)

        pid = intent.getIntExtra("pid", 0)

        callOrderidForCustomer(CustomerStore.id!!)

        iv_product_image.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("imageURL", image)
            startActivity(intent)
        }

        btn_plus.setOnClickListener {
            clickPlus()
        }

        btn_minus.setOnClickListener {
            clickMinus()
        }

        btn_addToCart.setOnClickListener {
            if (currentOrderID != null) {
                checkProductInCart(
                    currentOrderID!!,
                    currentProductID!!,
                    tv_amount.text.toString().toInt()
                )
            }
        }
    }

    override fun onResume() {
        callProduct(pid)
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                clickCart()
                true
            }
            R.id.menu_order_has_transport -> {
                clickOrderHasTransport()
                true
            }
            R.id.menu_history -> {
                clickHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun callProduct(pid: Int) {
        productAPI.selectProduct(pid).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val item = response.body()

                    currentProductID = item?.product_id
                    currentAmount = item?.product_amount

                    image = item?.product_image
                    Glide.with(applicationContext).load(image).into(iv_product_image)
                    tv_product_name.text = item?.product_name
                    tv_product_detail.text = item?.product_detail
                    tv_product_price.text = "${item?.product_price} บาท"
                    tv_product_amount.text = "/${item?.product_amount}"
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(this@ProductActivity, "ไม่พบสินค้านี้", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun callOrderidForCustomer(cid: Int) {
        productAPI.selectOrderidForCustomer(cid).enqueue(object : Callback<OrderIDForCustomer> {
            override fun onResponse(
                call: Call<OrderIDForCustomer>,
                response: Response<OrderIDForCustomer>
            ) {
                if (response.isSuccessful) {
                    currentOrderID = response.body()?.order_id
                }
            }

            override fun onFailure(call: Call<OrderIDForCustomer>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    private fun clickCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun clickOrderHasTransport() {
        val intent = Intent(this, OrderHasTransportActivity::class.java)
        startActivity(intent)
    }

    private fun clickHistory() {
        val intent = Intent(this, OrderHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun clickPlus() {
        changeAmount("+")
    }

    private fun clickMinus() {
        changeAmount("-")
    }

    private fun changeAmount(type: String) {
        var value = tv_amount.text.toString().toInt()
        when (type) {
            "+" -> {
                if (value < currentAmount!!) value += 1
            }
            "-" -> {
                if (value > 1) value -= 1
            }
        }
        tv_amount.text = value.toString()
    }

    private fun checkProductInCart(oid: Int, pid: Int, pamt: Int) {
        productAPI.updateProductInOrderDetail(oid, pid, pamt)
            .enqueue(object : Callback<OrderDetail> {
                override fun onResponse(call: Call<OrderDetail>, response: Response<OrderDetail>) {
                    if (response.isSuccessful) {
                        updateProductAmount(pid, pamt, "อัปเดทสินค้าในตะกร้าสำเร็จ")
                    } else {
                        addToCart(oid, pid, pamt)
                    }
                }

                override fun onFailure(call: Call<OrderDetail>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun addToCart(oid: Int, pid: Int, pamt: Int) {
        productAPI.insertOrderDetail(oid, pid, pamt).enqueue(object : Callback<OrderDetail> {
            override fun onResponse(call: Call<OrderDetail>, response: Response<OrderDetail>) {
                if (response.isSuccessful) {
                    updateProductAmount(pid, pamt, "เพิ่มลงในตะกร้าสำเร็จ")
                }
            }

            override fun onFailure(call: Call<OrderDetail>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun updateProductAmount(pid: Int, numberForCal: Int, toastSuccessText: String) {
        val newAmount = currentAmount!! + (numberForCal * -1)
        productAPI.updateProductAmount(pid, newAmount)
            .enqueue(object : Callback<ProductAmountUpdate> {
                override fun onResponse(
                    call: Call<ProductAmountUpdate>,
                    response: Response<ProductAmountUpdate>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ProductActivity, toastSuccessText, Toast.LENGTH_SHORT)
                            .show()
                        this@ProductActivity.recreate()
                    }
                }

                override fun onFailure(call: Call<ProductAmountUpdate>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}