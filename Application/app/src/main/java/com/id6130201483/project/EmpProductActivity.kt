package com.id6130201483.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.id6130201483.project.api.EmpAPI
import com.id6130201483.project.api.ProductActivityAPI
import com.id6130201483.project.dataclass.Product
import kotlinx.android.synthetic.main.activity_emp_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpProductActivity : AppCompatActivity() {
    val empAPI = EmpAPI.create()
    var pid: Int = 0
    var imageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_product)

        val actionBar = supportActionBar
        actionBar!!.title = "สินค้า"
        actionBar.setDisplayHomeAsUpEnabled(true)

        pid = intent.getIntExtra("pid", 0)

        iv_product_image.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("imageURL", imageURL)
            startActivity(intent)
        }

        edt_product_image.addTextChangedListener {
            Glide.with(this@EmpProductActivity).load(edt_product_image.text.toString())
                .into(iv_product_image)
        }

        btn_save.setOnClickListener {
            clickSave()
        }

        btn_delete.setOnClickListener {
            clickDelete()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callProduct(pid)
        super.onResume()
    }

    private fun callProduct(pid: Int) {
        empAPI.selectProduct(pid).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    Glide.with(this@EmpProductActivity).load(body?.product_image)
                        .into(iv_product_image)
                    edt_product_name.setText(body?.product_name)
                    edt_product_price.setText(body?.product_price.toString())
                    edt_product_detail.setText(body?.product_detail)
                    edt_product_amount.setText(body?.product_amount.toString())
                    edt_product_image.setText(body?.product_image)
                    imageURL = body?.product_image.toString()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun clickSave() {
        empAPI.updateProduct(
            pid,
            edt_product_name.text.toString(),
            edt_product_detail.text.toString(),
            edt_product_amount.text.toString().toInt(),
            edt_product_price.text.toString().toInt(),
            edt_product_image.text.toString()
        ).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EmpProductActivity,
                        "แก้ไขข้อมูลสินค้าสำเร็จ",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun clickDelete() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("ลบสินค้า")
            setMessage("คุณต้องการที่จะลบสินค้าชิ้นนี้จริงหรือไม่ ?")
            setPositiveButton("ไม่ใช่") { _, _ -> }
            setNegativeButton("ใช่") { _, _ -> deleteProduct(pid) }
            show()
        }
    }

    private fun deleteProduct(pid: Int) {
        empAPI.deleteProduct(pid).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EmpProductActivity,
                        "ลบสินค้าสำเร็จ",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}