package com.animator;


import android.view.animation.Interpolator;

public class bounce implements Interpolator{

	   

		private static float bounce(float t) {
	        return t * t * 8.0f;
	    }
		 
		@Override
	    public float getInterpolation(float t) {
	        // _b(t) = t * t * 8
	        // bs(t) = _b(t) for t < 0.3535
	        // bs(t) = _b(t - 0.54719) + 0.7 for t < 0.7408
	        // bs(t) = _b(t - 0.8526) + 0.9 for t < 0.9644
	        // bs(t) = _b(t - 1.0435) + 0.95 for t <= 1.0
	        // b(t) = bs(t * 1.1226)
			float a = param_a;
			float p = param_p;
			if (t==0) return 0;  if (t==1) return 1; if (!setP) p=.7f;
			float s;
			if (!setA || a < 1) { a=1; s=p/4; }
			else s = p/(2*PI) * (float)Math.asin(1/a);
			return a*(float)Math.pow(2,-10*t) * (float)Math.sin( (t-s)*(1.6*PI)/p ) + 1;
	    }
		
		
		private float PI = 3.14159265f;
		protected float param_a;
		protected float param_p;
		protected boolean setA = false;
		protected boolean setP = false;
}
