package Views.api;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.linedeer.api.call;
import com.linedeer.player.Ui;

import java.util.Vector;

import Views.ContentHome;

public class FMlyt extends FrameLayout {

	public FMlyt(Context context) {
		super(context);
		setClickable(true);
		setSoundEffectsEnabled(false);
	}

	@Override
	public void setBackgroundColor(int color) {
		if(mCatch){
			virchualLyt.setBackgroundColor(color);
		}else{
			super.setBackgroundColor(color);
		}
	}

	Vector<shapeImg> Shapes;
	public void addShape(shapeImg img){
		if(Shapes == null){
			Shapes = new Vector<shapeImg>(2);
		}
		if(mCatch){
			virchualLyt.addShape(img);
		}else{
			Shapes.add(img);
		}
	}

	public void removeShape(shapeImg img){
		if(mCatch){
			virchualLyt.removeShape(img);
		}else{
			Shapes.remove(img);
		}
		if(Shapes.size() == 0){
			Shapes = null;
		}
	}

	public void drawShape(Canvas canvas){
		if(Shapes != null){
			int len = Shapes.size();
			for(int i = 0;i < len;i++){
				Shapes.get(i).draw(canvas);
			}
		}
	}

	OnClickListener clickHandler;
	call Call;
	int animTime = 300;

