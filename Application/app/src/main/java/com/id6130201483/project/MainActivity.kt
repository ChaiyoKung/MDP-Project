package com.id6130201483.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.dataclass.Employee
import com.id6130201483.project.api.MainActivityAPI
import com.id6130201483.project.encrypt.Encrypt
import com.id6130201483.project.parcelable.CustomerParcel
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            if (us[0] == "KRS" && us[us.size - 1] == "99") {
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
                    Toast.makeText(
                        this@MainActivity,
                        "เข้าสู่ระบบสำเร็จ",
                        Toast.LENGTH_LONG
                    ).show()
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
                } else {
                    println("::::: fun loginCus unsuccessfully")
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(this@MainActivity, "ไม่พบผู้ใช้งาน", Toast.LENGTH_LONG).show()
//                println(t.printStackTrace())
            }
        })
    }
}
