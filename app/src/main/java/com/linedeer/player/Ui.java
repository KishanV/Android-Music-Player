package com.linedeer.player;

import com.linedeer.music.handler.R;
import com.player.data.catchBase;
import com.player.playerEvents;
import com.player.services.servicesBinder;
import com.linedeer.api.Backpress;
import com.linedeer.api.Event;
import com.linedeer.api.EventCall;
import com.themes.Theme;

import Views.ContentHome;
import Views.api.FMlyt;
import Views.api.config;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Ui extends Activity {
	public static config cd;
	public static Ui ef;
	public static Backpress bk;
	public static Theme th = new Theme();

	public int Event_onPause = 101;
	public int Event_onResume = 102;
	public int Event_onDestroy = 103;
	public int Event_onBind = 104;


	MediaPlayer mp;
	public static void CleanUpMemory(){
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
	}


	public void clickPlay(){
		mp.start();
	}

	public Ui() {
		playerEvent = new Event("Activity");
	}

	@Override
	public void onBackPressed() {
		if(bk.back()){
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	protected void onResume() {
		playerEvent.trigger(Event_onResume);
		super.onResume();
	}

	@Override
	protected void onPause() {
		playerEvent.trigger(Event_onPause);
		super.onPause();
	}

	catchBase data;
	public Event playerEvent;

	int width = 0;
	int height = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ef = Ui.this;
		mp = MediaPlayer.create(getBaseContext(), R.raw.tick);


		data = new catchBase(getBaseContext(),"appSetting","1"){
			@Override
			public void onRead(DataInputStream din) throws IOException {
				//super.onRead(din);
				width = din.readInt();
				height = din.readInt();
			}

			@Override
			public void onWrite(DataOutputStream dout) throws IOException {
				//super.onWrite(dout);
				dout.writeInt(width);
				dout.writeInt(height);
			}
		};
		data.readCatch();

		checkPermission();

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
		checkPermission();
	}

	void checkPermission(){

		if (ContextCompat.checkSelfPermission(Ui.ef, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions( Ui.ef, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
		} else{
			init();
		}
	}

	void init(){
		if(width == 0 || height == 0){
			final View V = new View(getBaseContext());
			setContentView(V);
			V.getRootView().setBackgroundResource(0);
			V.setBackgroundResource(0);
			V.post(new Runnable() {
				@Override
				public void run() {
					width = V.getWidth();
					height = V.getHeight();
					ef = Ui.this;
					cd = new config(getBaseContext(),V.getWidth(),V.getHeight());
					data.saveCatch();
					bk = new Backpress();
					ONDONE();
				}
			});
		}else{
			cd = new config(getBaseContext(),width,height);
			bk = new Backpress();
			ONDONE();
		}
	}

	public ContentHome MH;
	Intent servInt;
	public musicPlayer MusicPlayer;

	FMlyt hider;
	protected void ONDONE() {
		MH = new ContentHome(getBaseContext());
		setContentView(MH);
		MH.init();
		MH.getRootView().setBackgroundResource(0);

		hider = new FMlyt(getBaseContext(), cd.DPW, cd.DPH);
		hider.setClickable(true);
		//MH.addView(hider);

		servInt = new Intent(getBaseContext(), musicPlayer.class);
		startService(servInt);
		bindService(servInt, Sc, BIND_ADJUST_WITH_ACTIVITY);


	}

	public class myPhoneStateChangeListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			if (state == TelephonyManager.CALL_STATE_RINGING) {
				String phoneNumber =   incomingNumber;
			}
		}
	}

	EventCall playerCall = new EventCall(null) {
		@Override
		public void onCall(final int arg) {
			if(arg == playerEvents.PLAYER_EXIT){
				Ui.this.finish();
			}else{
				Ui.ef.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						playerEvent.trigger(arg);
					}
				});
			}
		}
	};

	ServiceConnection Sc = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			servicesBinder mLocalBinder = (servicesBinder)service;
			MusicPlayer = mLocalBinder.getServices();
			MusicPlayer.handler.mEvent.addEvent(playerCall);
			playerEvent.trigger(Event_onBind);
			MH.removeView(hider);
			//Log.i("My", "Massage bined..!");
		}
	};

	boolean needD = false;
	@Override
	protected void onDestroy() {
		if(MusicPlayer.handler.isPlaying()){
			needD = false;
		}else{
			needD = true;
		}
		playerEvent.trigger(Event_onDestroy);
		MusicPlayer.handler.mEvent.removeEvent(playerCall);

		unbindService(Sc);
		if(needD){
			stopService(servInt);
		}

		super.onDestroy();
	};


}
