package com.ramesh.awesomeslider.IndicatorView;


import androidx.annotation.Nullable;
import com.ramesh.awesomeslider.IndicatorView.animation.AnimationManager;
import com.ramesh.awesomeslider.IndicatorView.animation.controller.ValueController;
import com.ramesh.awesomeslider.IndicatorView.animation.data.Value;
import com.ramesh.awesomeslider.IndicatorView.draw.DrawManager;
import com.ramesh.awesomeslider.IndicatorView.draw.data.Indicator;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
