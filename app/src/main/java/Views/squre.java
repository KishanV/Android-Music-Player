package Views;

import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Path;
import Views.api.shapeImg;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

public class squre extends shapeImg {

	class Path0 extends Path {
		public Path0() {
			moveTo(0f, 0f);
			lineTo(100f, 0f);
			lineTo(100f, 100f);
			lineTo(0f, 100f);
			lineTo(0f, 0f);
		}
	}

	Paint P0 = new Paint();
	public Path0 S0 = new Path0();

	float Ht = 100;
	float Wh = 100;

	int[] colors;
	float[] points;

	public squre(float width, float height, float x, float y) {
		this.height = height;
		this.width = width;
		init((float) width / (float) Wh, (float) height / (float) Ht, x,
				y);
		S0.transform(matrix);
		P0.setAntiAlias(true);

	}

	@Override
	public void setY(int y) {
		super.setY(y);
		S0.transform(matrix);
	}

	@Override
	public void setX(int y) {
		super.setX(y);
		S0.transform(matrix);
	}

	public void setColor(int[] colors, float[] points) {
		this.colors = colors;
		this.points = points;
		LinearGradient Lg = null;
		Lg = new LinearGradient(40, 313, 444, 313, colors, points,
				TileMode.MIRROR);
		Lg.setLocalMatrix(matrix);
		P0.setShader(Lg);
	}

	public void draw(Canvas canvas) {
		canvas.drawPath(S0, P0);
	}

	public void setColor(int i) {
		P0.setColor(i);
	}
}