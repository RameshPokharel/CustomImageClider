package com.ramesh.awesomeimageslider

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ramesh.awesomeslider.OnSliderClickListener
import com.ramesh.awesomeslider.SliderDataModel
import com.ramesh.awesomeslider.SliderView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSliderClickListener {
    override fun onSliderClick(sliderView: SliderView) {
        Toast.makeText(this, sliderView.imageUrl, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSliderViews()
    }

    private fun setSliderViews() {

        var list = ArrayList<SliderDataModel>()
        list.add(SliderDataModel("https://www.gstatic.com/webp/gallery3/2.png", "Hello world"))
        list.add(SliderDataModel(R.drawable.ic_launcher_background, "Hello world hello wrold"))
        list.add(SliderDataModel("https://www.gstatic.com/webp/gallery3/4_webp_ll.png", ""))
        imageSlider.setSliderData(
            list,
            ImageView.ScaleType.CENTER_CROP,
            this, this
        )

    }

}
