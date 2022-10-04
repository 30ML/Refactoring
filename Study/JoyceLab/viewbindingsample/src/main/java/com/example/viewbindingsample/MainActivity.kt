package com.example.viewbindingsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.viewbindingsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // 바인딩 클래스 객체 생성
        val view = binding.root // 바인딩 객체의 root view 참조
        setContentView(view) // 생성한 뷰 설정
    }
}