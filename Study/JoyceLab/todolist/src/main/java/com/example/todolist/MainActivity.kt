package com.example.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var db: AppDatabase
    private lateinit var todoDao: ToDoDao
    private lateinit var todoList: ArrayList<ToDoEntity>
    private lateinit var adapter: TodoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

        // Get DB Instance
        db = AppDatabase.getInstance(this)!!
        // Get DAO
        todoDao = db.getTodoDao()

        getAllTodoList() // Get TodoList
    }

    private fun getAllTodoList() {
        Thread {
            todoList = ArrayList(todoDao.getAll())
            setRecyclerView()
        }.start()
    }

    // Setting RecyclerView
    private fun setRecyclerView() {
        runOnUiThread {
            adapter = TodoRecyclerViewAdapter(todoList) // Assign Adapter Object
            binding.recyclerView.adapter = adapter // Set Adapter By RecyclerView Adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }
}