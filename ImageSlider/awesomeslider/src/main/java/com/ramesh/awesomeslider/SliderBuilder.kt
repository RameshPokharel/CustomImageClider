package com.ramesh.awesomeslider

import android.app.Activity
import android.widget.ImageView
import java.lang.ref.WeakReference

class SliderBuilder {
    /**
     * Instantiates a new Builder.
     *
     * @param context the context
     */
    protected var context: WeakReference<Activity>
    protected var imageSlider: SliderType? = SliderType.DEFAULT
    protected var imageUrl: String? = null
    protected var imageDescription: String? = null
    protected var shouldShowDescription: Boolean? = true
    protected var imageDrawable: Int? = null
    protected var listener: OnSliderClickListener? = null
    protected var imageScaleType: ImageView.ScaleType? = ImageView.ScaleType.CENTER_CROP

    constructor (context: Activity) {
        this.context = WeakReference<Activity>(context)
    }

    fun setSliderType(imageSlider: SliderType?): SliderBuilder {
        this.imageSlider = imageSlider
        return this
    }

    fun setShouldShowDescription(flag: Boolean?): SliderBuilder {
        this.shouldShowDescription = flag
        return this
    }

    fun setImageDescription(description: String?): SliderBuilder {
        this.imageDescription = description
        this.imageDescription?.length?.let {
            if (it > 0) {
                this.shouldShowDescription = true
            }
        }
        return this
    }

    fun setImageDrawable(drawable: Int?): SliderBuilder {
        this.imageDrawable = drawable

        return this
    }

    fun setImageScaleType(scaleType: ImageView.ScaleType?): SliderBuilder {
        this.imageScaleType = scaleType
        return this
    }

    fun setOnClickListener(listener: OnSliderClickListener?): SliderBuilder {
        this.listener = listener
        return this
    }

    fun setImageUrl(imageUrl: String?): SliderBuilder {
        this.imageUrl = imageUrl
        return this
    }

    fun build(): SliderView {
        var slider = when (imageSlider) {
            SliderType.DEFAULT -> {
                DefaultSliderView(this)
            }
            else -> {
                DefaultSliderView(this)

            }

        }
        return slider
    }

}
