package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.id6130201483.project.api.TransportActivityAPI
import com.id6130201483.project.dataclass.CustomerOrderTransport
import com.id6130201483.project.dataclass.Transport
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.activity_transport.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransportActivity : AppCompatActivity() {
    val transAPI = TransportActivityAPI.create()
    var orderTotalNotTransCost: Int = 0
    val cid = CustomerStore.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport)

        val actionBar = supportActionBar
        actionBar!!.title = "การจัดส่ง"
        actionBar.setDisplayHomeAsUpEnabled(true)

        orderTotalNotTransCost = intent.getIntExtra("total", 0)

        edt_address.setText(CustomerStore.address)
        tv_total.text = "รวม ${orderTotalNotTransCost} บาท"

        rd_transport_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rd_transport_item1 -> setOrderTotalWithTransportCost(30)
                R.id.rd_transport_item2 -> setOrderTotalWithTransportCost(40)
                R.id.rd_transport_item3 -> setOrderTotalWithTransportCost(45)
            }
        }

        btn_save.setOnClickListener {
            save()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        callCustomerOrderTransport()
        super.onResume()
    }

    private fun setOrderTotalWithTransportCost(tansCost: Int) {
        tv_total.text = "รวม ${orderTotalNotTransCost + tansCost} บาท"
    }

    private fun callCustomerOrderTransport() {
        if (cid != null) {
            transAPI.getCustomerOrderTransport(cid!!)
                .enqueue(object : Callback<CustomerOrderTransport> {
                    override fun onResponse(
                        call: Call<CustomerOrderTransport>,
                        response: Response<CustomerOrderTransport>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            when (body?.transport_id) {
                                1 -> {
                                    rd_transport_item1.isChecked = true
                                }
                                2 -> {
                                    rd_transport_item2.isChecked = true
                                }
                                3 -> {
                                    rd_transport_item3.isChecked = true
                                }
                            }

                            tv_total.text = "รวม ${
                                orderTotalNotTransCost + body?.transport_cost.toString().toInt()
                            } บาท"
                        }
                    }

                    override fun onFailure(call: Call<CustomerOrderTransport>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }

    private fun getTransportID(): Int {
        return when (rd_transport_group.checkedRadioButtonId) {
            R.id.rd_transport_item1 -> 1
            R.id.rd_transport_item2 -> 2
            R.id.rd_transport_item3 -> 3
            else -> 1
        }
    }

    private fun isFromValid(): Boolean {
        if (cid == null) return false
        if (edt_address.text.isNullOrEmpty()) {
            Toast.makeText(this, "กรุณากรอกที่อยู่", Toast.LENGTH_SHORT).show()
            edt_address.requestFocus()
            return false
        }
        return true
    }

    private fun save() {
        if (isFromValid()) {
            transAPI.updateAddressAndTransport(cid!!, edt_address.text.toString(), getTransportID())
                .enqueue(object : Callback<Transport> {
                    override fun onResponse(call: Call<Transport>, response: Response<Transport>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@TransportActivity,
                                "อัปเดทข้อมูลสำเร็จ",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@TransportActivity,
                                "อัปเดทข้อมูลไม่สำเร็จ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Transport>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }
}