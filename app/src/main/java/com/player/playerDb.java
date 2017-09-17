package com.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class playerDb extends SQLiteOpenHelper {
	public static int DATABASE_VERSION = 7;
	private static final String DATABASE_NAME = "musicHandler";

	public SQLiteDatabase db;

	public playerDb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = getWritableDatabase();
	}

	public  final String LISTIDS = "ListIds";

	public  final String Id = "id";
	public  final String Aid = "aid";
	public  final String ListId = "listid";
	public  final String Data = "playlist";
	public  final String Name = "listName";
	public  final String Type = "Type";
	public final String QUE = "Que";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//Log.i("My", "ok is");
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Data + "(" + Id + " TEXT," + Data + " TEXT " + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		
		CREATE_CONTACTS_TABLE = "CREATE TABLE " + EQ + "(" + Id + " INTEGER PRIMARY KEY," + Name + " TEXT,"+ Type + " TEXT,"+ Data + " TEXT " + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		
		CREATE_CONTACTS_TABLE = "CREATE TABLE " + QUE + "(" + Id + " INTEGER PRIMARY KEY," + Aid + " INTEGER" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
		
		this.db = db;
		initEqs();
	}
	
	public void addSond(String AID){
		ContentValues Cv = new ContentValues();
		Cv.put(Aid, AID);	
		db.insert(QUE,null,Cv);
	}
	
	public void saveList(List<int[]> playlist) {
		db.delete(QUE, null, null);
		if(playlist.size() != 0){
			int length = playlist.size();
			for (int i = 0; i < length; i++) {
				ContentValues Cv = new ContentValues();
				Cv.put(Aid, playlist.get(i)[0]);	
				db.insert(QUE,null,Cv);
			}
		}
		Log.i("My", "Save song....!");
	}
	
	public List<int[]> getList() {
		List<int[]> playlist = new ArrayList<int[]>();
		Cursor cursor = db.query(QUE, new String[] { Aid},null, null, null, null, null, null);
		
		if(cursor.getCount() == 0){
			return playlist;
		}
		int length = cursor.getCount();
		for (int i = 0; i < length; i++) {
			cursor.moveToNext();
			playlist.add(new int[]{cursor.getInt(0)});
		}
		return playlist;
	}
	
	
	public  final String EQ = "EQ";
	
	public void initEqs(){
		addEd("Custome","1","0 0 0 0 0");
		addEd("Classica","1",EV(0f,0f)+" " +EV(0f,0f)+" "+EV(0f,0f)+" "+EV(-7.1f,-7.1f)+" "+EV(-7.1f,-9.6f));
		addEd("Club","1",EV(0f,0f)+" " +EV(7.9f,5.5f)+" "+EV(5.5f,5.5f)+" "+EV(3.2f,0f)+" "+EV(0f,0f));
		addEd("Dance","1",EV(9.6f,7.1f)+" " +EV(2.4f,0f)+" "+EV(0f,-5.5f)+" "+EV(-7.1f,-7.1f)+" "+EV(0f,0f));
		addEd("Full bass","1",EV(-7.9f,9.6f)+" " +EV(9.6f,5.5f)+" "+EV(1.6f,-3.9f)+" "+EV(-7.9f,-10.3f)+" "+EV(-11.1f,-11.1f));
		addEd("Full bass and treble","1",EV(7.1f,5.5f)+" " +EV(0f,-7.1f)+" "+EV(-4.8f,1.6f)+" "+EV(7.9f,11.1f)+" "+EV(11.9f,11.9f));
		addEd("Full treble","1",EV(-9.6f,-9.6f)+" " +EV(-9.6f,-3.9f)+" "+EV(2.4f,11.1f)+" "+EV(15.9f,15.9f)+" "+EV(15.9f,16.7f));
		addEd("Headphones","1",EV(4.8f,11.1f)+" " +EV(5.5f,-3.2f)+" "+EV(-2.4f,1.6f)+" "+EV(4.8f,9.6f)+" "+EV(12.8f,14.3f));
		addEd("Large hall","1",EV(10.3f,10.3f)+" " +EV(5.5f,5.5f)+" "+EV(0f,-4.8f)+" "+EV(-4.8f,-4.8f)+" "+EV(0f,0f));
		addEd("Live","1",EV(-4.8f,0f)+" " +EV(3.9f,5.5f)+" "+EV(5.5f,5.5f)+" "+EV(3.9f,2.4f)+" "+EV(2.4f,2.4f));
		addEd("Party","1",EV(7.1f,7.1f)+" " +EV(0f,0f)+" "+EV(0f,0f)+" "+EV(0f,0f)+" "+EV(7.1f,7.1f));
		addEd("Reggae","1",EV(0f,0f)+" " +EV(0f,-5.5f)+" "+EV(0f,6.4f)+" "+EV(6.4f,0f)+" "+EV(0f,0f));
		addEd("Rock","1",EV(7.9f,4.8f)+" " +EV(-5.5f,-7.9f)+" "+EV(-3.2f,3.9f)+" "+EV(8.8f,11.1f)+" "+EV(11.1f,11.1f));
		addEd("Ska","1",EV(-2.4f,-4.8f)+" " +EV(-3.9f,0f)+" "+EV(3.9f,5.5f)+" "+EV(8.8f,9.6f)+" "+EV(11.1f,9.6f));
		addEd("Soft","1",EV(4.8f,1.6f)+" " +EV(0f,-2.4f)+" "+EV(0f,3.9f)+" "+EV(7.9f,9.6f)+" "+EV(11.1f,11.9f));
		addEd("Soft rock","1",EV(3.9f,3.9f)+" " +EV(2.4f,0f)+" "+EV(-3.9f,-5.5f)+" "+EV(-3.2f,0f)+" "+EV(2.4f,8.8f));
		addEd("Techno","1",EV(7.9f,5.5f)+" " +EV(0f,-5.5f)+" "+EV(-4.8f,0f)+" "+EV(7.9f,9.6f)+" "+EV(9.6f,8.8f));
	}
	
	public int EV(float A,float B){
		return (int)((((A+B)*(30f/40f))/2f)*100f);
	}
	
	public String[] getEq(String id) {
		Cursor cursor = db.query(EQ, new String[] { Id,Data,Name,Type}, Id + "=?",
				new String[] { id }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			return new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)};
		}
		cursor.close();
		
		cursor = db.query(EQ, new String[] { Id,Data,Name,Type}, null,
				null, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			setData(eqId, "0");
			return new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)};
		}
		cursor.close();
		return null;
	}
	
	public String[][] getEqs() {
		Cursor cursor = db.query(EQ, new String[] { Id,Name,Type,Data}, null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() != 0) {
			int len = cursor.getCount();
			String Data[][] = new String[len][];
			for (int i = 0; i < len; i++) {
				cursor.moveToNext();
				Data[i] =  new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)};
			}
			return Data;
		} else {
			return null;
		}
	}
	
	public void addEd(String Nm,String Bool,String Dt){
		ContentValues values = new ContentValues();
		values.put(Name,Nm);
		values.put(Type,Bool);
		values.put(Data,Dt);
		db.insert(EQ, Name+"!="+Nm, values);
	}
	
	public void removeEqs(String aID2){
		db.delete(EQ,Id+"="+aID2,null);
	}
	
	public void setEq(String id,String Dt){
		
		ContentValues values = new ContentValues();
		values.put(Data,Dt);
		db.update(EQ, values,Id + "=" + id, null);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + Data);
		db.execSQL("DROP TABLE IF EXISTS " + EQ);
		db.execSQL("DROP TABLE IF EXISTS " + QUE);
		onCreate(db);
	}

	public String getData(String key, String data) {
		Cursor cursor = db.query(Data, new String[] { Data }, Id + "=?",
				new String[] { key }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			return cursor.getString(0);
		} else {
			ContentValues values = new ContentValues();
			values.put(Id, key);
			values.put(Data, data);
			db.insert(Data, null, values);
			return data;
		}
	}

	public void setData(String key, String data) {
		ContentValues values = new ContentValues();
		values.put(Data, data);
		int No = db.update(Data, values, Id + "=?", new String[] { key });
		Log.i("My", "Massage of db = " + No);
		if(No == 0){
			values = new ContentValues();
			values.put(Id, key);
			values.put(Data, data);
			db.insert(Data, null, values);
		}
	}
	
	final public  String Demo = "demo";
	final public  String DemoData = "KishanDevani";
	
	final public  String Playid = "Playlist";
	final public  String Playid_data = "";
	
	final public  String eqStatus = "eqstatus";
	final public  String eqStatus_data = "1";
	
	final public  String eqId = "eqId";
	final public  String eqId_data = "1";
	
	
	final public  String lastEq = "lastEq";
	final public  String lastEq_data = "0 0 0 0 0";
	
	final public  String bassStatus = "bassstatus";
	final public  String bassStatus_data = "0";
	
	final public  String bassVal = "bassval";
	final public  String bassVal_data = "0";
	
	
	final public  String suroundStatus = "suroundtatus";
	final public  String suroundStatus_data = "0";
	
	final public  String suroundVal = "suroundval";
	final public  String suroundVal_data = "0";
	
	final public  String reapet = "reapet";
	final public  String reapet_data = "0";
	
	final public  String shuffle = "shuffle";
	final public  String shuffle_data = "0";
	
	final public  String CID = "CID";
	final public  String CID_data = "0";
	
	

	
}
