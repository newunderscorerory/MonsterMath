package com.example.monstermath.Controller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.Customer
import com.example.monstermath.Model.CustomerDBHelper
import com.example.monstermath.R

class Admin : AppCompatActivity() {
    private lateinit var dbHelper: CustomerDBHelper
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin)

        dbHelper = CustomerDBHelper(this)
        listView = findViewById(R.id.listView)

        val customers = dbHelper.getAllCustomers()

        val customerInfoList = arrayListOf<String>()
        for (customer in customers) {
            val customerInfo = "Username: ${customer.username}\nFull Name: ${customer.fullname}\nEmail: ${customer.email}"
            customerInfoList.add(customerInfo)
        }

        val adapter = ArrayAdapter(this, R.layout.list_item, customerInfoList)
        listView.adapter = adapter
    }
}
