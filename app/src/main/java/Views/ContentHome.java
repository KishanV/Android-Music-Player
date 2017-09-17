package Views;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;

import com.linedeer.api.call;
import com.linedeer.player.Ui;

import Views.Home.LibreryMenu;
import Views.Home.QuickEqlizer;
import Views.Home.mainHome;
import Views.Library.libraryHome;
import Views.Home.PlayList.playLIstHome;
import Views.Popups.NoticePopup;
import Views.api.FMView;


public class ContentHome extends FrameLayout {
	
	public mainHome MainHome;
	public playLIstHome PlayLIstHome;
	public libraryHome MenuHome;
	public LibreryMenu libMenu;
	public QuickEqlizer QEq;
	public static ContentHome This;
	public FMView AlphaBack;
	public VelocityTracker  Vx;
	boolean canClick = true;

	public ContentHome(Context context) {
		super(context);
		setBackgroundResource(0);
		Vx = VelocityTracker.obtain();
		This = this;
	}

	public void setTouchBlock(boolean bool){
		canClick = bool;
	}

	public static void openMusucLibrery(){
		if(!This.MenuHome.isOpen){
			Ui.bk.add(new call() {
				@Override
				public void onCall(boolean bl) {
					this.lock = This.MenuHome.hasBack();
					if(!this.lock){
						This.MenuHome.open(Ui.cd.DPH,false);
					}
				}
			});
		}
		This.MenuHome.open(0,true);
	}

	public static void openPlaylist(){
		if(!This.PlayLIstHome.isOpen){
			Ui.bk.add(new call() {
				@Override
				public void onCall(boolean bl) {
					This.PlayLIstHome.open(-(int)(Ui.cd.DPW * 0.85f),false);
				}
			});
		}
		This.PlayLIstHome.open(0,true);
	}

	public static void openQEq(){
		if(!This.QEq.isOpen){
			Ui.bk.add(new call() {
				@Override
				public void onCall(boolean bl) {
					This.QEq.open(Ui.cd.DPW,false);
				}
			});
		}
		This.QEq.open((Ui.cd.DPW - This.QEq.width),true);;
	}

	public static void openlibMenu(){
		if(!This.libMenu.isOpen){
			Ui.bk.add(new call() {
				@Override
				public void onCall(boolean bl) {
					This.libMenu.open(Ui.cd.DPH,false);
				}
			});
		}
		This.libMenu.open((Ui.cd.DPH - This.libMenu.height),true);
	}
	public static void closelibMenu(){
		if(This.libMenu.isOpen){
			This.libMenu.close();
			Ui.bk.dropLast();
		}else{
			This.libMenu.close();
		}
	}
	
