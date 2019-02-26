package com.ramesh.awesomeslider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_awesome_full_screen.*

class AwesomeFullScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awesome_full_screen)
        Glide.with(this).asDrawable().load(intent.getStringExtra("imageUrl")).into(touchImage)

    }
}
