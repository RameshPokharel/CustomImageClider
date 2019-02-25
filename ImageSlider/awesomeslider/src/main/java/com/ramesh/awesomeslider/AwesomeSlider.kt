package com.ramesh.awesomeslider

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ramesh.awesomeslider.IndicatorView.PageIndicatorView
import com.ramesh.awesomeslider.IndicatorView.SliderAnimations
import com.ramesh.awesomeslider.IndicatorView.animation.type.AnimationType
import com.ramesh.awesomeslider.Transformations.AntiClockSpinTransformation
import com.ramesh.awesomeslider.Transformations.FadeTransformation
import com.ramesh.awesomeslider.adapter.SliderAdapter
import java.util.*

public class AwesomeSlider : FrameLayout, CircularSliderHandle.CurrentPageListener {

    private val DELAY_MS: Long = 2000
    private var mFlippingPagerAdapter: PagerAdapter? = null
    private var currentPage = 0
    private var circularSliderHandle: CircularSliderHandle? = null
    private var mSliderPager: ViewPager? = null
    private var pagerIndicator: PageIndicatorView? = null
    private var scrollTimeInSec = 2
    private val handlerVal = Handler()
    private var flippingTimer: Timer? = null
    private var autoScrolling = true


    constructor(context: Context) : super(context) {
        setLayout(context)

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setLayout(context)

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout(context)

    }

    private fun getFlippingPagerAdapter(): PagerAdapter? {
        return mFlippingPagerAdapter
    }

    private fun setLayout(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_layout, this, true)
        mSliderPager = view.findViewById(R.id.vp_slider_layout)
        pagerIndicator = view.findViewById(R.id.pager_indicator)
        pagerIndicator?.setDynamicCount(true)

        mFlippingPagerAdapter = SliderAdapter(context)

        mSliderPager?.setAdapter(mFlippingPagerAdapter)

        // Handler for onPageChangeListener
        circularSliderHandle = CircularSliderHandle(mSliderPager)
        circularSliderHandle?.setCurrentPageListener(this)
        mSliderPager?.addOnPageChangeListener(circularSliderHandle!!)

        //Starting auto cycle at the time of setting up of layout
        startAutoCycle()
    }

    override fun onCurrentPageChanged(currentPosition: Int) {
        this.currentPage = currentPage
    }

    private fun startAutoCycle() {

        flippingTimer?.cancel()
        flippingTimer?.purge()

        if (!autoScrolling) return

        //Cancel If Thread is Running
        val scrollingThread = Runnable {
            if (currentPage == getFlippingPagerAdapter()?.getCount()) {
                currentPage = 0
            }
            // true set for smooth transition between pager
            mSliderPager?.setCurrentItem(currentPage++, true)
        }

        flippingTimer = Timer()
        flippingTimer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(scrollingThread)
            }
        }, DELAY_MS, (scrollTimeInSec * 1000).toLong())
    }

    fun isAutoScrolling(): Boolean {
        return autoScrolling
    }

    fun setAutoScrolling(autoScrolling: Boolean) {
        this.autoScrolling = autoScrolling
        startAutoCycle()
    }


    fun getScrollTimeInSec(): Int {
        return scrollTimeInSec
    }

    fun setScrollTimeInSec(time: Int) {
        scrollTimeInSec = time
        startAutoCycle()
    }

    fun setSliderTransformAnimation(animation: SliderAnimations) {

        mSliderPager?.setPageTransformer(false, AntiClockSpinTransformation())

    }

    fun setCustomSliderTransformAnimation(animation: ViewPager.PageTransformer) {
        mSliderPager?.setPageTransformer(false, animation)

    }

    fun setIndicatorAnimation(animations: IndicatorAnimations) {
        mSliderPager?.setPageTransformer(false, FadeTransformation())

    }

    fun addSliderView(sliderView: SliderView) {
        (mFlippingPagerAdapter as SliderAdapter).addSliderView(sliderView)
        if (pagerIndicator != null && mSliderPager != null) {
            pagerIndicator?.setViewPager(mSliderPager)
        }
    }

}