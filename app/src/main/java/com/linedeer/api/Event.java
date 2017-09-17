package com.linedeer.api;

import android.util.Log;

import java.util.Vector;

public class Event {
	Vector<EventCall> List = new Vector<EventCall>(1, 1);
	String From;
	public Event(String From) {
		this.From = From;
		// TODO Auto-generated constructor stub
	}
	
	 
	public void trigger(int arg){

		EventCall Temp;
		for (int i = 0; i < List.size(); i++) {
			Temp = List.get(i);
			if(Temp.Type == null){
				Temp.onCall(arg);
			}else{
				for (int j = 0; j < Temp.Type.length; j++) {
					//
					 if(Temp.Type[j] == arg){
						 Temp.onCall(arg);
					 }
				}
			}
		}
	}
	
	public void flush(int arg){
		trigger(arg);
		List = new Vector<EventCall>(1, 1);
	}
	
	public void addEvent(EventCall Call){
		List.add(Call);
	}
	
	public void removeEvent(EventCall Call){
		List.removeElement(Call);
	}
	
	public void removeLast(){
		if(List.size() != 0){
			List.removeElement(List.lastElement());
		}
	}
}
