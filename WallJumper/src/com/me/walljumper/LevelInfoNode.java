package com.me.walljumper;

public class LevelInfoNode {
	private float fastTime;
	private String score;
	
	public LevelInfoNode(float fastTime, String score){
		this.fastTime = fastTime;
		this.score = score;
	}
	public float getTime(){
		return fastTime;
	}
	public String getScore(){
		return score;
	}
	
	public void setTime(float fastTime){
		this.fastTime = fastTime;
	}
	public void setScore(String score){
		this.score = score;
	}
	
}
