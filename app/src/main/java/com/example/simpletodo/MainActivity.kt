package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter:TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // Remove item from list
                listOfTasks.removeAt(position)
                // Notify adapter that our data changed
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }

        // Detect when user clicks add button
        //findViewById<Button>(R.id.button).setOnClickListener {

        // Way to test
        //Log.i("Bran","User clicked the button")

        loadItems()

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerView to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup for button and input field
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener {
            // Grab text user inputted
            val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()
            // Add string to list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify data adapter that data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            // Clear text field
            inputTextField.setText("")

            // Save items
            saveItems()
        }


    }
    // Save data user has inputted
    // Save data by writing and reading from a file

    // Method for getting file needed
    fun getDataFile() : File {
        // Every line represents a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }
    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException) {
            ioException.printStackTrace()
        }

    }
    // Save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}