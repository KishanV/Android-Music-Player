package com.player;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.EnvironmentalReverb;
import android.media.audiofx.Equalizer;
import android.media.audiofx.LoudnessEnhancer;
import android.media.audiofx.Virtualizer;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.linedeer.api.Event;
import com.linedeer.player.Ui;
import com.linedeer.player.musicPlayer;
import com.player.data.EqlizerSettings;
import com.player.data.Playlist;


public class musicHandler {

	public boolean isPlaying() {
		if(mediaplayer != null){
			if(mediaplayer.isPlaying()){
				return true;
			}
		}
		return false;
	}

	public void flipPlaying() {
		if(mediaplayer != null){
			if(mediaplayer.isPlaying()){
				mediaplayer.pause();
			}else{
				mediaplayer.start();
			}
		}else{
			playByNumber(PID);
		}
		mEvent.trigger(playerEvents.PLAYING_FLIP);
	}

	public void stop() {
		if(mediaplayer != null){
		 	mediaplayer.pause();
		}
		mEvent.trigger(playerEvents.PLAYING_FLIP);
	}

	musicPlayer Gh;
	public Event mEvent;
	public MediaPlayer mediaplayer;


	public int AID = -1;
	public int PID = -1;
	public ArrayList<String[]> list = new ArrayList<String[]>();


	public Playlist playlist;
	void loadStartup(){
		playlist = new Playlist();
		list = playlist.songs;
		PID = 0;
		mEvent.trigger(playerEvents.PLAYLIST_CHANGED);
	}

	public EqlizerSettings EQs;
	public BassBoost bass;
	public Equalizer equlizer;
	public Virtualizer virtualizer;
	public EnvironmentalReverb presetreverb;
	public LoudnessEnhancer loudness;


