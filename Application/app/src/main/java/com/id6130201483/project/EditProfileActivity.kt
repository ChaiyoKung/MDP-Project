package com.id6130201483.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.id6130201483.project.api.ProfileFragmentAPI
import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.activity_edit_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    val editProfileAPI = ProfileFragmentAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val actionBar = supportActionBar
        actionBar!!.title = "แก้ไขข้อมูลส่วนตัว"
        actionBar.setDisplayHomeAsUpEnabled(true)

        btn_clear_image_url.setOnClickListener {
            clearImageURL()
        }

        edt_profile_image.addTextChangedListener {
            Glide.with(this@EditProfileActivity).load(edt_profile_image.text.toString())
                .into(iv_profile_image)
        }

        btn_save.setOnClickListener {
            clickSave(
                CustomerStore.id!!,
                edt_profile_email.text.toString(),
                edt_profile_fname.text.toString(),
                edt_profile_lname.text.toString(),
                edt_profile_address.text.toString(),
                edt_profile_tel.text.toString(),
                edt_profile_image.text.toString()
            )
        }
    }

    override fun onResume() {
        callCustomer(CustomerStore.id!!)
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun callCustomer(cid: Int) {
        editProfileAPI.selectCustomer(cid).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    Glide.with(this@EditProfileActivity).load(body?.cus_image)
                        .into(iv_profile_image)
                    edt_profile_username.setText(body?.cus_username)
                    edt_profile_address.setText(body?.cus_address)
                    edt_profile_fname.setText(body?.cus_fname)
                    edt_profile_lname.setText(body?.cus_lname)
                    edt_profile_email.setText(body?.cus_email)
                    edt_profile_tel.setText(body?.cus_tel)
                    edt_profile_image.setText(body?.cus_image)
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun clickSave(
        cid: Int,
        email: String,
        fname: String,
        lname: String,
        address: String,
        tel: String,
        image: String
    ) {
        editProfileAPI.updateCustomer(cid, email, fname, lname, address, tel, image)
            .enqueue(object : Callback<Customer> {
                override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "แก้ไขข้อมูลสำเร็จ",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun clearImageURL() {
        edt_profile_image.text.clear()
    }
}