	public void init(){
		MainHome = new mainHome(getContext(), Ui.cd.DPW, Ui.cd.DPH);
		addView(MainHome);

		AlphaBack = new FMView(getContext(), Ui.cd.DPW, Ui.cd.DPH);
		AlphaBack.setBackgroundColor(0x99000000);
		AlphaBack.setAlpha(0.1F);
		//AlphaBack.setClickable(false);


		final int PlayLIstHomeWh = (int)(Ui.cd.DPW * 0.85f);
		PlayLIstHome = new playLIstHome(getContext(), (int)(Ui.cd.DPW * 0.85f), Ui.cd.DPH){
			@Override
			public void setX(float x) {
				super.setX(x);
				if(x == PlayLIstHome.width){
					PlayLIstHome.setAlpha(0);
				}else{
					PlayLIstHome.setAlpha(1);
				}
				float alpha = (float) (((100f / (PlayLIstHomeWh)) * (PlayLIstHomeWh - Math.abs(x))) * 0.01);
				AlphaBack.setAlpha(alpha);
				if((alpha > 0)){
					AlphaBack.setVisibility(VISIBLE);
					AlphaBack.setClickable(true);
				}else{
					AlphaBack.setVisibility(GONE);
					AlphaBack.setClickable(false);
				}
				AlphaBack.setX(x+PlayLIstHome.width);
				AlphaBack.setY(0);

				if(MenuHome!= null && MenuHome.isOpen){
					MenuHome.setX(x+PlayLIstHome.width);
					MenuHome.FMcatch.setX(-(x+PlayLIstHome.width));
					if(x == 0){
						ContentHome.This.MenuHome.drawCatch();
						removeCatch();
						//setVisibility(VISIBLE);
					}else if(x == -width){
						ContentHome.This.MenuHome.removeCatch();
						removeCatch();
						//setVisibility(GONE);
					} else{
						ContentHome.This.MenuHome.drawCatch();
						drawCatch();
						//setVisibility(VISIBLE);
					}
				}else {
					MainHome.setX(x+PlayLIstHome.width);
					MainHome.FMcatch.setX(-(x+PlayLIstHome.width));
					if(x == 0){
						ContentHome.This.MainHome.drawCatch();
						removeCatch();
						//setVisibility(VISIBLE);
					}else if(x == -width){
						ContentHome.This.MainHome.removeCatch();
						removeCatch();
						//setVisibility(GONE);
					} else{
						ContentHome.This.MainHome.drawCatch();
						drawCatch();
						//setVisibility(VISIBLE);
					}
				}



				//Log.i("My","SetX : " + alpha);
			}
		};
		PlayLIstHome.setX(-(int)(PlayLIstHome.width));


		QEq = new QuickEqlizer(getContext(), (int)(Ui.cd.DPW * 0.85f), Ui.cd.DPH){
			@Override
			public void setX(float x) {
				super.setX(x);
				if(x != Ui.cd.DPW){
					if(MenuHome!= null && MenuHome.isOpen){
						MenuHome.drawCatch();
					}else{
						MainHome.drawCatch();
					}

				}else{
					if(MenuHome!= null && MenuHome.isOpen){
						MenuHome.removeCatch();
					}else{
						MainHome.removeCatch();
					}
				}

				float alpha = (float) (((100f / (PlayLIstHomeWh)) * (PlayLIstHomeWh - Math.abs(x - (Ui.cd.DPW - QEq.width)))) * 0.01);

				if((alpha > 0)){
					AlphaBack.setVisibility(VISIBLE);
					AlphaBack.setClickable(true);
				}else{
					AlphaBack.setVisibility(GONE);
					AlphaBack.setClickable(false);
				}
				AlphaBack.setAlpha(alpha);
				AlphaBack.setX((x- Ui.cd.DPW));
				AlphaBack.setY(0);

				if(MenuHome!= null && MenuHome.isOpen){
					MenuHome.setX((x- Ui.cd.DPW));
					MenuHome.FMcatch.setX(-(x- Ui.cd.DPW));
				}else{
					MainHome.setX((x- Ui.cd.DPW));
					MainHome.FMcatch.setX(-(x- Ui.cd.DPW));
				}

				//Log.i("My","SetX : " + alpha);
			}
		};
		QEq.setX(Ui.cd.DPW);
		addView(QEq);

		MenuHome = new libraryHome(getContext(), Ui.cd.DPW, Ui.cd.DPH){
			@Override
			public void setY(float y) {
				super.setY(y);

				if(y < 0){
					y = 0;
				}
				float alpha = (float) (((100f / (Ui.cd.DPH)) * (Ui.cd.DPH - Math.abs(y))) * 0.01);
				AlphaBack.setAlpha(alpha);
				if((alpha > 0)){
					AlphaBack.setVisibility(VISIBLE);
					AlphaBack.setClickable(true);
				}else{
					AlphaBack.setVisibility(GONE);
					AlphaBack.setClickable(false);
				}

				if(y == 0){
					AlphaBack.setAlpha(0);
					MainHome.setVisibility(GONE);
				}else{
					MainHome.setVisibility(VISIBLE);
				}
				AlphaBack.setY(y-AlphaBack.height);
				//Log.i("My","SetX : " + alpha);
			}
		};
		MenuHome.setY(Ui.cd.DPH);
		addView(MenuHome);
		addView(AlphaBack);
		addView(PlayLIstHome);

		libMenu = new LibreryMenu(getContext(), Ui.cd.DPW, Ui.cd.DPW){
			@Override
			public void setY(float y) {
				super.setY(y);
				float alpha = (float) Math.abs(((100f / (libMenu.height)) * ((Ui.cd.DPH - libMenu.height) - Math.abs(y))) * 0.01);

				if(alpha < 0.50f){
					alpha = 0.50f;
				}

				Log.i("My","alpha : " + alpha);
				if(y >= Ui.cd.DPH){
					if(MenuHome.isOpen){
						MenuHome.setAlpha(1,false);
						MenuHome.removeCatch();
					}else {
						MainHome.setAlpha(1,false);
						MainHome.removeCatch();
					}
				}else if(y < Ui.cd.DPH - libMenu.height){
					if(MenuHome.isOpen){
						MenuHome.setAlpha(alpha,true);
						MenuHome.drawCatch();
					}else {
						MainHome.setAlpha(alpha,true);
						MainHome.drawCatch();
					}
				}else{
					if(MenuHome.isOpen){
						MenuHome.setAlpha(alpha,false);
						MenuHome.drawCatch();
					}else {
						MainHome.setAlpha(alpha,false);
						MainHome.drawCatch();
					}
				}
			}
		};
		libMenu.setY(Ui.cd.DPH);
		addView(libMenu);
		//ContentHome.openMusucLibrery();
		//ContentHome.openPlaylist();
		//openQEq();
		//openlibMenu();
	}

