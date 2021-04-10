package com.arkadiuszzimny.elevatorcontrolsystemapp.util;

import android.view.animation.Interpolator;

public class BubbleInterpolator implements Interpolator {

    private double A = 1, f = 10;

    public BubbleInterpolator(double a, double b) {
        A = a;
        this.f = f;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (-1*Math.pow(Math.E, -input/A)*Math.cos(f*input)+1);
    }
}
