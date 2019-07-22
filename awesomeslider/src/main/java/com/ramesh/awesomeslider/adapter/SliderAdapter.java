package com.ramesh.awesomeslider.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.ramesh.awesomeslider.SliderView;

import java.util.ArrayList;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<SliderView> sliderViews = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void setSliderViews(ArrayList<SliderView> sliderViews) {
        this.sliderViews = sliderViews;
    }

    public void addSliderView(SliderView view) {
        sliderViews.add(view);
        notifyDataSetChanged();
    }

    public void removeAllSliderViews() {
        sliderViews.clear();
        notifyDataSetChanged();
    }

    public SliderView getSliderView(int position) {
        if (sliderViews.isEmpty() || position >= sliderViews.size()) {
            return null;
        }
        return sliderViews.get(position);
    }

    @Override
    public int getCount() {
        return sliderViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SliderView imageSliderView = sliderViews.get(position);
        View v = imageSliderView.getView();
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