	public void opneNotice(){
		final NoticePopup np = new NoticePopup(getContext(), Ui.cd.DPW, Ui.cd.DPH);
		addPopup(np);
		Ui.bk.add(new call(){
			@Override
			public void onCall(boolean bl) {
				removePopup(np);
			}
		});
	}

	float DownX;
	float DownY;
	boolean MoveD = false;
	boolean BaneD = false;
	public boolean TargetD = false;

	boolean downQEqOpen = false;
	boolean downQEqClose = false;
	boolean downMenuHomeOpen = false;
	boolean downMenuHomeClose = false;
	boolean downLibMenuOpen = false;
	boolean downLibMenuClose = false;
	boolean downPlaylistOpen = false;
	boolean downPlaylistClose = false;

	boolean onDown(MotionEvent event){

		DownX = event.getX();
		DownY = event.getY();
		Vx.clear();
		Vx.addMovement(event);

		downQEqOpen = false;
		downQEqClose = false;
		downLibMenuOpen = false;
		downLibMenuClose = false;
		downMenuHomeOpen = false;
		downMenuHomeClose = false;
		downPlaylistOpen = false;
		downPlaylistClose = false;

		if(Popup == false){
			if(QEq.isOpen == true){
				if(DownX < (Ui.cd.DPW - QEq.width)){
					downQEqClose = true;
				}
			}else if(libMenu.isOpen == true){
				 if(DownY < (Ui.cd.DPH - libMenu.height)){
					downLibMenuClose = true;
				}
			}else if(PlayLIstHome.isOpen == true){
				if(DownX > Ui.cd.DPW - (int)(Ui.cd.DPW * 0.15f)){
					downPlaylistClose = true;
				}
			}else if(MenuHome.isOpen == true){
				if(PlayLIstHome.isOpen){
					if(DownX > Ui.cd.DPW - (int)(Ui.cd.DPW * 0.15f)){
						downPlaylistClose = true;
					}
				}else if(QEq.isOpen){
					if(DownX < (Ui.cd.DPW - QEq.width)){
						downQEqClose = true;
					}
				}else if(DownX < Ui.cd.getHt(20)){
					downPlaylistOpen = true;
				}else if(DownX > Ui.cd.DPW - Ui.cd.getHt(20)){
					downQEqOpen = true;
				}else if(DownY < Ui.cd.getHt(50)){
					downMenuHomeClose = true;
				}else if(DownX > Ui.cd.getHt(20) && DownX < Ui.cd.DPW - Ui.cd.getHt(20) && DownY > Ui.cd.DPH - Ui.cd.getHt(60)){
					downLibMenuOpen = true;
				}
			}else if(DownX < Ui.cd.getHt(20)){
				downPlaylistOpen = true;
			}else if( DownX > Ui.cd.DPW - Ui.cd.getHt(20)){
				downQEqOpen = true;
			}else if(DownX > Ui.cd.getHt(20) && DownX < Ui.cd.DPW - Ui.cd.getHt(20) && DownY > Ui.cd.DPH - Ui.cd.getHt(60)){
				downMenuHomeOpen = true;
			}
		}

		MoveD = false;
		BaneD = false;
		TargetD = false;
		if(downMenuHomeOpen  || downPlaylistOpen || downPlaylistClose || downMenuHomeClose || downLibMenuOpen || downLibMenuClose || downQEqOpen || downQEqClose) {
			TargetD = true;
		}
		return true;
	}


