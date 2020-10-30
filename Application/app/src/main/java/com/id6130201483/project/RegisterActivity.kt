package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.api.RegisterActivityAPI
import com.id6130201483.project.encrypt.Encrypt
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    val registerAPI = RegisterActivityAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val actionBar = supportActionBar
        actionBar!!.title = "สมัครสมาชิก"
        actionBar.setDisplayHomeAsUpEnabled(true)

        btn_register.setOnClickListener {
            clickRegister()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun clickRegister() {
        if (edt_username.text.isNotEmpty() && edt_password.text.isNotEmpty() && edt_confpass.text.isNotEmpty() && edt_email.text.isNotEmpty() && edt_fname.text.isNotEmpty() && edt_lname.text.isNotEmpty() && edt_address.text.isNotEmpty() && edt_tel.text.isNotEmpty()) {
            val u = edt_username.text.toString()
            val p = edt_password.text.toString()
            val cfp = edt_confpass.text.toString()
            val e = edt_email.text.toString()
            val fn = edt_fname.text.toString()
            val ln = edt_lname.text.toString()
            val addr = edt_address.text.toString()
            val t = edt_tel.text.toString()

            if (p == cfp) {
                if (edt_image.text.isNotEmpty()) {
                    val img = edt_image.text.toString()
                    register(u, p, e, fn, ln, addr, t, img)
                } else {
                    register(u, p, e, fn, ln, addr, t)
                }
            } else {
                Toast.makeText(this, "รหัสผ่านและยืนยันรหัสผ่านจะต้องตรงกัน", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show()
        }
    }

    private fun register(
        u: String,
        p: String,
        e: String,
        fn: String,
        ln: String,
        addr: String,
        t: String,
        img: String = "https://images.squarespace-cdn.com/content/5b47794f96d4553780daae3b/1531516790942-VFS0XZE207OEYBLVYR99/profile-placeholder.jpg"
    ) {
        val enP = Encrypt.encryptString(p)
        registerAPI.register(u, enP, e, fn, ln, addr, t, img).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "สมัครสมาชิกสำเร็จ", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "สมัครสมาชิกไม่สำเร็จ", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show()
            }

        })
    }
}