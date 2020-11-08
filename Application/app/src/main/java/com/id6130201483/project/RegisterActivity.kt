package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
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

        edt_image.addTextChangedListener {
            Glide.with(this).load(edt_image.text.toString()).into(iv_profile_image)
        }

        btn_register.setOnClickListener {
            clickRegister()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun isFormValid(): Boolean {
        fun notValidToast(text: String) {
            Toast.makeText(this, "กรุณากรอก$text", Toast.LENGTH_SHORT).show()
        }

        if (edt_username.text.isNullOrEmpty()) {
            notValidToast("ชื่อผู้ใช้งาน")
            edt_username.requestFocus()
            return false
        } else if (edt_password.text.isNullOrEmpty()) {
            notValidToast("รหัสผ่าน")
            edt_password.requestFocus()
            return false
        } else if (edt_confpass.text.isNullOrEmpty()) {
            notValidToast("ยืนยันรหัสผ่าน")
            edt_confpass.requestFocus()
            return false
        } else if (edt_email.text.isNullOrEmpty()) {
            notValidToast("อีเมล")
            edt_email.requestFocus()
            return false
        } else if (edt_fname.text.isNullOrEmpty()) {
            notValidToast("ชื่อ")
            edt_fname.requestFocus()
            return false
        } else if (edt_lname.text.isNullOrEmpty()) {
            notValidToast("นามสกุล")
            edt_lname.requestFocus()
            return false
        } else if (edt_address.text.isNullOrEmpty()) {
            notValidToast("ที่อยู่")
            edt_address.requestFocus()
            return false
        } else if (edt_tel.text.isNullOrEmpty()) {
            notValidToast("เบอร์โทรศัพท์")
            edt_tel.requestFocus()
            return false
        }
        return true
    }

    private fun clickRegister() {
        if (isFormValid()) {
            val u = edt_username.text.toString()
            val p = edt_password.text.toString()
            val cfp = edt_confpass.text.toString()
            val e = edt_email.text.toString()
            val fn = edt_fname.text.toString()
            val ln = edt_lname.text.toString()
            val addr = edt_address.text.toString()
            val t = edt_tel.text.toString()
            // If edt_image.text.toString() is null set img = ""
            val img = edt_image.text.toString() ?: ""

            // Password is equal Confirm password
            if (p == cfp) {
                register(u, p, e, fn, ln, addr, t, img)
            } else {
                Toast.makeText(this, "รหัสผ่านและยืนยันรหัสผ่านจะต้องตรงกัน", Toast.LENGTH_LONG)
                    .show()
            }
        }
        /*if (edt_username.text.isNotEmpty() && edt_password.text.isNotEmpty() && edt_confpass.text.isNotEmpty() && edt_email.text.isNotEmpty() && edt_fname.text.isNotEmpty() && edt_lname.text.isNotEmpty() && edt_address.text.isNotEmpty() && edt_tel.text.isNotEmpty()) {
            val u = edt_username.text.toString()
            val p = edt_password.text.toString()
            val cfp = edt_confpass.text.toString()
            val e = edt_email.text.toString()
            val fn = edt_fname.text.toString()
            val ln = edt_lname.text.toString()
            val addr = edt_address.text.toString()
            val t = edt_tel.text.toString()

            // Password is equal Confirm password
            if (p == cfp) {
                // If edt_image.text.toString() is null set img=""
                val img = edt_image.text.toString() ?: ""
                register(u, p, e, fn, ln, addr, t, img)
            } else {
                Toast.makeText(this, "รหัสผ่านและยืนยันรหัสผ่านจะต้องตรงกัน", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show()
        }*/
    }

    private fun register(
        u: String,
        p: String,
        e: String,
        fn: String,
        ln: String,
        addr: String,
        t: String,
        img: String
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