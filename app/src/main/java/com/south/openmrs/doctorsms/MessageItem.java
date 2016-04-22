package com.south.openmrs.doctorsms;


import android.text.format.Time;

public class MessageItem extends Item {
	public String username;
	public String msg;
	public String chattername;
	public long chatter;
	public Time time;
	
	
	
	
	MessageItem(String userName, String msg, String chatterName, Time time){
		this.username = userName;
		this.msg = msg;
		this.chattername = chatterName;
		this.time = time;
	}
	
	
	
	
	String postInfo(){
        
		return "";
		
	}
	
String postDisplayInfo(){
		
		String s =  (username + ": " + msg);
		
		return s;
	}
	
}
