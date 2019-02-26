package com.ramesh.awesomeslider

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ramesh.awesomeslider.IndicatorView.PageIndicatorView
import com.ramesh.awesomeslider.IndicatorView.SliderAnimations
import com.ramesh.awesomeslider.IndicatorView.animation.type.AnimationType
import com.ramesh.awesomeslider.Transformations.*
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

    var sliderView: SliderView? = null

    constructor(context: Context) : super(context) {
        setLayout(context, null, null, null)

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setLayout(context, attrs, null, null)

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setLayout(context, attrs, defStyleAttr, null)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        setLayout(context, attrs, defStyleAttr, defStyleRes)

    }

    private fun getFlippingPagerAdapter(): PagerAdapter? {
        return mFlippingPagerAdapter
    }

    private fun setLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int?, defStyleRes: Int?) {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_layout, this, true)
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AwesomeSlider,
            defStyleAttr ?: 0,
            defStyleRes ?: 0
        )
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

        var indicatorAnimations = a.getInt(R.styleable.AwesomeSlider_awe_indicator_animation, 0)
        var sliderAnimations = a.getInt(R.styleable.AwesomeSlider_awe_slide_animation, 0)
        //mapSlideAnimation int to enum
        var slideA =
            when (sliderAnimations) {
                1 ->
                    SliderAnimations.DEPTHTRANSFORMATION
                2 ->
                    SliderAnimations.FADETRANSFORMATION
                3 ->
                    SliderAnimations.FANTRANSFORMATION
                else
                ->
                    SliderAnimations.ANTICLOCKSPINTRANSFORMATION
            }

        var slideIndicatorA =
            when (indicatorAnimations) {
                0 ->
                    IndicatorAnimations.WORM
                1 ->
                    IndicatorAnimations.THIN_WORM
                2 ->
                    IndicatorAnimations.COLOR
                3 ->
                    IndicatorAnimations.DROP
                4 ->
                    IndicatorAnimations.FILL
                5 ->
                    IndicatorAnimations.NONE
                6 ->
                    IndicatorAnimations.SCALE
                7 ->
                    IndicatorAnimations.SLIDE
                8 ->
                    IndicatorAnimations.SWAP
                else
                ->
                    IndicatorAnimations.SLIDE

            }
        setIndicatorAnimation(slideIndicatorA)
        setSliderTransformAnimation(slideA)
        scrollTimeInSec = a.getInt(R.styleable.AwesomeSlider_awe_scroll_time_sec, 3)

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
                handlerVal.post(scrollingThread)
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

    private fun setScrollTimeInSec(time: Int) {
        scrollTimeInSec = time
        startAutoCycle()
    }

    private fun setSliderTransformAnimation(animation: SliderAnimations) {
        when (animation) {
            SliderAnimations.ANTICLOCKSPINTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                AntiClockSpinTransformation()
            )
            SliderAnimations.CLOCK_SPINTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                Clock_SpinTransformation()
            )
            SliderAnimations.CUBEINDEPTHTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                CubeInDepthTransformation()
            )
            SliderAnimations.CUBEINROTATIONTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                CubeInRotationTransformation()
            )
            SliderAnimations.CUBEINSCALINGTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                CubeInScalingTransformation()
            )
            SliderAnimations.CUBEOUTDEPTHTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                CubeOutDepthTransformation()
            )
            SliderAnimations.CUBEOUTROTATIONTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                CubeOutRotationTransformation()
            )
            SliderAnimations.CUBEOUTSCALINGTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                CubeOutScalingTransformation()
            )
            SliderAnimations.DEPTHTRANSFORMATION -> mSliderPager?.setPageTransformer(false, DepthTransformation())
            SliderAnimations.FADETRANSFORMATION -> mSliderPager?.setPageTransformer(false, FadeTransformation())
            SliderAnimations.FANTRANSFORMATION -> mSliderPager?.setPageTransformer(false, FanTransformation())
            SliderAnimations.FIDGETSPINTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                FidgetSpinTransformation()
            )
            SliderAnimations.GATETRANSFORMATION -> mSliderPager?.setPageTransformer(false, GateTransformation())
            SliderAnimations.HINGETRANSFORMATION -> mSliderPager?.setPageTransformer(false, HingeTransformation())
            SliderAnimations.HORIZONTALFLIPTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                HorizontalFlipTransformation()
            )
            SliderAnimations.POPTRANSFORMATION -> mSliderPager?.setPageTransformer(false, PopTransformation())
            SliderAnimations.SIMPLETRANSFORMATION -> mSliderPager?.setPageTransformer(false, SimpleTransformation())
            SliderAnimations.SPINNERTRANSFORMATION -> mSliderPager?.setPageTransformer(false, SpinnerTransformation())
            SliderAnimations.TOSSTRANSFORMATION -> mSliderPager?.setPageTransformer(false, TossTransformation())
            SliderAnimations.VERTICALFLIPTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                VerticalFlipTransformation()
            )
            SliderAnimations.VERTICALSHUTTRANSFORMATION -> mSliderPager?.setPageTransformer(
                false,
                VerticalShutTransformation()
            )
            SliderAnimations.ZOOMOUTTRANSFORMATION -> mSliderPager?.setPageTransformer(false, ZoomOutTransformation())
            else -> mSliderPager?.setPageTransformer(false, SimpleTransformation())
        }

        mSliderPager?.setPageTransformer(false, AntiClockSpinTransformation())

    }

    fun setCustomSliderTransformAnimation(animation: ViewPager.PageTransformer) {
        mSliderPager?.setPageTransformer(false, animation)

    }

    fun setIndicatorAnimation(animations: IndicatorAnimations) {
        when (animations) {
            IndicatorAnimations.DROP -> pagerIndicator?.setAnimationType(AnimationType.DROP)
            IndicatorAnimations.FILL -> pagerIndicator?.setAnimationType(AnimationType.FILL)
            IndicatorAnimations.NONE -> pagerIndicator?.setAnimationType(AnimationType.NONE)
            IndicatorAnimations.SWAP -> pagerIndicator?.setAnimationType(AnimationType.SWAP)
            IndicatorAnimations.WORM -> pagerIndicator?.setAnimationType(AnimationType.WORM)
            IndicatorAnimations.COLOR -> pagerIndicator?.setAnimationType(AnimationType.COLOR)
            IndicatorAnimations.SCALE -> pagerIndicator?.setAnimationType(AnimationType.SCALE)
            IndicatorAnimations.SLIDE -> pagerIndicator?.setAnimationType(AnimationType.SLIDE)
            IndicatorAnimations.SCALE_DOWN -> pagerIndicator?.setAnimationType(AnimationType.SCALE_DOWN)
            IndicatorAnimations.THIN_WORM -> pagerIndicator?.setAnimationType(AnimationType.THIN_WORM)
        }

    }

    private fun addSliderView(sliderView: SliderView) {
        (mFlippingPagerAdapter as SliderAdapter).addSliderView(sliderView)
        if (pagerIndicator != null && mSliderPager != null) {
            pagerIndicator?.setViewPager(mSliderPager)
        }
    }

    fun setSliderData(
        list: ArrayList<SliderDataModel>,
        scaleType: ImageView.ScaleType,
        activity: Activity,
        listener: OnSliderClickListener
    ) {
        for (it in list) {
            addSliderView(
                SliderBuilder(activity)
                    .setImageUrl(it.imageUrl)
                    .setImageDrawable(it.imageDrawablel)
                    .setImageScaleType(scaleType)
                    .setOnClickListener(listener)
                    .setImageDescription(it.imageDescription)
                    .setSliderType(SliderType.DEFAULT)
                    .setShouldShowDescription(true)
                    .build()
            )
        }


    }


}