	boolean onMove(MotionEvent event){

		if(downMenuHomeOpen  || downPlaylistOpen || downPlaylistClose || downMenuHomeClose || downLibMenuOpen || downLibMenuClose || downQEqOpen || downQEqClose) {
			Vx.addMovement(event);
			if (!MoveD && !BaneD) {
				int valx = (int) (DownX - event.getX());
				int valy = (int) (DownY - event.getY());
				if (Math.abs(valx) > Ui.cd.getHt(10) || Math.abs(valy) > Ui.cd.getHt(10)) {
					if(downPlaylistOpen && Math.abs(valy) > Ui.cd.getHt(10)){
						downPlaylistOpen = false;
						BaneD = true;
					}if(downQEqOpen && Math.abs(valy) > Ui.cd.getHt(10)){
						downQEqOpen = false;
						BaneD = true;
					}if(downLibMenuOpen && Math.abs(valx) > Ui.cd.getHt(10)){
						downLibMenuOpen = false;
						BaneD = true;
					}else{
						MoveD = true;
					}
				}
				if(BaneD){
					MoveD = false;
					return  true;
				}
			}else if(BaneD){
				MoveD = false;
				return  true;
			} else if(MoveD && !BaneD) {
				Vx.computeCurrentVelocity(100);
				if (downPlaylistOpen) {
					if (true) {
						float val = (event.getX()) - (PlayLIstHome.getWidth() + DownX);
						if (val < 0) {
							PlayLIstHome.setX(val);
						} else {
							PlayLIstHome.setX(0);
						}
					}
				} else if (downPlaylistClose) {
					if (DownX > Ui.cd.DPW - (int) (Ui.cd.DPW * 0.15f)) {
						float val = (event.getX()) - (DownX);
						if (val < 0) {
							PlayLIstHome.setX(val);
						} else {
							PlayLIstHome.setX(0);
						}
					}
				} else if (downMenuHomeOpen) {
					float val = Ui.cd.DPH + (event.getY()) - (DownY);
					if(val < -1){
						val = 0;
					}
					MenuHome.setY(val);
				}else if(downMenuHomeClose){
					float val = (event.getY()) - (DownY);
					if(val < -1){
						val = 0;
					}
					MenuHome.setY(val);
				}else if(downLibMenuOpen){
					float val = Ui.cd.DPH + (event.getY()) - (DownY);
					if(val < -1){
						val = 0;
					}else if(val < Ui.cd.DPH - libMenu.height){
						val = Ui.cd.DPH - libMenu.height;
					}
					libMenu.setY(val);
				}else if(downLibMenuClose){
					float val = (Ui.cd.DPH - libMenu.height) + (event.getY()) - (DownY);
					if(val < -1){
						val = 0;
					}else if(val < Ui.cd.DPH - libMenu.height){
						val = Ui.cd.DPH - libMenu.height;
					}
					libMenu.setY(val);
				}else if(downQEqOpen){
					float val = Ui.cd.DPW + (event.getX() - (DownX));
					if(val < Ui.cd.DPW - QEq.width){
						val = Ui.cd.DPW - QEq.width;
					}else if(val > Ui.cd.DPW){
						val = Ui.cd.DPW;
					}
					QEq.setX(val);
				}else if(downQEqClose){
					float val = (Ui.cd.DPW - QEq.width) + (event.getX() - (DownX));
					if(val < Ui.cd.DPW - QEq.width){
						val = Ui.cd.DPW - QEq.width;
					}else if(val > Ui.cd.DPW){
						val = Ui.cd.DPW;
					}
					QEq.setX(val);
				}
			}
		}
		return true;
	}

