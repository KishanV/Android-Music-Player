package Views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader.TileMode;

import Views.api.shapeImg;

public class radiusBorder extends shapeImg {

	class Path0 extends Path {
		public Path0() {
		}
	}

	public Paint P0 = new Paint();
	public Path0 S0 = new Path0();

	float Ht = 104;
	float Wh = 104;

	int[] colors;
	float[] points;


	public radiusBorder(float width, float height, float x, float y,float border, float crv) {
		this.width = width;
		this.height = height;

		S0.moveTo(crv, border);//S0.moveTo(13f, 2f);
		S0.quadTo(border, border, border, crv);//S0.quadTo(2f, 2f, 2f, 13f);
		S0.lineTo(border, height-crv);//S0.lineTo(2f, 87f);
		S0.quadTo(border, height-border, crv, height-border);//S0.quadTo(2f, 98f, 13f, 98f);
		S0.lineTo(width - crv, height-border);//S0.lineTo(37f, 98f);
		S0.quadTo(width - border, height-border, width - border, height-crv);//S0.quadTo(48f, 98f, 48f, 87f);
		S0.lineTo(width - border, crv);//S0.lineTo(48f, 13f);
		S0.quadTo(width - border, border, width - crv, border);//S0.quadTo(48f, 2f, 37f, 2f);
		S0.lineTo(crv, border);//S0.lineTo(13f, 2f);
		S0.moveTo(crv, 0f);//S0.moveTo(13f, 0f);
		S0.lineTo(width-crv, 0f);//S0.lineTo(37f, 0f);
		S0.quadTo(width, 0f, width, crv);//S0.quadTo(50f, 0f, 50f, 13f);
		S0.lineTo(width, height-crv);//S0.lineTo(50f, 87f);
		S0.quadTo(width, height, width - crv, height);//S0.quadTo(50f, 100f, 37f, 100f);
		S0.lineTo(crv, height);//S0.lineTo(13f, 100f);
		S0.quadTo(0f, height, 0f, height- crv);//S0.quadTo(0f, 100f, 0f, 87f);
		S0.lineTo(0f, crv);//S0.lineTo(0f, 13f);
		S0.quadTo(0f, 0f, crv, 0f);//S0.quadTo(0f, 0f, 13f, 0f);

		/*S0.moveTo(13f, 2f);
		S0.quadTo(2f, 2f, 2f, 13f);
		S0.lineTo(2f, 87f);
		S0.quadTo(2f, 98f, 13f, 98f);
		S0.lineTo(37f, 98f);
		S0.quadTo(48f, 98f, 48f, 87f);
		S0.lineTo(48f, 13f);
		S0.quadTo(48f, 2f, 37f, 2f);
		S0.lineTo(13f, 2f);
		S0.moveTo(13f, 0f);
		S0.lineTo(37f, 0f);
		S0.quadTo(50f, 0f, 50f, 13f);
		S0.lineTo(50f, 87f);
		S0.quadTo(50f, 100f, 37f, 100f);
		S0.lineTo(13f, 100f);
		S0.quadTo(0f, 100f, 0f, 87f);
		S0.lineTo(0f, 13f);
		S0.quadTo(0f, 0f, 13f, 0f);*/


		init(1, 1, x, y);
		S0.transform(matrix);
		init((float) width / (float) Wh, (float) height / (float) Ht, x, y);
		P0.setAntiAlias(true);

	}

	@Override
	public void setSize(int wh, int ht) {
		super.setSize(wh, ht);
		S0.transform(matrix);
	}

	public radiusBorder(float width, float height, float x, float y, float R1, float R2, float R3, float R4) {
		this.width = width;
		this.height = height;

		S0.moveTo(0f, R1);
		S0.quadTo(0f, 0f, R1, 0f);
		S0.lineTo(width - R2, 0f);
		S0.quadTo(width, 0f, width, R2);
		S0.lineTo(width, height - R3);
		S0.quadTo(width, height, width - R3, height);
		S0.lineTo(R4, height);
		S0.quadTo(0f, height, 0f, height - R4);
		S0.lineTo(0f, R1);

		init(1, 1, x, y);
		S0.transform(matrix);
		init((float) width / (float) Wh, (float) height / (float) Ht, x, y);
		P0.setAntiAlias(true);

	}

	@Override
	public void setX(int x) {
		super.setX(x);
		S0.transform(matrix);
	}

	@Override
	public void setY(int x) {
		super.setY(x);
		S0.transform(matrix);
	}

	public void setColor(int[] colors, float[] points) {
		this.colors = colors;
		this.points = points;

		LinearGradient Lg = null;
		Lg = new LinearGradient(104, 52, 0, 52, colors, points, TileMode.MIRROR);
		Lg.setLocalMatrix(matrix);
		P0.setShader(Lg);
	}



	public void draw(Canvas canvas) {
		if(drawing){
			canvas.drawPath(S0, P0);
		}
	}

	public void setColor(int i) {
		P0.setColor(i);
	}
	
	public void setColor(Color i) {
		P0.setColor(i.hashCode());
	}
}