package com.me.walljumper;

public class LevelInfoNode {
	private float fastTime;
	private String id;
	private int fragmentCollected;
	
	public LevelInfoNode(float fastTime, String id){
		this.fastTime = fastTime;
		this.id = id;
	}
	public LevelInfoNode() {

	}
	public float getTime(){
		return fastTime;
	}
	public String getID(){
		return id;
	}
	
	public void setTime(float fastTime){
		this.fastTime = fastTime;
	}
	public void setID(String id){
		this.id = id;
	}
	
}
