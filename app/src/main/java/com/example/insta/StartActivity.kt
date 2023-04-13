package com.example.insta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout

class StartActivity : AppCompatActivity() {
    private lateinit var iconImage:ImageView
    private lateinit var linearLayout:LinearLayout
    private lateinit var register: Button
    private lateinit var login:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        iconImage=findViewById(R.id.icon_image)
        linearLayout=findViewById(R.id.linearLayout)
        register=findViewById(R.id.register)
        login=findViewById(R.id.login)

        linearLayout.animate().alpha(0f).duration = 1
        val animation=TranslateAnimation(0F, 0F,0F,-500F)
        animation.duration=1000
        animation.fillAfter=false
        animation.setAnimationListener(MyAnimationListener())

        iconImage.animation=animation

        register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))

        }
    }

    private inner class MyAnimationListener:AnimationListener{
        override fun onAnimationStart(p0: Animation?) {
//            TODO("Not yet implemented")
        }

        override fun onAnimationEnd(p0: Animation?) {
            iconImage.clearAnimation()
            iconImage.visibility=View.INVISIBLE
            linearLayout.animate().alpha(1f).duration=1000
        }

        override fun onAnimationRepeat(p0: Animation?) {
//            TODO("Not yet implemented")
        }

    }
}