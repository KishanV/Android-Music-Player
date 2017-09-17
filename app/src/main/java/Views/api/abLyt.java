package Views.api;



import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.AbsoluteLayout;

public class abLyt extends AbsoluteLayout {
	
	public abLyt(Context context) {
		super(context);
	}

	public abLyt(Context context, int width, int height, int x, int y) {
		super(context);
		setPos(width, height, x, y);
	}

	public LayoutParams Ls = new AbsoluteLayout.LayoutParams(0, 0, 0, 0);

	public void setPos(int width, int height, int x, int y) {
		Ls.height = height;
		Ls.width = width;
		Ls.x = x;
		Ls.y = y;
		setLayoutParams(Ls);
	}
	
	
	
	public void setAnim(int val) {

	}
	
	public void setx(int x){
		Ls.x = x;
		setLayoutParams(Ls);
	}
	
	public void sety(int y){
		Ls.y = y;
		setLayoutParams(Ls);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
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
		invalidate();
		super.onTouchEvent(event);
		return true;
	}

	public void onUp(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void onleave(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	public void onOut(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	public void onMove(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	public void onDown(MotionEvent event) {
		// TODO Auto-generated method stub

	}

	public int getAnim() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	

}
