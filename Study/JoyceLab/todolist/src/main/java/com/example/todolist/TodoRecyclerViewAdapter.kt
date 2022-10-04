package com.example.todolist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.db.ToDoEntity

class TodoRecyclerViewAdapter(private val todoList: ArrayList<ToDoEntity>) :
    RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle

        // 뷰 바인딩에서 기본적으로 제공하는 root 변수는 레이아웃의 루트 레이아웃을 의미함.
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create item_todo.xml View Binding object
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]
        // Change Color By importance
        when (todoData.importance) {
            1 -> holder.tv_importance.setBackgroundColor(R.color.red)
            2 -> holder.tv_importance.setBackgroundColor(R.color.yellow)
            3 -> holder.tv_importance.setBackgroundColor(R.color.green)
        }
        // Change Number By Importance
        holder.tv_importance.text = todoData.importance.toString()
        // Change Title
        holder.tv_title.text = todoData.title
    }

    override fun getItemCount(): Int {
        // Number of RecycleView Item == Size of ToDo List
        return todoList.size
    }
}
