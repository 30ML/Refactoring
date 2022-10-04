package com.example.qrcodereader

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.qrcodereader.databinding.ActivityMainBinding
import com.google.common.util.concurrent.ListenableFuture

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val PERMISSION_REQUEST_CODE = 1
    private val PERMISSION_REQUIRED = arrayOf(android.Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (!hasPermissions(this)) {
            requestPermissions(PERMISSION_REQUIRED, PERMISSION_REQUEST_CODE)
        } else {
            startCamera()
        }
    }

    fun hasPermissions(context: Context) = PERMISSION_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                Toast.makeText(this@MainActivity, "권한 요청이 승인되었습니다.", Toast.LENGTH_LONG).show()
                startCamera()
            } else {
                Toast.makeText(this@MainActivity, "권한 요청이 거부되었습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    fun startCamera() { // 미리보기와 이미지 분석 시작
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable { // cameraProviderFuture 태스크가 끝나면 실행됨
            val cameraProvider = cameraProviderFuture.get()
            val preview = getPreview() // 미리보기 객체 가져오기
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // 후면 카메라 선택

            cameraProvider.bindToLifecycle(this, cameraSelector, preview) // 미리보기 기능 선택(preview)
        }, ContextCompat.getMainExecutor(this))
    }

    fun getPreview(): Preview { // 미리보기 객체 반환
        val preview: Preview = Preview.Builder().build() // Preview 객체 생성
        preview.setSurfaceProvider(binding.barcodePreview.surfaceProvider)
        return preview
    }
}