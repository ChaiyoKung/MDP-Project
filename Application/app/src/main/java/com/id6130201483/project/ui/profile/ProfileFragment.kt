package com.id6130201483.project.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.id6130201483.project.EditProfileActivity
import com.id6130201483.project.R
import com.id6130201483.project.api.ProfileFragmentAPI
import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.savedata.CustomerStore
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    val profileAPI = ProfileFragmentAPI.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        root.btn_edit.setOnClickListener {
            clickEdit()
        }
        return root
    }

    override fun onResume() {
        callCustomer(CustomerStore.id!!)
        super.onResume()
    }

    private fun clickEdit() {
        val intent = Intent(context, EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun callCustomer(cid: Int) {
        profileAPI.selectCustomer(cid).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    Glide.with(this@ProfileFragment).load(body?.cus_image).into(iv_profile_image)
                    tv_profile_username.text = body?.cus_username
                    tv_profile_address.text = body?.cus_address
                    tv_profile_name.text = "${body?.cus_fname} ${body?.cus_lname}"
                    tv_profile_email.text = body?.cus_email
                    tv_profile_tel.text = body?.cus_tel
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}