	public void onClick(call Call){
		this.Call = Call;
		clickHandler = new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentHome.This.setTouchBlock(false);
				new android.os.Handler().postDelayed(
						new Runnable() {
							public void run() {
								Ui.ef.clickPlay();
								FMlyt.this.Call.onCall(true);
								ContentHome.This.setTouchBlock(true);
							}
						}, 100);
			}
		};
		setOnClickListener(clickHandler);
	}

	public void removeClick(){
		setOnClickListener(null);
		clickHandler = null;
		Call = null;
	}

	public boolean mCatch = false;
	FMlyt virchualLyt;
	public FMView FMcatch;
	public Bitmap bm;
	public Canvas cn;
	Paint alphaPaint;
	public void setEnableCatch(){
		if(mCatch == false){
			super.setBackgroundResource(0);

			alphaPaint = new Paint();
			virchualLyt = new FMlyt(getContext(),width,height);
			virchualLyt.setBackgroundResource(0);
			FMcatch = new FMView(getContext(),width,height){
				@Override
				protected void onDraw(Canvas canvas) {
					//super.onDraw(canvas);
					if(bm != null){
						canvas.drawBitmap(bm,0,0,null);
					}
				}
			};
			//FMcatch.setBackgroundColor(0x00000000);
			FMcatch.setBackgroundResource(0);
			FMcatch.invalidate();
			FMcatch.setClickable(true);
			super.addView(virchualLyt);
			mCatch = true;
		}
	}

	float alpha = 1;
	boolean alphaApplyed = false;
	public void setAlpha(float alpha,boolean toBitmap) {

		if(mCatch){
			if(!toBitmap){
				if(bm != null){
					this.alpha = alpha;
					if(!alphaApplyed){
						alphaApplyed = true;
						virchualLyt.draw(cn);
					}
					FMcatch.setAlpha(alpha);
					FMcatch.invalidate();
				}

			}else{
				if(this.alpha != alpha){
					alphaApplyed = false;
					FMcatch.setAlpha(1);
					this.alpha = alpha;
					virchualLyt.draw(cn);
					Paint pn = new Paint();
					pn.setColor(Color.argb((int) ((alpha)*255),0,0,0));
					cn.drawRect(0,0,width,height,pn);
					FMcatch.invalidate();
				}
			}

		} else{
			super.setAlpha(alpha);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		drawShape(canvas);
		if(rippleSet){
			canvas.drawCircle(DownX,DownY,ripleHt,mBitmapPaint);
		}
	}

	public void onDrawCatch(){

	}

	public void onRemoveCatch(){

	}

	void createBitmap(){
		if(bm == null){
			bm = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
			cn = new Canvas(bm);
		}
	}

	public boolean drawEd = false;
	public void drawCatch(){
		if(mCatch && !drawEd){
			drawEd = true;
			onDrawCatch();
			createBitmap();
			virchualLyt.invalidate();
			virchualLyt.draw(cn);
			super.removeView(virchualLyt);
			super.addView(FMcatch);
			FMcatch.invalidate();
			//invalidate();
		}
	}

	public void removeCatch(){
		if(mCatch && drawEd){
			//super.setBackgroundResource(0);
			drawEd = false;
			super.removeView(FMcatch);
			super.addView(virchualLyt);
			virchualLyt.invalidate();
			onRemoveCatch();
			//invalidate();
		}
	}



	@Override
	public void addView(View child) {
		if(mCatch && child != virchualLyt && child != FMcatch){
			virchualLyt.addView(child);
		}else{
			super.addView(child);
		}
	}

	@Override
	public void addView(View child, int index) {
		if(mCatch && child != virchualLyt && child != FMcatch){
			virchualLyt.addView(child, index);
		}else{
			super.addView(child, index);
		}
	}


	@Override
	public void removeView(View child) {
		if(mCatch && child != virchualLyt && child != FMcatch){
			virchualLyt.removeView(child);
		}else{
			super.removeView(child);
		}
	}

	@Override
	public int indexOfChild(View child) {
		if(mCatch && child != virchualLyt && child != FMcatch){
			return virchualLyt.indexOfChild(child);
		}else{
			return super.indexOfChild(child);
		}
	}

	AnimatorSet Set;
	boolean rippleSet = false;
	boolean rippleDown = true;
	public void setRipple(boolean b) {
		rippleSet = b;
		if(height > width){
			supportPixle = height;
		}else{
			supportPixle = width;
		}
		if(rippleSet){
			Set = new AnimatorSet();
			Set.setStartDelay(0);
			mBitmapPaint = new Paint();
			mBitmapPaint.setAntiAlias(true);
			mBitmapPaint.setColor(0xFFFFFFFF);
		}else{
			if(Set != null) {
				Set.cancel();
				Set.setStartDelay(0);
				Set = null;
			}
			mBitmapPaint = null;
		}
	}

	public void setRipple(boolean b,float alpha) {
		rippleSet = b;
		supportAlpha = alpha;
		setRipple(b);
	}

	public void setRippleDown(boolean b) {
		rippleDown = b;
	}

	public FMlyt(Context context,int width,int height) {
		super(context);
		setClickable(true);
		setSoundEffectsEnabled(false);
		setSize(width,height);
	}

	public FMlyt(Context context,int width,int height,int x,int y) {
		super(context);
		setClickable(true);
		setSoundEffectsEnabled(false);
		setSize(width,height);
		setX(x);
		setY(y);
	}

	@Override
	public void setClickable(boolean clickable) {
		click = clickable;
		super.setClickable(clickable);
	}

	public int height;
	public int width;
	public void setSize
			(int width, int height){
		this.height = height;
		this.width = width;
		setLayoutParams(new FrameLayout.LayoutParams(width, height));
	}

	int DownX;
	int DownY;
	public void onDown(MotionEvent event) {
		DownX = (int)event.getX();
		DownY = (int)event.getY();
		if(Set != null){
			Set.cancel();
		}
		if(rippleSet){
			setMove(0);
			point = 0;
		}
		if(rippleSet && rippleDown != false){
			if(android.os.Build.VERSION.SDK_INT <= 18){
				setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			}
			Set.playTogether( ObjectAnimator.ofInt(FMlyt.this, "Move",0,  49));
			Set.setDuration(2000).start();
		}
	}

	public void onUp(MotionEvent event) {
		if(rippleSet){
			int nowPoint = point;
			Set.cancel();
			if(android.os.Build.VERSION.SDK_INT <= 18){
				setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			}
			Set.playTogether( ObjectAnimator.ofInt(FMlyt.this, "Move",nowPoint,  100));
			Set.addListener(new animLis(){
				@Override
				public void onAnimationEnd(Animator animation) {
					if(android.os.Build.VERSION.SDK_INT <= 18){
						setLayerType(View.LAYER_TYPE_HARDWARE, null);
					}
				}
			});
			Set.setDuration(animTime).start();
		}
	}

	public Paint mBitmapPaint ;

	int point;//100
	float ripleHt = 0;
	int ripleAlpha = 0;
	float lastAlpha = ripleAlpha;
	int supportPixle;
	float supportAlpha = 2F;
	float ripleScale = 1;
	public void setMove(int point){
		this.point = point;
		ripleHt = (supportPixle * 4f)/ 100 * point;

		if(point == 49){
			ripleAlpha = 0;
		}else if(point <= 75 && point >= 30){
			ripleAlpha = (int)(25f + ((point - 30) * 0.5));
			// Log.i("My","75 - 30 : -----------------" );
		}else if(point > 75){
			ripleAlpha = (int)(47f - ( (47f / 25f) * ((point - 75))));
			//Log.i("My","75 - 100 : -----------------" );
		}else{
			ripleAlpha = (25);
		}

		if(point >= 25 && point <= 50){
			ripleScale =  (0.875f) + (float)(0.005f * (point - 25f));
		} else if(point <= 25){
			ripleScale = (1.0f) - (float)(0.0050f *  point);
		}else{
			ripleScale = (1f);
		}

		ripleAlpha = (int)(ripleAlpha * supportAlpha);
		mBitmapPaint.setAlpha(ripleAlpha);

		if(lastAlpha != ripleScale){
			lastAlpha = ripleScale;
			//Log.i("My","ripleAlpha : "+ ripleScale + ": " + (100f - point) );
		}

		invalidate();

	}

	protected void postDraw(Canvas canvas) {
		if(rippleSet){
			canvas.save();
			canvas.scale(ripleScale,ripleScale,DownX,DownY);
			canvas.restore();
		}
	}

	protected void afterDraw(Canvas canvas, Path path) {
		if(rippleSet){
			canvas.save();
			canvas.clipPath(path);
			canvas.drawCircle(DownX,DownY,ripleHt,mBitmapPaint);
			canvas.restore();
		}
	}

	public boolean click = true;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(click){
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					onDown(event);
				case MotionEvent.ACTION_MOVE:
					onMove(event);
					break;
				case MotionEvent.ACTION_OUTSIDE:
					onOut(event);
				case MotionEvent.ACTION_UP:
					onUp(event);
					break;
				default:
					onleave(event);
			}
			if(!clickLisner){
				return true;
			}
		}
		return super.onTouchEvent(event);
	}

	public boolean clickLisner = false;

	@Override
	public void setOnClickListener(OnClickListener l) {
		if(l != null){
			clickLisner = true;
		}else{
			clickLisner = false;
		}
		super.setOnClickListener(l);
	}

	public void onleave(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	public void onOut(MotionEvent event) {
		// TODO Auto-generated method stub
		if(rippleSet){
			int nowPoint = point;
			Set.cancel();
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			Set.playTogether( ObjectAnimator.ofInt(FMlyt.this, "Move",nowPoint,  100));
			Set.setDuration(500).start();
		}
	}

	public void onMove(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	public void setAnim(int y) {
		// TODO Auto-generated method stub

	}

	public void InCenter(float wh,float ht){
		setX((int)((wh - width) / 2f));
		setY((int)((ht - height) / 2f));
	}

}
