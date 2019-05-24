package com.ramesh.awesomeslider

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_awesome_full_screen.*

class AwesomeFullScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AwesomeSliderTheme);
        setContentView(R.layout.activity_awesome_full_screen)
        Glide.with(this).asDrawable().load(intent.getStringExtra("imageUrl")).into(awesome_touchImage)

        aawesome_cancel.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}
