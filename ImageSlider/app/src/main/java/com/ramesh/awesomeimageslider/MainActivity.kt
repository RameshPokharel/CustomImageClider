package com.ramesh.awesomeimageslider

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ramesh.awesomeslider.DefaultSliderView
import com.ramesh.awesomeslider.IndicatorAnimations
import com.ramesh.awesomeslider.IndicatorView.SliderAnimations
import com.ramesh.awesomeslider.SliderView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageSlider.setIndicatorAnimation(IndicatorAnimations.SWAP) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
        imageSlider.setScrollTimeInSec(5) //set scroll delay in seconds :

        setSliderViews()
    }

    private fun setSliderViews() {

        for (i in 0..2) {

            val sliderView = DefaultSliderView(this)

            when (i) {
                0 -> sliderView.setImageDrawable(R.drawable.ic_launcher_background)
                1 -> sliderView.setImageUrl("https://www.gstatic.com/webp/gallery3/2.png")
                2 -> sliderView.setImageUrl("https://www.gstatic.com/webp/gallery3/4_webp_ll.png")
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            sliderView.setDescription(
                "The quick brown fox jumps over the lazy dog.\n" +
                        "Jackdaws love my big sphinx of quartz. " + (i + 1)
            )

            sliderView.setOnSliderClickListener(object : SliderView.OnSliderClickListener {
                override fun onSliderClick(sliderView: SliderView) {
                    Toast.makeText(this@MainActivity, "This is slider " + (i + 1), Toast.LENGTH_SHORT).show()

                }
            })

            //at last add this view in your layout :
            imageSlider.addSliderView(sliderView)
        }

    }

}
