package com.id6130201483.project

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.id6130201483.project.parcelable.CustomerParcel

class IndexActivity : AppCompatActivity() {
    var cusParcel: CustomerParcel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        val data = intent.extras
        cusParcel = data?.getParcelable("cusData")

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                clickCart()
                true
            }
            R.id.menu_order_has_transport -> {
                clickOrderHasTransport()
                true
            }
            R.id.menu_history -> {
                clickHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clickCart() {
        val intent = Intent(this, CartActivity::class.java)
        intent.putExtra("cusData", cusParcel)
        startActivity(intent)
    }

    private fun clickOrderHasTransport() {
        val intent = Intent(this, OrderHasTransportActivity::class.java)
        startActivity(intent)
    }

    private fun clickHistory() {
        val intent = Intent(this, OrderHistoryActivity::class.java)
        startActivity(intent)
    }
}