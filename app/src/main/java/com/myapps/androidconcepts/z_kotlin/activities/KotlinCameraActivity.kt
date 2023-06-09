package com.myapps.androidconcepts.z_kotlin.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import com.myapps.androidconcepts.R
import java.io.File

class KotlinCameraActivity : AppCompatActivity() {
    private lateinit var iv_viewCamera: AppCompatImageView
    private lateinit var btn_openCamera: AppCompatButton
    lateinit var imgUri: Uri

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        iv_viewCamera.setImageURI(null)
        iv_viewCamera.setImageURI(imgUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_camera)

        iv_viewCamera = findViewById(R.id.iv_viewCamera)
        btn_openCamera = findViewById(R.id.btn_openCamera)

        imgUri = createImgUri()!!

        btn_openCamera.setOnClickListener {
            contract.launch(imgUri)
        }
    }

    private fun createImgUri(): Uri? {
        val image = File(applicationContext.filesDir, "cameraPhoto.png")
        return FileProvider.getUriForFile(applicationContext, "com.myapps.androidconcepts.fileProvider", image)
    }


}