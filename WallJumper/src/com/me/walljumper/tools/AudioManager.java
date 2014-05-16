package com.me.walljumper.tools;

import com.badlogic.gdx.audio.Music;

public class AudioManager {
	
	public static AudioManager instance = new AudioManager();
	private Music playingMusic;
	
	private AudioManager(){
		
	}
	public boolean isPlaying(){
		return playingMusic != null;
	}
	public void playMusic(Music music){
		stopMusic();
		playingMusic = music;
		music.setLooping(true);
		music.play();
		
	}
	public void stopMusic(){
		System.out.println("theousntu");
		if(playingMusic != null){
			playingMusic.stop();
			playingMusic = null;
		}
	}
}
