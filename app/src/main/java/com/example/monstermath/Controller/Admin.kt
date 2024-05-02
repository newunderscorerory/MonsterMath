package com.example.monstermath.Controller
// Import necessary Android packages and classes
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
// Define the Admin activity
class Admin : AppCompatActivity() {
    private lateinit var dbHelper: MonsterMathDBHelper
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var customers: MutableList<Customer>
    private var isDeleteMode = false
    private var isEditMode = false

    // Override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin)
        // Initialize the database helper
        dbHelper = MonsterMathDBHelper(this)
        // Find the ListView in the layout
        listView = findViewById(R.id.listView)

        // Get all customers from the database and create a list of their full names
        customers = dbHelper.getAllCustomers().toMutableList()
        val fullNameList = customers.map { it.fullname }

        // Create an ArrayAdapter and set it to the ListView
        adapter = ArrayAdapter(this, R.layout.list_item, R.id.textView, fullNameList)
        listView.adapter = adapter

        // Set up click listener for button
        val returnButton: Button = findViewById(R.id.returnToStart)
        returnButton.setOnClickListener {
            // Return to the Start activity
            val intent = Intent(this, Start::class.java)
            startActivity(intent)
        }

        // Set up click listener for button
        val addButton: Button = findViewById(R.id.AddButton)
        addButton.setOnClickListener {
            // Start the AdminAddUser activity to add a new user
            val intent = Intent(this, AdminAddUser::class.java)
            startActivity(intent)
        }

        // Set up click listener for button
        val deleteButton: Button = findViewById(R.id.DeleteButton)
        deleteButton.setOnClickListener {
            // Toggle delete mode on/off and show appropriate toast messages

            if (!isDeleteMode) {
                isDeleteMode = true
                isEditMode = false
                Toast.makeText(this, "Select an item to delete", Toast.LENGTH_SHORT).show()
            } else {
                isDeleteMode = false
                Toast.makeText(this, "Delete mode turned off", Toast.LENGTH_SHORT).show()
            }
        }

        val editButton: Button = findViewById(R.id.EditButton)
        editButton.setOnClickListener {
            // Toggle edit mode on/off and show appropriate toast messages
            if (!isEditMode) {
                isEditMode = true
                isDeleteMode = false
                Toast.makeText(this, "Select an item to edit", Toast.LENGTH_SHORT).show()
            } else {
                isEditMode = false
                Toast.makeText(this, "Edit mode turned off", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up item click listener for the ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            // If in delete mode, remove the selected user from the database and update the ListView
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
            // If in edit mode, start the EditUser activity with the selected user's username
            } else if (isEditMode) {
                val intent = Intent(this, EditUser::class.java)
                intent.putExtra("username", customers[position].username)

                startActivity(intent)
            }
        }
    }
}




