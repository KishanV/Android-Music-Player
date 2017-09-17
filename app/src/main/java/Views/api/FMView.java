package Views.api;

import android.animation.Animator;
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

import com.linedeer.api.call;
import com.linedeer.player.Ui;

import java.util.Vector;

import Views.ContentHome;

public class FMView extends View {

	public FMView(Context context) {
		super(context);
		setClickable(true);
		setSoundEffectsEnabled(false);
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
		}else{
			if(Set != null) {
				Set.cancel();
				Set.setStartDelay(0);
				Set = null;
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
								FMView.this.Call.onCall(true);
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

	public void setRipple(boolean b,float alpha) {
		rippleSet = b;
		supportAlpha = alpha;
		setRipple(b);
	}

	public void setRippleDown(boolean b) {
		rippleDown = b;
	}

	public FMView(Context context,int width,int height) {
		super(context);
		setClickable(true);
		setSoundEffectsEnabled(false);
		setSize(width,height);
	}

	public FMView(Context context, int width,int height,int x,int y) {
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
	public void setSize(int width,int height){
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
		point = 0;
		setMove(0);
		if(rippleSet && rippleDown != false){
			if(android.os.Build.VERSION.SDK_INT <= 18){
				setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			}
			Set.playTogether( ObjectAnimator.ofInt(FMView.this, "Move",0,  49));
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
			Set.playTogether( ObjectAnimator.ofInt(FMView.this, "Move",nowPoint,  100));
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

	public Paint mBitmapPaint = new Paint() {
		{
			setAntiAlias(true);
			setColor(0xFFFFFFFF);
		}
	};

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

		if(point <= 75 && point >= 30){
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
			canvas.scale(ripleScale,ripleScale,width/2f,height/2f);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawShape(canvas);
		if(rippleSet){
			canvas.drawCircle(DownX,DownY,ripleHt,mBitmapPaint);
		}
	}

	protected void afterDraw(Canvas canvas, Path path) {
		if(rippleSet){
			//canvas.scale(1 + (1 - ripleScale),1 + (1 - ripleScale), width/2,height/2);
			canvas.restore();
			canvas.clipPath(path);
			canvas.drawCircle(DownX,DownY,ripleHt,mBitmapPaint);
		}
	}

	Vector<shapeImg> Shapes;
	public void addShape(shapeImg img){
		if(Shapes == null){
			Shapes = new Vector<shapeImg>(2);
		}
			Shapes.add(img);

	}

	public void removeShape(shapeImg img){

			Shapes.remove(img);

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


	public boolean click = true;
	private Rect rect;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(click){
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					onDown(event);
					break;
				case MotionEvent.ACTION_MOVE:
					onMove(event);
					break;
				case MotionEvent.ACTION_OUTSIDE:
					onOut(event);
				case MotionEvent.ACTION_UP:
					rect = null;
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

	}

	public void onMove(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	public void setAnim(int y) {
		// TODO Auto-generated method stub

	}

	public void InCenter(float wh,float ht){
		setX( ((wh - width) / 2f));
		setY( ((ht - height) / 2f));
	}

	public void InCenter(FMView bassFm) {
		InCenter(bassFm.width,bassFm.height);
		setX(bassFm.getX() + getX());
		setY(bassFm.getY() + getY());
	}
}
