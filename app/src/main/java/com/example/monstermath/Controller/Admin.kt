package com.example.monstermath.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.monstermath.Model.Customer
import com.example.monstermath.Model.MonsterMathDBHelper
import com.example.monstermath.R

class Admin : AppCompatActivity() {
    private lateinit var dbHelper: MonsterMathDBHelper
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var customers: MutableList<Customer>
    private var isDeleteMode = false
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin)

        dbHelper = MonsterMathDBHelper(this)
        listView = findViewById(R.id.listView)

        customers = dbHelper.getAllCustomers().toMutableList()
        val fullNameList = customers.map { it.fullname }

        adapter = ArrayAdapter(this, R.layout.list_item, R.id.textView, fullNameList)
        listView.adapter = adapter

        val returnButton: Button = findViewById(R.id.returnToStart)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




        val addButton: Button = findViewById(R.id.AddButton)
        addButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val deleteButton: Button = findViewById(R.id.DeleteButton)
        deleteButton.setOnClickListener {
            if (!isDeleteMode) {
                isDeleteMode = true
                isEditMode = false // Exit edit mode if it's active
                Toast.makeText(this, "Select an item to delete", Toast.LENGTH_SHORT).show()
            } else {
                isDeleteMode = false
                Toast.makeText(this, "Delete mode turned off", Toast.LENGTH_SHORT).show()
            }
        }

        val editButton: Button = findViewById(R.id.EditButton)
        editButton.setOnClickListener {
            if (!isEditMode) {
                isEditMode = true
                isDeleteMode = false // Exit delete mode if it's active
                Toast.makeText(this, "Select an item to edit", Toast.LENGTH_SHORT).show()
            } else {
                isEditMode = false
                Toast.makeText(this, "Edit mode turned off", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for list items to handle deletion or editing
        listView.setOnItemClickListener { _, _, position, _ ->
            if (isDeleteMode) {
                val selectedCustomer = customers[position]
                val deleted = dbHelper.removeUser(selectedCustomer.username)
                if (deleted) {
                    customers.removeAt(position)
                    adapter.remove(adapter.getItem(position))
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show()
                }
            } else if (isEditMode) {
                // Launch an edit activity with the selected user's information
                val intent = Intent(this, EditUser::class.java)
                intent.putExtra("username", customers[position].username)
                // Pass other relevant information to the edit activity if needed
                startActivity(intent)
            }
        }
    }
}




