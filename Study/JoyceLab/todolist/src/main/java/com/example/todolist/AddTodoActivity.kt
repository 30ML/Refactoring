package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTodoBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntity

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddTodoBinding
    lateinit var db: AppDatabase
    lateinit var todoDao: ToDoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnCompletion.setOnClickListener {
            insertTodo()
        }
    }

    /**
     * @desc 할 일 추가 함수
     */
    private fun insertTodo() {
        val todoTitle = binding.etTitle.text.toString() // 제목
        var todoImportance = binding.rgRadioGroup.checkedRadioButtonId // 중요도

        when (todoImportance) {
            R.id.btn_high -> todoImportance = 1
            R.id.btn_middle -> todoImportance = 2
            R.id.btn_low -> todoImportance = 3
            else -> todoImportance = -1
        }

        if (todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(
                this,
                "추가되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Thread {
                todoDao.insertTodo(ToDoEntity(null, todoTitle, todoImportance))
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "추가되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish() // AddToDoActivity 종료. 다시 MainActivity로 돌아감.
                }
            }.start()
        }
    }
}