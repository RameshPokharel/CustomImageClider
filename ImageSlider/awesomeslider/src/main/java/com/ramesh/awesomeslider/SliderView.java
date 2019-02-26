package com.ramesh.awesomeslider;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class SliderView {

    @DrawableRes
    protected int imageRes = 0;
    protected OnSliderClickListener onSliderClickListener;
    protected String description;
    protected String imageUrl;
    protected Boolean shouldShowDescription;
    protected ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
    protected Context context;

    abstract public View getView();

    abstract void bindViewData(View v, ImageView autoSliderImage);


    SliderView(SliderBuilder builder) {
        this.context = builder.getContext().get();
        this.imageUrl = builder.getImageUrl();
        if (builder.getImageDrawable() != null)
            this.imageRes = builder.getImageDrawable();
        this.scaleType = builder.getImageScaleType();
        this.description = builder.getImageDescription();
        this.onSliderClickListener = builder.getListener();
        this.shouldShowDescription = builder.getShouldShowDescription();
    }


    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private void setImageUrl(String imageUrl) {
        if (imageRes != 0) {
            throw new IllegalStateException("Can't set multiple images");
        }
        this.imageUrl = imageUrl;
    }

    private void setImageByte(byte[] imageByte) {
        ContextWrapper wrapper = new ContextWrapper(context);
        File file = new File(wrapper.getCacheDir().getAbsolutePath(), "Cached" + System.currentTimeMillis() + ".jpeg");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 95, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.imageUrl = String.valueOf(Uri.fromFile(file));
    }

    private void setImageDrawable(int imageDrawable) {
        if (!TextUtils.isEmpty(imageUrl)) {
            throw new IllegalStateException("Can't set multiple images");
        }
        this.imageRes = imageDrawable;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    private void setImageScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    private void setOnSliderClickListener(OnSliderClickListener l) {
        onSliderClickListener = l;
    }

}
