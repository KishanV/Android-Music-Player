package Views;


import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;

import Views.api.FMText;
import Views.api.shapeImg;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;

import com.linedeer.player.Ui;

public class textImg extends shapeImg {

	public Paint P0 = new Paint();

	float Ht = 5;
	float Wh = 379;
	float lastPOint;

	int[] colors = new int[] { Color.parseColor("#ffffffff"), Color.parseColor("#ffffffff"), Color.parseColor("#00ffffff") };
	float[] points;
	public boolean shader;
	int Size;

	public textImg(int width, int height, int x, int y, float Size) {
		this.Size = (int) (Size * 0.95f);
		//this.Size = Size;
		P0.setAntiAlias(true);
		P0.setColor(Color.parseColor("#ffffffff"));
		P0.setTextSize(this.Size);
		P0.setTypeface(Ui.cd.cuprumFont);
		this.height = height;
		this.width = width;
		init((float) width / (float) Wh, (float) height / (float) Ht, x, y);

		lastPOint = ((100f / this.width) * (this.width - (Ui.cd.DPIX[30]))) / 100f;
		LinearGradient Lg = null;

		points = new float[] { 0.0f, lastPOint, 1.0f };
		Lg = new LinearGradient(0, 2, 379, 2, colors, points, TileMode.CLAMP);
		Lg.setLocalMatrix(matrix);
		//P0.setShader(Lg);

	}


	@Override
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}


	public String Text = "";
	public float Y = 0;
	public float X = 0;
	
	public void setText(String text) {
		Text = text;
		Rect bounds =   getBound(text);

		Y = (int) ((-bounds.top) + ((height - (bounds.bottom - bounds.top)) / 2f));
		X = 0;
	}

	

	public void setText(String text, boolean center) {
		Text = text;
		Rect bounds    = getBound(text);
		Y = (int) ((-bounds.top) + ((height - (bounds.bottom - bounds.top)) / 2f));

		if (width < (bounds.right + bounds.left)) {
			X = 0;
		} else {
			X = (width - (bounds.right + bounds.left)) / 2f;
			// P0.setTextAlign(Align.CENTER);
		}
	}

	public void draw(Canvas canvas) {
		if(drawing){
			canvas.drawText(Text, x + X, y + Y, P0);
		}
	}

	public float getX() {
		return x + X;
	}

	public float getY() {
		return y + Y;
	}

	public boolean hasEffect = false;
	public void setEfects(int[] colors) {
		hasEffect = true;
		lastPOint = ((100f / this.width) * (this.width - (Ui.cd.getHt(30)))) / 100f;
		LinearGradient Lg = null;
		//Log.i("My","Devani : " + lastPOint);
		points = new float[] { 0.0f, lastPOint , 1.0f };
		P0 = new Paint();
		P0.setAntiAlias(true);
		P0.setTextSize(Size);
		P0.setTypeface(Ui.cd.cuprumFont);
		Lg = new LinearGradient( (x + X), 0, this.width +  (x + X), 0, colors, points, TileMode.CLAMP);
		P0.setShader(Lg);
		//Lg.setLocalMatrix(matrix);
	}

	public void removeEfects() {
		hasEffect = false;
		P0 = new Paint();
		P0.setAntiAlias(true);
		P0.setColor(color);
		P0.setTextSize(Size);
		P0.setTypeface(Ui.cd.cuprumFont);
	}

	int color;
	public void setColor(int color) {
		this.color = color;
		removeEfects();
	}

	Rect getBound(String str){
		Rect bounds = new Rect();
		P0.getTextBounds(str, 0, str.length(), bounds);
		if(width == 0 ){
			width = (bounds.right - bounds.left);
		}
		if(height == 0){
			height = (bounds.bottom - bounds.top);
		}
		return bounds;
	}

	public static FMText getFMText(Context context,String text,float size){
		textImg img = new textImg(0,0,0,0,size);
		img.setText(text);
		FMText view = new FMText(context,img);
		return view;
	}

	public static textImg getText(String text, float size){
		textImg img = new textImg(0,0,0,0,size);
		img.setText(text);
		return img;
	}
}