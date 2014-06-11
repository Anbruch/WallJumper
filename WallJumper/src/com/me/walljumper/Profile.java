package com.me.walljumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Profile {
	public int tutorial;
	public int collectedRiftFragments;
	public int lastLevelCompleted;	
	public Array<LevelInfoNode> World1;
	
	
	public Profile(){
		World1 = new Array<LevelInfoNode>();
		
		
	}
		/*String x = json.toJson(this);
		String profileAsCode = Base64Coder.encodeString( x );
		file.writeString(profileAsCode, false);*/

		//json.toJson(levelInfoNode)
		//json.writeValue("World" + WallJumper.WorldNum, currentWorld);
	

}
