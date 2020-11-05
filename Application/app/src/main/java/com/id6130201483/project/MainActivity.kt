package com.id6130201483.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.dataclass.Employee
import com.id6130201483.project.api.MainActivityAPI
import com.id6130201483.project.dataclass.CreateNewOrder
import com.id6130201483.project.dataclass.Order
import com.id6130201483.project.encrypt.Encrypt
import com.id6130201483.project.parcelable.CustomerParcel
import com.id6130201483.project.savedata.CustomerStore
import com.id6130201483.project.savedata.UserTypeStore
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val mainAPI = MainActivityAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "เข้าสู่ระบบ"

        btn_login.setOnClickListener {
            clickBtnLogin()
        }

        btn_register.setOnClickListener {
            clickBtnRegister()
        }
    }

    private fun clickBtnLogin() {
        if (edt_username.text.isNotEmpty() && edt_password.text.isNotEmpty()) {
            val u = edt_username.text.toString()
            val p = edt_password.text.toString()
            val enP = Encrypt.encryptString(p)

            val us = u.split("-")
            if (us[0] == "KRG" && us[us.size - 1] == "99") {
                loginEmp(u, enP)
            } else {
                loginCus(u, enP)
            }
        } else {
            Toast.makeText(
                this,
                "กรุณากรอกชื่อผู้ใช้งานและรหัสผ่าน",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun clickBtnRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun loginEmp(u: String, p: String) {
        mainAPI.loginEmp(u, p).enqueue(object : Callback<Employee> {
            override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                if (response.isSuccessful) {
                    UserTypeStore.type = "emp"
                    val intent = Intent(this@MainActivity, EmpHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(this@MainActivity, "ไม่พบผู้ใช้งาน", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loginCus(u: String, p: String) {
        mainAPI.loginCus(u, p).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val rb = response.body()

                    val id = rb?.cus_id.toString().toInt()
                    val username = rb?.cus_username.toString()
                    val email = rb?.cus_email.toString()
                    val fname = rb?.cus_fname.toString()
                    val lname = rb?.cus_lname.toString()
                    val address = rb?.cus_address.toString()
                    val tel = rb?.cus_tel.toString()
                    val image = rb?.cus_image.toString()

                    checkCustomerOrder(id)

                    CustomerStore.setData(
                        id,
                        username,
                        email,
                        fname,
                        lname,
                        address,
                        tel,
                        image
                    )
                    UserTypeStore.type = "cus"
                    val intent = Intent(this@MainActivity, IndexActivity::class.java)
                    intent.putExtra(
                        "cusData",
                        CustomerParcel(
                            id,
                            username,
                            email,
                            fname,
                            lname,
                            address,
                            tel,
                            image
                        )
                    )
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun getMyDateNow(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return current.format(formatter)
    }

    private fun checkCustomerOrder(cid: Int) {
        mainAPI.checkCustomerOrder(cid).enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    // Already have order
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                // Don't have order
                // Create first order
                createNewOrderIfFirst(cid, "2020-99-99")
            }

        })
    }

    private fun createNewOrderIfFirst(cid: Int, oDate: String) {
        println(":::::::::: CREATE NEW ORDER")
        mainAPI.createNewOrder(cid, oDate).enqueue(object : Callback<CreateNewOrder> {
            override fun onResponse(
                call: Call<CreateNewOrder>,
                response: Response<CreateNewOrder>
            ) {
            }

            override fun onFailure(call: Call<CreateNewOrder>, t: Throwable) {
                println(":::::::::::: CREATE FIRST ORDER FAIL")
//                t.printStackTrace()
            }

        })
    }
}
