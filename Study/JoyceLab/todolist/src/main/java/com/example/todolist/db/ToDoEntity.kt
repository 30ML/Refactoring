package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity // 어떤 구성요소인지 알려주려면 반드시 어노테이션을 써주어야 함.
data class ToDoEntity ( // Kotlin Data Class / 중괄호 아닌 소괄호
    @PrimaryKey(autoGenerate = true) var id: Int? = null, // Primary Key(기본키)
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "importance") val importance: Int,
)
