package com.id6130201483.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.activity_product.*

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val actionBar = supportActionBar
        actionBar!!.title = "รูปภาพ"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val image = intent.getStringExtra("imageURL")
        Glide.with(applicationContext).load(image).into(iv_fullscreen_image)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}