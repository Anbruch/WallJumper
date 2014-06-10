package com.me.walljumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Profile implements Serializable {
	public static int tutorial;
	//public static Array<LevelInfoNode> currentWorld;
	private FileHandle file;
	
	public void setFile(String file){
		this.file = Gdx.files.local(file);
	}
	
	@Override
	public void write(Json json) {
		json.writeValue("tutorial", tutorial);
		
		
	/*	   
        FileHandle handle = Gdx.files.internal("assets/map.json");
        String fileContent = handle.readString();                  
        Json  json = new Json();
        json.setElementType(Config.class, "enemies", Position.class);
        Config data = new Config();
        data = json.fromJson(Config.class, fileContent);
        Gdx.app.log(GameManager.LOG, "Data name = " + data.name);
        for(Object e :data.enemies){  
            Position p = (Position)e;*/
		
		
		/*String x = json.toJson(this);
		String profileAsCode = Base64Coder.encodeString( x );
		file.writeString(profileAsCode, false);*/

		//json.toJson(levelInfoNode)
		//json.writeValue("World" + WallJumper.WorldNum, currentWorld);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) {
		tutorial = json.readValue("tutorial", Integer.class, jsonData);
		
		//currentWorld = json.fromJson(Array.class,"World" + WallJumper.WorldNum);
		
		
	}

}
