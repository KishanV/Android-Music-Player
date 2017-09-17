package com.linedeer.player;

import com.linedeer.api.EventCall;

import com.linedeer.music.handler.R;
import com.player.audioHandler;
import com.player.musicHandler;
import com.player.playerEvents;
import com.player.services.servicesBinder;
import com.shape.notification.closeBtn;
import com.shape.notification.nextBtn;
import com.shape.notification.pauseBtn;
import com.shape.notification.playBtn;
import com.shape.notification.prevBtn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;

import Views.radiusSqure;

public class musicPlayer extends Service{
	
	IBinder binder;
	public static  musicPlayer THIS;
	public musicPlayer() {
		binder = new servicesBinder(this);
	}
	public musicHandler handler;
	SettingsContentObserver SCo;

	boolean byCall;
	@Override
	public void onCreate() {

		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(getBaseContext().TELEPHONY_SERVICE);
		PhoneStateListener callStateListener = new PhoneStateListener() {
			public void onCallStateChanged(int state, String incomingNumber)
			{
				if(state==TelephonyManager.CALL_STATE_RINGING){
					//Toast.makeText(getApplicationContext(),"Phone Is Riging", Toast.LENGTH_LONG).show();
					if(handler.mediaplayer.isPlaying()){
						byCall = true;
						handler.mediaplayer.pause();
					}
				}

				if(state==TelephonyManager.CALL_STATE_OFFHOOK){
					//Toast.makeText(getApplicationContext(),"Phone is Currently in A call", Toast.LENGTH_LONG).show();
					if(handler.mediaplayer.isPlaying()){
						byCall = true;
						handler.mediaplayer.pause();
					}
				}

				if(state==TelephonyManager.CALL_STATE_IDLE){
					//Toast.makeText(getApplicationContext(),"phone is neither ringing nor in a call", Toast.LENGTH_LONG).show();
					if(byCall){
						byCall = false;
						handler.mediaplayer.start();
					}
				}
			}
		};
		telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_CALL_STATE);
		mSettingsContentObserver = new SettingsContentObserver( new Handler() );
		this.getContentResolver().registerContentObserver( android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver );


		THIS = this;
		handler = new musicHandler(this);
		super.onCreate();
		//statusBar();

		handler.mEvent.addEvent(new EventCall(new int[]{playerEvents.PLAYER_COMPLETE,playerEvents.SONG_CHANGED,playerEvents.PLAYING_FLIP}){
			@Override
			public void onCall(final int eventId) {
				new Thread(){
					@Override
					public void run() {
						showNotification(eventId);
					}
				}.start();
			}
		});

