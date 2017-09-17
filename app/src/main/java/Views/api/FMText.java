package Views.api;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import Views.radiusSqure;
import Views.textImg;

public class FMText extends  FMView{

	public textImg img;
	public radiusSqure rs;
	public int minWidth;

	public FMText(Context context, textImg img) {
		super(context,(int)Math.ceil(img.width+3),(int)Math.ceil(img.height));
		this.img = img;
		setBackgroundResource(0);
	}

	public void setText(String string){
		img.width = 0;
		img.height = 0;
		img.setText(string);
		setSize((int)Math.round(img.width+3), (int)Math.round(img.height));
		img.setText(string,true);
	}


	@Override
	public void setSize(int width, int height) {
		if(width < minWidth && minWidth != 0){
			width = minWidth;
		}
		super.setSize(width, height);
		if(img != null){
			img.width = width;
			img.height = height;
		}
	}


	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(rs != null){
			rs.draw(canvas);
		}
		img.draw(canvas);
	}


	public void setSqure(boolean val, int ht) {
		if(val == true){
			rs = null;
			rs = new radiusSqure(width,height,0,0,ht);
		}else{
			rs = null;
		}
	}

	public void setMargin(boolean val, int x , int y) {
		if(val == true){
			setSize(width + x,height + y);
			img.setX(x/2);
			img.setY(y/2);
		}
	}

	public void setSqure(boolean b, int ht, int color0) {
	    setSqure(b,ht);
		rs.setColor(color0);
	}

	public void textIncenter() {
		img.width = width;
		img.height = height;
		img.setText(img.Text,true);
	}
}
