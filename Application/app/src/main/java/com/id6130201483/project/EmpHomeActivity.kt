package com.id6130201483.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.id6130201483.project.adapter.EmpMainAdapter
import com.id6130201483.project.api.EmpAPI
import com.id6130201483.project.dataclass.Product
import kotlinx.android.synthetic.main.activity_emp_home.*
import kotlinx.android.synthetic.main.add_product_dialog_layout.*
import kotlinx.android.synthetic.main.fragment_home.rev_product_list
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpHomeActivity : AppCompatActivity() {
    val empAPI = EmpAPI.create()
    val productLists = arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_home)

        val actionBar = supportActionBar
        actionBar!!.title = "รายการสินค้า"

        rev_product_list.layoutManager = LinearLayoutManager(applicationContext)

        btn_search.setOnClickListener {
            clickSearch(edt_search.text.toString())
        }

        edt_search.addTextChangedListener {
            clickSearch(edt_search.text.toString())
        }

        btn_addProduct.setOnClickListener {
            clickAddProduct()
        }
    }

    override fun onResume() {
        callProduct()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.emp_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_showCusOrder -> {
                goToShowCusOrderActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToShowCusOrderActivity() {
        val intent = Intent(this, EmpShowCusOrderActivity::class.java)
        startActivity(intent)
    }

    private fun callProduct() {
        productLists.clear()

        empAPI.selectAllProduct().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        productLists.add(
                            Product(
                                it.product_id,
                                it.product_name,
                                it.product_detail,
                                it.product_amount,
                                it.product_price,
                                it.product_image
                            )
                        )
                    }

                    rev_product_list.adapter = EmpMainAdapter(productLists, this@EmpHomeActivity)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun clickSearch(pn: String) {
        productLists.clear()

        empAPI.searchProduct("%$pn%").enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        productLists.add(
                            Product(
                                it.product_id,
                                it.product_name,
                                it.product_detail,
                                it.product_amount,
                                it.product_price,
                                it.product_image
                            )
                        )
                    }

                    rev_product_list.adapter = EmpMainAdapter(productLists, this@EmpHomeActivity)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun clickAddProduct() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_product_dialog_layout, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val alertDialog = builder.show()

        alertDialog.dialog_btn_add.setOnClickListener {
            insertProduct(
                alertDialog.dialog_product_name.text.toString(),
                alertDialog.dialog_product_detail.text.toString(),
                alertDialog.dialog_product_amount.text.toString().toInt(),
                alertDialog.dialog_product_price.text.toString().toInt(),
                alertDialog.dialog_product_image.text.toString()
            )
            alertDialog.dismiss()
            callProduct()
        }

        alertDialog.dialog_product_image.addTextChangedListener {
            Glide.with(alertDialog.context).load(alertDialog.dialog_product_image.text.toString())
                .into(alertDialog.dialog_show_product_image)
        }
    }

    private fun insertProduct(pn: String, pd: String, pamt: Int, pp: Int, pimg: String) {
        empAPI.insertProduct(pn, pd, pamt, pp, pimg).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                println(":::::::: RES")
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EmpHomeActivity,
                        "เพิ่มสินค้าสำเร็จ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                println(":::::: FAIL")
                t.printStackTrace()
            }

        })
    }
}