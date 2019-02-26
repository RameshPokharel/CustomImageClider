package com.ramesh.awesomeslider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class DefaultSliderView extends SliderView {
    private int descriptionTextColor = Color.WHITE;
    private float descriptionTextSize = 1;

    DefaultSliderView(SliderBuilder builder) {
        super(builder);
    }

    @Override
    public View getView() {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(context).inflate(R.layout.image_slider_layout_item, null, true);
        ImageView autoSliderImage = v.findViewById(R.id.iv_auto_image_slider);
        TextView tv_description = v.findViewById(R.id.tv_auto_image_slider);
        if (shouldShowDescription) {
            tv_description.getBackground();
            // if (descriptionTextSize != 1) {
            //  tv_description.setTextSize(descriptionTextSize);
            // }
            tv_description.setTextColor(descriptionTextColor);
            tv_description.setText(getDescription());
            tv_description.setVisibility(View.VISIBLE);
        } else {
            tv_description.setVisibility(View.INVISIBLE);
        }
        bindViewData(v, autoSliderImage);
        return v;
    }

    @Override
    void bindViewData(View v, ImageView autoSliderImage) {
        final DefaultSliderView con = this;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSliderClickListener != null) {
                    onSliderClickListener.onSliderClick(con);
                }
            }
        });
        try {
            autoSliderImage.setScaleType(getScaleType());
            if (imageUrl != null) {
                Glide.with(context).asDrawable().load(imageUrl).into(autoSliderImage);
            }
            if (imageRes != 0) {
                Glide.with(context).asDrawable().load(imageRes).into(autoSliderImage);
            }
        } catch (Exception exception) {
            Log.d("Exception", exception.getMessage());
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionTextColor(int descriptionTextColor) {
        this.descriptionTextColor = descriptionTextColor;
    }

    public void setDescriptionTextSize(float descriptionTextSize) {
        this.descriptionTextSize = descriptionTextSize;
    }

}