	@TargetApi(Build.VERSION_CODES.KITKAT)
	public musicHandler(musicPlayer Gh) {
		mEvent = new Event("PLayer");
		this.Gh = Gh;
		loadStartup();
		mediaplayer = new MediaPlayer();
		mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				if(needPlay){
					isPrepared = true;
					play(needId);
				}else{
					isPrepared = true;
					if(needStop){
						needStop = false;
					}else{
						mediaplayer.start();
					}
					mediaplayer.setOnCompletionListener(OC);
					mEvent.trigger(playerEvents.SONG_CHANGED);
				}
			}
		});

		needStop = true;
		playByNumber(0);

		EQs = new EqlizerSettings(Gh.getBaseContext());

		bass = new BassBoost(0, mediaplayer.getAudioSessionId());
		bass.setEnabled(true);


		equlizer = new Equalizer(0, mediaplayer.getAudioSessionId());
		equlizer.setEnabled(true);

		virtualizer = new Virtualizer(0, mediaplayer.getAudioSessionId());
		virtualizer.setEnabled(true);

		if(android.os.Build.VERSION.SDK_INT >= 19){
			loudness = new LoudnessEnhancer(  mediaplayer.getAudioSessionId());
			loudness.setEnabled(true);
		}else{
			loudness = null;
		}


		setEQs(EQs.isOn);


		setBass(EQs.BASS);
		setTrable(EQs.TRABLE);
		setVoice(EQs.VOICE);
		setVirtualizer(EQs.VIRCHULIZER);
		setLoudnessEnhancer(EQs.LOUDNESS);
		mEvent.trigger(playerEvents.EQ_CHANGED);
		mEvent.trigger(playerEvents.PLAYER_COMPLETE);

	}

	public void resetEQs(){
		EQs.resetEQs();
		EQs.save();

		setBass(EQs.BASS);
		setTrable(EQs.TRABLE);
		setVoice(EQs.VOICE);
		setVirtualizer(EQs.VIRCHULIZER);
		setLoudnessEnhancer(EQs.LOUDNESS);

		setEQs(EQs.isOn);
	}

	public void setVirtualizer(int val) {
		EQs.VIRCHULIZER = val;
		virtualizer.setStrength((short) val);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void setLoudnessEnhancer(int val) {
		EQs.LOUDNESS = val;
		if(loudness != null){
			loudness.setTargetGain(val * 20);
		}

	}

	public void writeBands(){
		equlizer.setBandLevel((short) 0,(short)EQs.BAND_01);
		equlizer.setBandLevel((short) 1,(short)EQs.BAND_02);
		equlizer.setBandLevel((short) 2,(short)EQs.BAND_03);
		equlizer.setBandLevel((short) 3,(short)EQs.BAND_04);
		equlizer.setBandLevel((short) 4,(short)EQs.BAND_05);
	}

	public void stateBands(){
		EQs.BAND_01 = equlizer.getBandLevel((short) 0);
		EQs.BAND_02 = equlizer.getBandLevel((short) 1);
		EQs.BAND_03 = equlizer.getBandLevel((short) 2);
		EQs.BAND_04 = equlizer.getBandLevel((short) 3);
		EQs.BAND_05 = equlizer.getBandLevel((short) 4);
	}

	public void setEQs(boolean val) {
		EQs.isOn = val;
		if(val){
			bass.setEnabled(true);
			equlizer.setEnabled(true);
			virtualizer.setEnabled(true);
			if(loudness != null){
				loudness.setEnabled(true);
			}

			writeBands();
		}else{
			stateBands();
			bass.setEnabled(false);
			equlizer.setEnabled(false);
			virtualizer.setEnabled(false);
			if(loudness != null){
				loudness.setEnabled(false);
			}

		}
	}

	public void setPreset(int Id,String val) {

		String[] bandsValues = val.split(" ");
		EQs.EQ_PRESETS = Id;
		EQs.BAND_01 = Integer.parseInt(bandsValues[0]);
		setBass((int) ((100f / 1500f) * EQs.BAND_01));
		EQs.BAND_02 = Integer.parseInt(bandsValues[1]);
		setVoice((int) ((100f / 1500f) * EQs.BAND_02));
		EQs.BAND_03 = Integer.parseInt(bandsValues[2]);
		EQs.BAND_04 = Integer.parseInt(bandsValues[3]);
		EQs.BAND_05 = Integer.parseInt(bandsValues[4]);
		setTrable((int) ((100f / 1500f) * EQs.BAND_05));
		writeBands();

		EQs.BASS =  (int) (100f / 1500f  * equlizer.getBandLevel((short) 0));
		if(EQs.BASS > 0){
			int bVal = (int) (1000f / 100f   * EQs.BASS);
			bass.setStrength((short)bVal);
		}else{
			bass.setStrength((short) 0);
		}

		EQs.TRABLE =  (int) (100f / 1500f  * equlizer.getBandLevel((short) 4));
		EQs.VOICE =  (int) (100f / 1500f  * equlizer.getBandLevel((short) 1));
	}

	public void setPreset(int val) {
		equlizer.usePreset((short) val);
		EQs.EQ_PRESETS = val;

		EQs.BASS =  (int) (100f / 1500f  * equlizer.getBandLevel((short) 0));
		if(EQs.BASS > 0){
			int bVal = (int) (1000f / 100f   * EQs.BASS);
			bass.setStrength((short)bVal);
		}else{
			bass.setStrength((short) 0);
		}

		EQs.TRABLE =  (int) (100f / 1500f  * equlizer.getBandLevel((short) 4));
		EQs.VOICE =  (int) (100f / 1500f  * equlizer.getBandLevel((short) 1));

		stateBands();
	}

	public void setBass(int val) {
		EQs.BASS = val;
		int rVal = (int) (1500f / 100f   * val);
		if(rVal > 0){
			int bVal = (int) (1000f / 200f   * val);
			bass.setStrength((short)bVal);
		}else{
			bass.setStrength((short) 0);
		}
		equlizer.setBandLevel((short) 0,(short) rVal);
		EQs.BAND_01 = equlizer.getBandLevel((short) 0);
	}

	public void setTrable(int val) {
		EQs.TRABLE = val;
		int rVal = (int) (1500f / 100f   * val);
		equlizer.setBandLevel((short) 4,(short) rVal);
		EQs.BAND_05 = equlizer.getBandLevel((short) 4);
	}

	public void setVoice(int val) {
		EQs.VOICE = val;
		int rVal = (int) (1500f / 100f   * val);
		equlizer.setBandLevel((short) 1,(short) rVal);
		EQs.BAND_02 = equlizer.getBandLevel((short) 1);
	}


	public short getBandLavel(int band){
		return equlizer.getBandLevel((short) band);
	}

	public void setBandLavel(int band,int val){
	   equlizer.setBandLevel((short) band, (short) val);
		switch (band){
			case 0:
				EQs.BAND_01 = val;
				setBass((int) ((100f / 1500f) * val)) ;
				break;
			case 1:
				EQs.BAND_02 = val;
				setVoice((int) ((100f / 1500f) * val)) ;
				break;
			case 2:
				EQs.BAND_03 = val;
				break;
			case 3:
				EQs.BAND_04 = val;
				break;
			case 4:
				setTrable((int) ((100 / 1500f) * val)) ;
				EQs.BAND_05 = val;
				break;
		}
	}

	public int getAIDfrom(int no) {
		int val = PID + no;
		//Log.i("My","getAIDfrom :" + val);
		if(list == null){
			return -1;
		}
		if(val > list.size()-1 || val < 0){
			return  -1;
		}
		return Integer.parseInt( list.get(val)[1]);
	}
	public int getAID(int no) {
		int val =  no;
		//Log.i("My","getAIDfrom :" + val);
		if(list == null){
			return -1;
		}
		if(val > list.size()-1 || val < 0){
			return  -1;
		}
		return Integer.parseInt( list.get(val)[1]);
	}

	public String getPATHfrom(int no) {
		int val = PID + no;
		//Log.i("My","getAIDfrom :" + val);
		if(list == null){
			return "";
		}
		if(val > list.size()-1 || val < 0){
			return  "";
		}
		return  list.get(val)[3] ;
	}

	public void resetDefault(){
		AID = -1;
		PID = -1;
		mEvent.trigger(playerEvents.PLAYING_FLIP);
		isPrepared = true;
	}

	public void playALlSong(final int from){
		new Thread(new Runnable() {
			@Override
			public void run() {
				list = playlist.getAllSongAid();
				playlist.id = -1;
				playlist.save(list,"ALL SONGS");
				playByNumber(from);
				mEvent.trigger(playerEvents.PLAYLIST_CHANGED);
			}
		}).start();
	}

	public void playALlSong(final int from, final int[] ADIS, final String name){
		new Thread(new Runnable() {
			@Override
			public void run() {
				list = getSongsList(ADIS);
				playlist.id = -1;
				playlist.save(list,name);
				mEvent.trigger(playerEvents.PLAYLIST_CHANGED);
				playByNumber(from);
			}
		}).start();
	}

	public void playByPlaylistId(String str){
		playByPlaylistId(str,0);
	}

	public void playByPlaylistId(String str,int No){
		int count = playlistHandler.getPlaylistLength(Ui.ef.getBaseContext().getContentResolver(),No);
		if(count == 0 || count == -1){
			Toast toast = Toast.makeText(Gh.getBaseContext(), "Playlist is Empty.", Toast.LENGTH_SHORT);
			View view = toast.getView();
			view.setBackgroundColor(0xFFD35D69);
			toast.show();
			return;
		}
		resetDefault();
		list = playlistHandler.getPlaylistByIds(Gh.getContentResolver(),Integer.parseInt(str));
		if(list != null && list.size() != 0){
			PID = -1;
			if(list != null && list.size() > 0){
				AID =  Integer.parseInt( list.get(0)[1]) ;
			} else {
				AID = -1;
			}
		}

		playlist.listName = playlistHandler.getPlaylistNameById(Gh.getContentResolver(),Integer.parseInt(str));
		playlist.id = Integer.parseInt(str);
		playlist.save(list, playlist.listName);
		songPrepared = false;
		playByNumber(No);
		mEvent.trigger(playerEvents.PLAYLIST_CHANGED);
	}


	public void playByNumber(int id) {
		if(list.size() != 0){
			if(id != PID || !isPlaying()){
				PID = id;
				play( Integer.parseInt( list.get(PID)[1])) ;
			}
		}
	}

	public void  playNext(){

		int id = PID + 1;
		if(id > list.size() - 1){
			id = list.size() - 1;
		}
		playByNumber(id);
	}

	public void  playPrevious(){
		int id = PID - 1;
		if(id < 0){
			id = 0;
		}
		playByNumber(id);
	}

	MediaPlayer.OnCompletionListener OC = new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			playNext();
		}
	};

	public void forScrollDown(){
		mediaplayer.setOnCompletionListener(null);
	}

	public void forScrollUp(){
		mediaplayer.setOnCompletionListener(OC);
		if(mediaplayer.getDuration() == mediaplayer.getCurrentPosition()){
			playNext();
		}
	}

	boolean isPrepared = true;
	public boolean songPrepared = false;
	boolean needPlay = false;
	boolean needStop = false;
	int needId = -1;

	void play(final int id) {
		new Thread(){
			@Override
			public void run() {
				if(isPrepared){
					isPrepared = false;
					songPrepared = true;
					needPlay = false;
					MediaPlayer Nm = mediaplayer;
					mediaplayer.reset();
					mediaplayer.setOnCompletionListener(null);
					AID = id;
					try {
						Nm.setDataSource(getAudiopath(id));
						Nm.prepareAsync();
					} catch (Exception e) {
						isPrepared = true;
						needPlay = true;
						needId = id;
					}
				}else{
					needPlay = true;
					needId = id;
				}
			}
		}.start();

	}

	public String getAudiopath(int Id) {
		String[] projection = { MediaStore.Audio.Media.DATA };
		Cursor DataCursor = Gh.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				MediaStore.Audio.Media._ID + " = " + Id, null, null);

		DataCursor.moveToNext();
		String audioPath = DataCursor.getString(0);
		DataCursor.close();
		return audioPath;
	}

	public void addSongs(int[] Id) {

		String str = "";
		for(int i = 0;i < Id.length;i++){
			str += MediaStore.Audio.Media._ID + " = " + Id[i];
			if(Id.length-1 != i){
				str += " OR ";
			}
		}

		String[] projection = {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DURATION};
		Cursor DataCursor = Gh.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				str, null, null);

		HashMap<Integer,String[]> nData = new HashMap<Integer,String[]>();
		while (DataCursor.moveToNext()) {
			int nId = DataCursor.getInt(1);
			nData.put(nId,new String[] {DataCursor.getString(0),DataCursor.getString(1),DataCursor.getString(2),""});
			//list.add(new String[] {DataCursor.getString(0),DataCursor.getString(1),DataCursor.getString(2),""});
		}

		DataCursor.close();

		for(int i = 0;i < Id.length;i++){
			 if(nData.containsKey(Id[i])){
				 list.add(nData.get(Id[i]));
			 }
		}

		mEvent.trigger(playerEvents.SONGS_ADDED);

		playlist.save(list, playlist.listName);

	}

	public void addSongsNext(int[] Id) {
		String str = "";
		for(int i = 0;i < Id.length;i++){
			Log.i("My","ADD NEXT : " + Id[i]) ;
			str += MediaStore.Audio.Media._ID + " = " + Id[i];
			if(Id.length-1 != i){
				str += " OR ";
			}
		}

		String[] projection = {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DURATION};
		Cursor DataCursor = Gh.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				str, null, MediaStore.Audio.Media.TITLE +" COLLATE NOCASE ASC ");
		int from = PID + 1;
		if(PID == 0  || PID == -1){
			from = list.size();
		}
		while (DataCursor.moveToNext()) {
			list.add(from,new String[] {DataCursor.getString(0),DataCursor.getString(1),DataCursor.getString(2),""});
			from++;
		}

		DataCursor.close();

		mEvent.trigger(playerEvents.SONGS_ADDED);

		playlist.save(list, playlist.listName);
	}

	public ArrayList<String[]> getSongsList(int[] Id) {
		String str = "";
		for(int i = 0;i < Id.length;i++){
			str += MediaStore.Audio.Media._ID + " = " + Id[i];
			if(Id.length-1 != i){
				str += " OR ";
			}
		}

		String[] projection = {MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DURATION};
		Cursor DataCursor = Gh.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				str, null, MediaStore.Audio.Media.TITLE +" COLLATE NOCASE ASC ");
		ArrayList<String[]> list = new ArrayList<String[]>();

		HashMap<Integer,String[]> nData = new HashMap<Integer,String[]>();

		while (DataCursor.moveToNext()) {
			int nId = DataCursor.getInt(1);
			nData.put(nId,new String[] {DataCursor.getString(0),DataCursor.getString(1),DataCursor.getString(2),""});
			//list.add(new String[] {DataCursor.getString(0),DataCursor.getString(1),DataCursor.getString(2),""});
		}

		DataCursor.close();


		for(int i = 0;i < Id.length;i++){
			if(nData.containsKey(Id[i])){
				list.add(nData.get(Id[i]));
			}
		}
		return list;
	}

	public void playlistMode() {
		mEvent.trigger(playerEvents.PLAYLIST_MODE);
	}
}
