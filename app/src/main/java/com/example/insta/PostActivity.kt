package com.example.insta

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImage.CancelledResult.bitmap
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView

class PostActivity : AppCompatActivity() {
    private lateinit var imageUri:Uri
    private lateinit var close:ImageView
    private lateinit var imageAdded:CropImageView
    private lateinit var post: TextView
    private lateinit var description:AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        close= findViewById(R.id.close)
        imageAdded=findViewById(R.id.image_added)
        post=findViewById(R.id.post)
        description=findViewById(R.id.description)

        close.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }

}