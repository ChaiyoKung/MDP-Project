package com.id6130201483.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.id6130201483.project.adapter.EmpShowCusOrderAdapter
import com.id6130201483.project.api.EmpAPI
import com.id6130201483.project.dataclass.Order
import kotlinx.android.synthetic.main.activity_emp_show_cus_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EmpShowCusOrderActivity : AppCompatActivity() {
    val api = EmpAPI.create()
    var cusOrderLists = arrayListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_show_cus_order)

        val actionBar = supportActionBar
        actionBar!!.title = "รายการคำสั่งซื้อสินค้า"
        actionBar.setDisplayHomeAsUpEnabled(true)

        rev_cus_order.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callCusOrder()
        super.onResume()
    }

    private fun callCusOrder() {
        cusOrderLists.clear()

        api.selectAllCusOrder().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        cusOrderLists.add(
                            Order(
                                it.order_id.toString().toInt(),
                                it.order_date,
                                it.order_track ?: "",
                                it.cus_id.toString().toInt(),
                                it.transport_id.toString().toInt(),
                                it.status_id.toString().toInt()
                            )
                        )

                        rev_cus_order.adapter =
                            EmpShowCusOrderAdapter(cusOrderLists, this@EmpShowCusOrderActivity)
                    }
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun getMyDateFormatted(date: String, format: String = "dd-MM-yyyy"): String {
        val inputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(format)
        val date: LocalDate = LocalDate.parse(date, inputFormatter)

        return outputFormatter.format(date)
    }
}