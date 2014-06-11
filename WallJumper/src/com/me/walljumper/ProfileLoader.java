package com.me.walljumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class ProfileLoader {
	
	public static ProfileLoader profileLoader;

	
	private ProfileLoader(){
		readAndBuidlProfile();

	}
	public static void init(){
		profileLoader = new ProfileLoader();
	}
	public void readAndBuidlProfile(){
		if(!Gdx.files.local("profile.json").exists()){
			initializeProfile();
			System.out.println("tnsoeutn");
			return;
		}
		
        Json  json = new Json();
		FileHandle handle = Gdx.files.local("profile.json");
        String fileContent = handle.readString(); 
        
        //json.setElementType(Profile.class, "enemies", Position.class);
        WallJumper.profile = json.fromJson(Profile.class, fileContent);

        
		
	}
	
	private void initializeProfile() {
		WallJumper.profile = new Profile();
		WallJumper.profile.lastLevelCompleted = 1;
		WallJumper.profile.collectedRiftFragments = 0;
		
		
		for(int i = 0; i < WallJumper.numButtonsPerPage; i++){
			WallJumper.profile.World1.add(new LevelInfoNode());
		}
		
		saveProfile();		
	}
	public void saveProfile(){
		
		Json json = new Json();
		FileHandle handle = Gdx.files.local("profile.json");
		String fileContent = json.toJson(WallJumper.profile);
		System.out.println(fileContent);
		handle.writeString(fileContent, false);
	}

}
