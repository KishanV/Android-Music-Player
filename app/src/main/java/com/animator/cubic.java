package com.animator;

import android.view.animation.Interpolator;

public class cubic implements Interpolator{

	@Override
    public float getInterpolation(float t) {
		if ((t*=2) < 1) return 0.5f*t*t*t;
		return 0.5f * ((t-=2)*t*t + 2);
    }
}
