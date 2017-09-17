package com.linedeer.api;

import java.util.Vector;



public class Backpress {
	
	public Backpress() {
		// TODO Auto-generated constructor stub
	}

	public boolean back() {
		if(LOCK){
			return false;
		}
		if(List.size() == 0){
			return true;
		}
		call cl = List.lastElement();
		List.removeElement(cl);
		cl.onCall(true);
		if(cl.lock){
			List.add(cl);
		}
		return false;
	}
	
	Vector<call> List = new Vector<call>(1, 1);
	
	public void add(call call) {
		// TODO Auto-generated method stub
		List.add(call);
	}
	
	
	public void replaceLast(call call) {
		if(LOCK){
			return;
		}
		
		call cl = List.lastElement();
		List.removeElement(cl);
		List.add(call);
	}

	public void remove(call cl) {
		List.removeElement(cl);
	}

	public void dropLast() {
		List.removeElement(List.lastElement());
	}
	
	boolean LOCK = false;
	
	public void lock(){
		LOCK = true;
	}
	
	public void unLock(){
		LOCK = false;
	}

	public call removeLast() {
		call cl = List.lastElement();
		List.removeElement(cl);
		return cl;
	}
}
