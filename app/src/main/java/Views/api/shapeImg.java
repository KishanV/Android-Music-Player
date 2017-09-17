package Views.api;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import Views.radiusSqure;

public class shapeImg implements ShapeInterface{
	
	public float scalex;
	public float height;
	public float width;
	public float scaley;
	public float x;
	public float y;
	public float Ht;
	public float Wh;
	public Matrix matrix = new Matrix();
	public boolean changed = false;
	public Path mask;
	public Paint maskPaint;

	public void init(float scalex,float scaley,float x,float y) {
		this.scalex = scalex;
		this.scaley = scaley;
		this.x =  x;
		this.y =  y;
		matrix.postScale(scalex,scaley);
		matrix.postTranslate(x,y);
		
	}

	public void setSize(int wh,int ht) {

		this.scalex = (float)wh/(float)width;
		this.scaley = (float)ht/(float)height;

		width = wh;
		height = ht;
		matrix.reset();
		matrix.postScale(scalex,scaley);
		//matrix.postTranslate(x,y);
		changed = true;
	}

	public void setX(int x) {
		matrix.reset();
		matrix.postTranslate(x - this.x,0);
		this.x = x;
		changed = true;
	}
	
	public void setY(int y) {
		matrix.reset();
		matrix.postTranslate(0,y - this.y);
		this.y = y;
		changed = true;
		//Log.i("My","SetY : " + (y - this.y));
	}


	public void InCenter(float wh,float ht){
		setX((int)((wh - width) / 2f));
		setY((int)((ht - height) / 2f));
	}


	@Override
	public void setDown(){}
	
	@Override
	public void setUp(){}
	
	@Override
	public void draw(Canvas canvas) {}

	public boolean drawing = true;
	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public void InCenter(shapeImg sq) {
		setX((int) (sq.x +  ((sq.width - width) / 2f)));
		setY((int) (sq.y + ((sq.height - height) / 2f)));
	}

	public Bitmap getBitmap(){
		Bitmap bm = bm = Bitmap.createBitmap((int)width,(int)height, Bitmap.Config.ARGB_8888);
        Canvas cn = new Canvas(bm);
        draw(cn);
        return bm;
	}
}