		showNotification(-1);
	}
	SettingsContentObserver mSettingsContentObserver;

	public class SettingsContentObserver extends ContentObserver {

		public SettingsContentObserver(Handler handler) {
			super(handler);
	}

		@Override
		public boolean deliverSelfNotifications() {
			return super.deliverSelfNotifications();
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			AudioManager audio = (AudioManager) musicPlayer.this.getBaseContext().getSystemService(Context.AUDIO_SERVICE);
			int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
			Log.i("My","Ok html...!" + currentVolume);
			handler.mEvent.trigger(playerEvents.VOLUME_CHANGE);
		}
	}

	@Override
	public void onDestroy() {
		notification = null;
		this.getContentResolver().unregisterContentObserver( mSettingsContentObserver );
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String cmd = null;
		if(intent != null){
			cmd = intent.getAction();
		}
		if(cmd != null){
			if (cmd.equals("next")) {
				handler.playNext();
			}else if (cmd.equals("prev")) {
				handler.playNext();
			}else if (cmd.equals("pause")) {
				handler.flipPlaying();
			}else if (cmd.equals("close")) {
				handler.stop();
				handler.mEvent.trigger(playerEvents.PLAYER_EXIT);
				/*Intent notificationIntent = new Intent(this, Ui.class);
				notificationIntent.setAction(Intent.ACTION_MAIN);
				notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
				try {
					pendingIntent.send();
				} catch (PendingIntent.CanceledException e) {
					e.printStackTrace();
				}*/
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public android.app.Notification notification;


	Notification status;
	private final String LOG_TAG = "NotificationService";

	RemoteViews views;
	Bitmap nextBtn;
	Bitmap pauseBtn;
	Bitmap prevBtn;
	Bitmap playBtn;
	Bitmap closeBtn;
	Bitmap rbBtn;

	int NAID;


	private void showNotification(int Id) {

		int AID = handler.getAIDfrom(0);
		if(NAID == AID && Id != playerEvents.PLAYING_FLIP){
			return;
		}
		NAID = AID;
		if(views == null){
			views =  new RemoteViews(getPackageName(), R.layout.status_bar);
			radiusSqure rs = new radiusSqure(getDPI(10),getDPI(10),0,0,getDPI(0));
			rs.setColor(0xFF352E4D);
			rbBtn = rs.getBitmap();

			closeBtn = new closeBtn(getDPI(40),getDPI(64),0,0).getBitmap();
			pauseBtn = new pauseBtn(getDPI(60),getDPI(60),0,0).getBitmap();
			playBtn = new playBtn(getDPI(60),getDPI(60),0,0).getBitmap();
			nextBtn = new nextBtn(getDPI(50),getDPI(64),0,0).getBitmap();
			prevBtn = new prevBtn(getDPI(50),getDPI(64),0,0).getBitmap();
		}



		Bitmap bm = audioHandler.getAlubumArtBitmapById(getContentResolver(),AID);
		Bitmap BM = Bitmap.createBitmap(getDPI(60),getDPI(60),Bitmap.Config.ARGB_8888);
		Canvas cv = new Canvas(BM);
		radiusSqure rClip = new radiusSqure(getDPI(60),getDPI(60),0,0,getDPI(7));
		cv.clipPath(rClip.S0);
		if(bm != null){
			cv.drawBitmap(bm,0,0,null);
			bm.recycle();
		}

		views.setImageViewBitmap(R.id.Icon,BM);

		//views.setImageViewBitmap(R.id.IconRing,rb.getBitmap());
		//sviews.setImageViewBitmap(R.id.mainBackground,rs.getBitmap());
		/*views.setImageViewBitmap(R.id.closeeBtn,new closeBtn(getDPI(40),getDPI(64),0,0).getBitmap());
		views.setImageViewBitmap(R.id.pauseBtn,new pauseBtn(getDPI(60),getDPI(60),0,0).getBitmap());
		views.setImageViewBitmap(R.id.nextBtn,new nextBtn(getDPI(50),getDPI(64),0,0).getBitmap());
		views.setImageViewBitmap(R.id.prevBtn,new prevBtn(getDPI(50),getDPI(64),0,0).getBitmap());
		*/

		views.setImageViewBitmap(R.id.closeeBtn,closeBtn);
		if(handler.isPlaying()){
			views.setImageViewBitmap(R.id.pauseBtn,pauseBtn);
		}else{
			views.setImageViewBitmap(R.id.pauseBtn,playBtn);
		}


		views.setImageViewBitmap(R.id.nextBtn,nextBtn);
		views.setImageViewBitmap(R.id.prevBtn,prevBtn);
		views.setTextViewText(R.id.title,"AID : " + AID);
		views.setTextViewText(R.id.artist,"Artist Here.");

		Intent nextIntent = new Intent(this, musicPlayer.class);
		nextIntent.setAction("next");
		PendingIntent pnextIntent = PendingIntent.getService(this, 0, nextIntent, 0);
		views.setOnClickPendingIntent(R.id.nextBtn,pnextIntent);

		Intent prevIntent = new Intent(this, musicPlayer.class);
		prevIntent.setAction("prev");
		PendingIntent pprevIntent = PendingIntent.getService(this, 0, prevIntent, 0);
		views.setOnClickPendingIntent(R.id.prevBtn,pprevIntent);

		Intent pauseIntent = new Intent(this, musicPlayer.class);
		pauseIntent.setAction("pause");
		PendingIntent ppauseIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
		views.setOnClickPendingIntent(R.id.pauseBtn,ppauseIntent);

		Intent closeIntent = new Intent(this, musicPlayer.class);
		closeIntent.setAction("close");
		PendingIntent pcloseIntent = PendingIntent.getService(this, 0, closeIntent, 0);
		views.setOnClickPendingIntent(R.id.closeeBtn,pcloseIntent);


		Intent notificationIntent = new Intent(this, Ui.class);
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setOngoing(true);

		notification = builder.build();
		notification.contentView = views;
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.icon = R.drawable.notificationicon;
		notification.contentIntent = pendingIntent;

		//status.bigContentView = bigViews;
		startForeground(1, notification);
	}

	float DPI = 0;
	int getDPI(int val){
		if(DPI == 0){
			DPI = getBaseContext().getResources().getDisplayMetrics().density;
		}
		return (int)(DPI * val);
	}

	public void statusBar(){
		Intent intent = new Intent(this,Ui.class);
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		android.app.PendingIntent pi = android.app.PendingIntent.getActivity(this, 0, intent,   android.app.PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("Doodle handler starting.. \n Spidre Inc.");
		builder.setContentText("Open musicPlayer");
		builder.setContentIntent(pi);
		builder.setOngoing(true);

		notification = builder.build();

		// optionally set a custom view

		startForeground(1, notification);
	}

	public void statusBarNew(){
		String strtitle = "Music PLayer";
		String strtext = "MUSIC PLAYER BY LINEDEER.";


		Intent intent = new Intent(this, Ui.class);
		intent.putExtra("title", strtitle);
		intent.putExtra("text", strtext);
		// Open NotificationView.java Activity
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		//Create Notification using NotificationCompat.Builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				// Set Icon
				.setSmallIcon(R.drawable.ic_launcher)
				// Set Ticker Message
				.setTicker(strtitle)
				// Set Title
				.setContentTitle(strtext)
				// Set Text
				.setContentText("NOW PLAYING.!")
				// Add an Action Button below Notification
				.addAction(R.drawable.ic_launcher, "Action Button", pIntent)
				// Set PendingIntent into Notification
				.setContentIntent(pIntent)
				// Dismiss Notification
				.setAutoCancel(true);

		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		notification =  builder.build();
		notificationmanager.notify(0,notification);
	}


}