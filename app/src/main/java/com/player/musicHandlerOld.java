package com.player;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.provider.MediaStore;
import android.util.Log;

import com.linedeer.api.Event;
import com.linedeer.player.musicPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class musicHandlerOld {

	public playerDb db;
	public static List<int[]> playlist = new ArrayList<int[]>();
	public static List<int[]> randem = new ArrayList<int[]>();
	musicPlayer Gh;
	public Event mEvent;

	public musicHandlerOld(musicPlayer Gh) {
		mEvent = new Event("Player");
		this.Gh = Gh;
		db = new playerDb(Gh.getBaseContext());
		chEq();
		chBass();
		chSuround();
		loadReapet();
		loadshuffle();
		playlist = db.getList();
		
		if (playlist.size() != 0) {
			canPlay = true;
			CID = Integer.parseInt(db.getData(db.CID, db.CID_data));
			AID = playlist.get(CID)[0];
			
			int play_int = Integer.parseInt(db.getData("isPlaying", "1"));
			play();
			
			int pos = Integer.parseInt(db.getData("songPos", "0"));
			mediaplayer.seekTo(pos);
			
			if(play_int == 0){
				play = true;
				start();
			}else{
				play = false;
			}
		}
	}
	
	public void addSongs(int[] adids) {
		int len = playlist.size();
		for(int i = 0 ; i < adids.length;i++){
			playlist.add(new int[]{adids[i]});
		}
		
		if (len == 0) {
			canPlay = true;
			CID = Integer.parseInt(db.getData(db.CID, db.CID_data));
			AID = playlist.get(CID)[0];
			
			int play_int = Integer.parseInt(db.getData("isPlaying", "1"));
			play();
			
			int pos = Integer.parseInt(db.getData("songPos", "0"));
			mediaplayer.seekTo(pos);
			
			if(play_int == 0){
				play = true;
				start();
			}else{
				play = false;
			}
		}
	}
	
	public void reLoad(){
		playlist = new ArrayList<int[]>();
		stop();
		canPlay = false;
		Loaded = false;
		hasList = false;
	}
	
	public boolean play = false;
	public boolean shuffle = false;
	public boolean reapet = false;

	public boolean Switch() {
		if (canPlay || Loaded) {
			if (!Loaded) {
				play();
			}
			if (play) {
				pause();
			} else {
				start();
			}
		}
		return play;
	}

	public void stop(){
		if (canPlay || Loaded) {
			if (!Loaded) {
				play = false;
				return;
			}
			if (play) {
				pause();
			}
		}
	}
	
	public void pause() {
		play = false;
		mediaplayer.pause();
		mEvent.trigger(Event_Pause);
	}

	public void start() {
		play = true;
		mediaplayer.start();
		mEvent.trigger(Event_Start);
	}

	public int Event_Start = 1;
	public int Event_Pause = 2;
	public int Event_Data_Changed = 3;
	public int Event_Song_Load = 4;
	public int Event_LIST_CHANGED = 5;

	private void play() {
		if (canPlay || tempPlay) {
			tempPlay = false;
			getDetail(AID);
			Load(audioPath);
			mEvent.trigger(Event_Song_Load);
			if (Loaded) {
				if (play) {
					mediaplayer.start();
					mEvent.trigger(Event_Start);
				}
				chEq();
				chBass();
				chSuround();
				Log.i("My", "Song loaded sucsses fully...!");
			} else {
				Log.i("My", CID + "Song Not loaded = " + audioPath);
				next(false);
			}
		}
	}
	
	public boolean onEq = false;
	public Equalizer Eq;
	boolean eqSupport = false;
	boolean eqLoaded = false;
	public int eqId = -1;
	public int[] eqData = new int[5];
	public String eqName = "Custome";
	
	public void chEq() {
		if(!eqLoaded){
			eqLoaded = true;
			
			String eqStr = db.getData(db.eqStatus, db.eqStatus_data);
			if(eqStr.equals("1")){
				onEq = true;
			}else{
				onEq = false;
			}
			
			String eqIdStr = db.getData(db.eqId, db.eqId_data);
			eqId = Integer.parseInt(eqIdStr);
			
			String[] lastEq = db.getEq(eqId+"");
			String[] lastEqStr = lastEq[1].split(" ");
			
			eqName = lastEq[2];
			
			eqData[0] = Integer.parseInt(lastEqStr[0]);
			eqData[1] = Integer.parseInt(lastEqStr[1]);
			eqData[2] = Integer.parseInt(lastEqStr[2]);
			eqData[3] = Integer.parseInt(lastEqStr[3]);
			eqData[4] = Integer.parseInt(lastEqStr[4]);
		}

		if (onEq) {
			if (Loaded) {
				if (Eq == null) {
					try {
						Eq = new Equalizer(0, mediaplayer.getAudioSessionId());
						Eq.setProperties(new Equalizer.Settings("Equalizer;curPreset=-1;numBands=5;band1Level=0;band2Level=0;band3Level=0;band4Level=0;band5Level=0;"));
						Eq.setEnabled(true);
						eqSupport = true;
					} catch (UnsupportedOperationException un) {
						eqSupport = false;
						Eq = null;
					}
				} else {
					if (eqSupport) {
						if (Eq.getId() != mediaplayer.getAudioSessionId()) {
							Eq = new Equalizer(0,
									mediaplayer.getAudioSessionId());
							Eq.setEnabled(true);
							eqSupport = true;
						}
					}
				}
				if(Eq != null){
					Eq.setBandLevel((short)0, (short)eqData[0]);
					Eq.setBandLevel((short)1, (short)eqData[1]);
					Eq.setBandLevel((short)2, (short)eqData[2]);
					Eq.setBandLevel((short)3, (short)eqData[3]);
					Eq.setBandLevel((short)4, (short)eqData[4]);
					Log.i("Eq", Eq.getProperties().toString());
				}
			}
		}
	}
	
	public void offEq(){
		if(onEq){
			onEq = false;
			if (Eq != null) {
				if (Eq.getEnabled() == true) {
					Eq.setEnabled(false);
				}
				Eq = null;
			}
			db.setData(db.eqStatus,"0");
		}
		
	}
	
	public void loadEq(int id) {
		eqId = id;

		String[] lastEq = db.getEq(eqId + "");
		String[] lastEqStr = lastEq[1].split(" ");
		if (lastEqStr != null) {
			eqName = lastEq[2];

			eqData[0] = Integer.parseInt(lastEqStr[0]);
			eqData[1] = Integer.parseInt(lastEqStr[1]);
			eqData[2] = Integer.parseInt(lastEqStr[2]);
			eqData[3] = Integer.parseInt(lastEqStr[3]);
			eqData[4] = Integer.parseInt(lastEqStr[4]);
		}
		
		if(Eq != null){
			Eq.setBandLevel((short)0, (short)eqData[0]);
			Eq.setBandLevel((short)1, (short)eqData[1]);
			Eq.setBandLevel((short)2, (short)eqData[2]);
			Eq.setBandLevel((short)3, (short)eqData[3]);
			Eq.setBandLevel((short)4, (short)eqData[4]);
		}
		
		saveEq();
	}
	
	public void onEq(){
		if(!onEq){
			onEq = true;
			chEq();
			db.setData(db.eqStatus,"1");
		}
	}
	
	public void  setBand(int band,int val){
		eqData[band] = val;
		if (Eq != null) {
			Eq.setBandLevel((short) band, (short) val);
		}
	}
	
	public void saveEq(){
		db.setEq("1", eqData[0] +" "+eqData[1] +" "+eqData[2] +" "+eqData[3] +" "+eqData[4]);
		db.setData(db.lastEq, eqData[0] +" "+eqData[1] +" "+eqData[2] +" "+eqData[3] +" "+eqData[4]);
		db.setData(db.eqId, eqId+"");
	}
	
	
	BassBoost bassBoost;
	boolean bassLoaded;
	public boolean onBass = false;
	public int bassVal = 0;
	boolean bassSupport;
	
	public void chBass(){
		if(!bassLoaded){
			bassLoaded = true;
			
			String bassStr = db.getData(db.bassStatus, db.bassStatus_data);
			if(bassStr.equals("1")){
				onBass = true;
			}else{
				onBass = false;
			}
			
			String eqIdStr = db.getData(db.bassVal,db.bassVal_data);
			bassVal = Integer.parseInt(eqIdStr);
		}
		
		if (onBass) {
			if (Loaded) {
				if (bassBoost == null) {
					try {
						bassBoost = new BassBoost(0, mediaplayer.getAudioSessionId());
						bassBoost.setEnabled(true);
						bassSupport = true;
					} catch (UnsupportedOperationException un) {
						bassSupport = false;
						bassBoost = null;
					}
				} else {
					if (bassSupport) {
						if (bassBoost.getId() != mediaplayer.getAudioSessionId()) {
							bassBoost = new BassBoost(0,
									mediaplayer.getAudioSessionId());
							bassBoost.setEnabled(true);
							bassSupport = true;
						}
					}
				}
				if(bassBoost != null){
					bassBoost.setStrength((short) bassVal);
				}
			}
		}
	}
	
	public void offBass(){
		if(onBass){
			onBass = false;
			if (bassBoost != null) {
				if (bassBoost.getEnabled() == true) {
					bassBoost.setEnabled(false);
				}
				bassBoost = null;
			}
			db.setData(db.bassStatus,"0");
		}
		
	}
	
	public void onBass(){
		if(!onBass){
			onBass = true;
			chBass();
			db.setData(db.bassStatus,"1");
		}
	}

	public void  setBass(int strength){
		bassVal = strength;
		if (bassBoost != null) {
			bassBoost.setStrength((short) strength);
		}
	}
	
	public void saveBass(){
		db.setData(db.bassVal, bassVal+"");
	}

	Virtualizer suroundBoost;
	boolean suroundLoaded;
	public boolean onSuround = false;
	public int suroundVal = 0;
	boolean suroundSupport;
	
	public void chSuround(){
		if(!suroundLoaded){
			suroundLoaded = true;
			
			String suroundStr = db.getData(db.suroundStatus, db.suroundStatus_data);
			if(suroundStr.equals("1")){
				onSuround = true;
			}else{
				onSuround = false;
			}
			
			String eqIdStr = db.getData(db.suroundVal,db.suroundVal_data);
			suroundVal = Integer.parseInt(eqIdStr);
		}
		
		if (onSuround) {
			if (Loaded) {
				if (suroundBoost == null) {
					try {
						suroundBoost = new Virtualizer(0, mediaplayer.getAudioSessionId());
						suroundBoost.setEnabled(true);
						suroundSupport = true;
					} catch (UnsupportedOperationException un) {
						suroundSupport = false;
						suroundBoost = null;
					}
				} else {
					if (suroundSupport) {
						if (suroundBoost.getId() != mediaplayer.getAudioSessionId()) {
							suroundBoost = new Virtualizer(0,
									mediaplayer.getAudioSessionId());
							suroundBoost.setEnabled(true);
							suroundSupport = true;
						}
					}
				}
				if(suroundBoost != null){
					suroundBoost.setStrength((short) suroundVal);
				}
			}
		}
	}
	
	public void offSuround(){
		if(onSuround){
			onSuround = false;
			if (suroundBoost != null) {
				if (suroundBoost.getEnabled() == true) {
					suroundBoost.setEnabled(false);
				}
				suroundBoost = null;
			}
			db.setData(db.suroundStatus,"0");
		}
		
	}
	
	public void onSuround(){
		if(!onSuround){
			onSuround = true;
			chSuround();
			db.setData(db.suroundStatus,"1");
		}
	}

	public void  setSuround(int strength){
		suroundVal = strength;
		if (suroundBoost != null) {
			suroundBoost.setStrength((short) strength);
		}
	}
	
	public void saveSuround(){
		db.setData(db.suroundVal, suroundVal+"");
	}
	
	boolean tempPlay = false;
	public void playing(int aid) {
		AID = aid;
		play = true;
		tempPlay = true;
		play();
	}

	public void playFromList(int cid) {
		if(!canPlay){
			return;
		}
		play = true;
		CID = cid;
		AID = playlist.get(CID)[0];
		play();
	}

	public void next(boolean flag) {
		if(!canPlay){
			return;
		}
		CID++;
		if (CID == playlist.size() && flag) {
			CID = 0;
			AID = playlist.get(CID)[0];
			play();
		} else {
			if (CID == playlist.size()) {
				CID--;
			} else {
				AID = playlist.get(CID)[0];
				play();
			}
		}
	}
	
	public int getAid(int cid2) {
		if(!canPlay){
			return -1;
		}
		return playlist.get(cid2)[0];
	}
	
	public int getNow(){
		return AID;
	}
	
	public int getNext(int count){
		if(!canPlay){
			return -1;
		}
		int nowId = count;
		nowId++;
		if (nowId == playlist.size()) {
			return 0;
		} else {
			return nowId;
		}
	}
	
	public int getPrev(int count){
		if(!canPlay){
			return -1;
		}
		int nowId = count;
		nowId--;
		if (nowId == -1) {
			return playlist.size()-1;
		} else {
			return nowId;
		}
	}
	
	public void prev(boolean flag) {
		if(!canPlay){
			return;
		}
		CID--;
		if (CID == -1 && flag) {
			CID = playlist.size()-1;
			AID = playlist.get(CID)[0];
			play();
		} else {
			if (CID == -1) {
				CID++;
			} else {
				AID = playlist.get(CID)[0];
				play();
			}
		}
	}

	public String audioPath = "";
	public String Title = "";
	public String Artist = "";

	public void getDetail(int Id) {
		String[] projection = { MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.DATA };
		Cursor DataCursor = Gh.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				MediaStore.Audio.Media._ID + " = " + Id, null, null);
		if (DataCursor != null && DataCursor.getCount() != 0) {
			DataCursor.moveToNext();
			audioPath = DataCursor.getString(3);
			Title = DataCursor.getString(2);
			Artist = DataCursor.getString(1);
			DataCursor.close();
			// Log.i("My", "Ok massage is here:" + DataCursor.getString(2));
		} else if (DataCursor != null) {
			audioPath = "";
			Title = "";
			Artist = "";
			DataCursor.close();
		}
		mEvent.trigger(Event_Data_Changed);
	}

	public boolean hasList = false;
	public boolean canPlay = false;
	
	public int CID = -1;
	public int AID = -1;
	
	public void loadlist(int Id,String name){
		loadlist(Id);
		
		hasList = true;
	}
	
	public void resetRandom(){
		if(playlist.size() != 0){
			randem = new ArrayList<int[]>();
			for (int i = 0; i < playlist.size(); i++) {
				randem.add(new int[]{i});
			}
		}else{
			randem = new ArrayList<int[]>();
		}
	}
	
	public void loadlist(int Id) {
		playlist = new ArrayList<int[]>();
			String[] projection = { MediaStore.Audio.Playlists.Members.AUDIO_ID };
			Cursor DataCursor = Gh.getContentResolver().query(
					MediaStore.Audio.Playlists.Members.getContentUri(
							"external", Id), projection,
					MediaStore.Audio.Media.IS_MUSIC + " != 0 ", null, null);
			
			int length = DataCursor.getCount();

			for (int i = 0; i < length; i++) {
				DataCursor.moveToNext();
				// Log.i("My", Playlist);
				playlist.add(new int[] { DataCursor.getInt(0) });
			}
			DataCursor.close();
			CID = 0;
			if (length != 0) {
				canPlay = true;
				AID = playlist.get(CID)[0];
				resetRandom();
			} else { 
				canPlay = false;
			}
			mEvent.trigger(Event_LIST_CHANGED);
		    db.setData(db.Playid,Id+"");
	}

	public MediaPlayer mediaplayer;
	public boolean playing = false;

	public void checkPlayer() {
		if (mediaplayer == null) {
			mediaplayer = new MediaPlayer();
			mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					int[] ok;
					/*if(shuffle){
						if(randem.size() == 0 && Playlist.size() != 0){
							resetRandom();
							CID = Playlist.size();
						}
						if(randem.size() != 0){
							int min = 0;
							int max = randem.size();
							 
							ok = randem.get(new Random().nextInt(max - min + 1) + min);
							randem.remove(new Random().nextInt(max - min + 1) + min);
							playFromList(ok[0]);
						}
					}else{*/
						next(reapet);
					/*}*/
				}
			});
		}
	}
	
	public void saveReapet() {
		if(reapet){
			db.setData(db.reapet, "0");
		}else{
			db.setData(db.reapet, "1");
		}
	}
	
	public void loadReapet(){
		if(db.getData(db.reapet, db.reapet_data).equals("0")){
			reapet = true;
		}else{
			reapet = false;
		}
	}
	
	public void saveShuffle() {
		if(shuffle){
			db.setData(db.shuffle, "0");
		}else{
			db.setData(db.shuffle, "1");
		}
	}
	public void loadshuffle(){
		if(db.getData(db.shuffle, db.shuffle_data).equals("0")){
			shuffle = true;
		}else{
			shuffle = false;
		}
	}
	
	public boolean Loaded = false;
	

	public boolean Load(String File) {
		checkPlayer();
		Loaded = false;
		mediaplayer.reset();
		try {
			mediaplayer.setDataSource(File);
			mediaplayer.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		Loaded = true;
		return true;
	}

	public void saveList() {
		db.saveList(playlist);
		db.setData(db.CID, CID+"");
		
		int play_int = 1;
		if(play){
			play_int = 0;
		}
		db.setData("isPlaying", play_int+"");
		if(Loaded){
			db.setData("songPos", mediaplayer.getCurrentPosition()+"");
		}else{
			db.setData("songPos", "0");
		}
	}

	
}
