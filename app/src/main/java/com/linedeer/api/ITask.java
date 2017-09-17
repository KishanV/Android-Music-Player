package com.linedeer.api;

import android.os.AsyncTask;

public class ITask extends AsyncTask<String, Void, String>{
	
	@Override
	protected String doInBackground(String... params) {
		Go();
		return null;
	}
	
	public  void Go() {
		
	}

	public boolean stop = false;


	@Override
	protected void onPostExecute(String result) {
		if(!stop){
			than();
		}
	}
	
	public void than(){
		
	}

}
