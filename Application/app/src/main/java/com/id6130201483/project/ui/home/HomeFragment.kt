package com.id6130201483.project.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.id6130201483.project.R
import com.id6130201483.project.adapter.ProductAdapter
import com.id6130201483.project.api.HomeFragmentAPI
import com.id6130201483.project.dataclass.Product
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    val homeAPI = HomeFragmentAPI.create()
    val productLists = arrayListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        root.btn_search.setOnClickListener {
            searchProduct(root.edt_search.text.toString())
        }

        root.edt_search.addTextChangedListener {
            searchProduct(root.edt_search.text.toString())
        }

        root.rev_product_list.layoutManager = GridLayoutManager(context, 2)

        return root
    }

    override fun onResume() {
        super.onResume()
        callAllProduct()
    }

    private fun callAllProduct() {
        productLists.clear()

        homeAPI.selectAllProduct().enqueue(object : Callback<List<Product>> {
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

                    rev_product_list.adapter = context?.let { ProductAdapter(productLists, it) }
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun searchProduct(pn: String) {
        productLists.clear()

        homeAPI.searchProduct("%$pn%").enqueue(object : Callback<List<Product>> {
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

                    rev_product_list.adapter = context?.let { ProductAdapter(productLists, it) }
                } else {
                    Toast.makeText(context, "ไม่เจอสินค้า", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}