	void onUp(MotionEvent event){
		if(MoveD){
			Vx.computeCurrentVelocity(100);
			float valx = Vx.getXVelocity(0);
			float valy = Vx.getYVelocity(0);
			if(downPlaylistOpen || downPlaylistClose){
				boolean goOpen = false;
				if((int)Math.abs(valx) < 12){
					if( (int)(event.getX())  > (int)(Ui.cd.DPW * 0.85f) / 2){
						goOpen = true;
					}else{
						goOpen = false;
					}
				}else if(valx > 0){
					goOpen = true;
				}else{
					goOpen = false;
				}

				if(goOpen){
					if(!PlayLIstHome.isOpen){
						Ui.bk.add(new call() {
							@Override
							public void onCall(boolean bl) {
								PlayLIstHome.open(-(int)(Ui.cd.DPW * 0.85f),false);
							}
						});
					}
					PlayLIstHome.open(0,true);
				}else{
					if(PlayLIstHome.isOpen){
						PlayLIstHome.open(-(int)(Ui.cd.DPW * 0.85f),false);
						Ui.bk.dropLast();
					}else{
						PlayLIstHome.open(-(int)(Ui.cd.DPW * 0.85f),false);
					}

				}
			}else if(downQEqClose == true || downQEqOpen == true){
				boolean goOpen = false;
				if((int)Math.abs(valx) < 12){
					if( (int)(event.getX())  < (int)((QEq.width) / 2) + (Ui.cd.DPW - QEq.width)){
						goOpen = true;
					}else{
						goOpen = false;
					}
				}else if(valx < 0){
					goOpen = true;
				}else{
					goOpen = false;
				}

				if(goOpen){
					if(!QEq.isOpen){
						Ui.bk.add(new call() {
							@Override
							public void onCall(boolean bl) {
								QEq.open(Ui.cd.DPW,false);
							}
						});
					}
					QEq.open((Ui.cd.DPW - QEq.width),true);
				}else{
					if(QEq.isOpen){
						QEq.open(Ui.cd.DPW,false);
						Ui.bk.dropLast();
					}else{
						QEq.open(Ui.cd.DPW,false);
					}

				}
			}else if(downMenuHomeOpen || downMenuHomeClose){
				boolean goOpen = false;
				if((int)Math.abs(valy) < 12){
					if( (int)(event.getY())  <  Ui.cd.DPH  / 2){
						goOpen = true;
					}else{
						goOpen = false;
					}
				}else if(valy < 0){
					goOpen = true;
				}else{
					goOpen = false;
				}

				if(goOpen){
					openMusucLibrery();
				}else{
					if(MenuHome.isOpen){
						MenuHome.open(Ui.cd.DPH,false);
						Ui.bk.dropLast();
					}else{
						MenuHome.open(Ui.cd.DPH,false);
					}

				}
			}else if(downLibMenuOpen || downLibMenuClose){
				boolean goOpen = false;
				if((int)Math.abs(valy) < 12){
					if( (int)(event.getY())  <  ((Ui.cd.DPH - libMenu.height) + (libMenu.height / 2f))){
						goOpen = true;
					}else{
						goOpen = false;
					}
				}else if(valy < 0){
					goOpen = true;
				}else{
					goOpen = false;
				}

				if(goOpen){
					openlibMenu();
				}else{
					if(libMenu.isOpen){
						libMenu.open(Ui.cd.DPH,false);
						Ui.bk.dropLast();
					}else{
						libMenu.open(Ui.cd.DPH,false);
					}

				}
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(MoveD){
			return  false;
		}
		return super.onTouchEvent(event);
	}

	public boolean dispatchTouchEvent(MotionEvent event) {
		if(!canClick){
			return false;
		}
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				 onDown(event);
				break;
			case MotionEvent.ACTION_MOVE:
				if(!onMove(event)) {
					return  false;
				}
				break;
			case MotionEvent.ACTION_OUTSIDE:
				//onOut(event);
				break;
			case MotionEvent.ACTION_UP:
				onUp(event);
				if(MoveD){
					return  false;
				}
				break;
			default:
				//onleave(event);
		}
		return super.dispatchTouchEvent(event);
	}

	boolean Popup = false;
	public void addPopup(View vl) {
		Popup = true;
		addView(vl);
	}
	public void removePopup(View vl) {
		Popup = false;
		removeView(vl);
	}
}
