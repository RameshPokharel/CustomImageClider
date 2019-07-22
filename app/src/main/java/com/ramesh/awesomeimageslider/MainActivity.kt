package com.ramesh.awesomeimageslider

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ramesh.awesomeslider.SliderDataModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSliderViews()
        button.setOnClickListener(View.OnClickListener {
            imageSlider.clearSliderData()
            setSliderViews()
        })

    }

    private fun setSliderViews() {

        var list = ArrayList<SliderDataModel>()
        list.add(SliderDataModel("https://www.gstatic.com/webp/gallery3/2.png", "Hello world"))
        list.add(SliderDataModel(R.drawable.ic_launcher_background, "Hello world hello wrold"))
        list.add(SliderDataModel("https://www.gstatic.com/webp/gallery3/4_webp_ll.png", ""))
        imageSlider.setSliderData(
            list,
            ImageView.ScaleType.CENTER_CROP,
            this
        )

    }


}
