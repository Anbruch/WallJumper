package com.me.walljumper.gui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.walljumper.Constants;
import com.me.walljumper.tools.Assets;

public class Button extends SceneObject {
	
	
	FileHandle file;
	private int number;
	
	
	
	public Button(boolean clickable, String textureKeyUp, String textureKeyDown
			, float x, float y, float width, float height) {
		super(clickable, x, y, width, height);
		
		//Set up images
		up = SceneAssets.instance.ui.uiMap.get(textureKeyUp);
		down = SceneAssets.instance.ui.uiMap.get(textureKeyDown);
		cur = up;
		
	}
	public Button(boolean clickable, TextureRegion textureUp, TextureRegion textureDown
			, float x, float y, float width, float height) {
		super(clickable, x, y, width, height);
		
		//Set up images
		up = textureUp;
		down = textureDown;
		cur = up;
		
	}
	
	
	public void setLinkTo(FileHandle file){
		this.file = file;
	}
	public FileHandle getFile(){
		return file;
	}
	@Override
	public boolean clickedDown() {
		
		return false;
	}



	
	
	
	@Override
	public boolean clickRelease() {

		return false;
	}
	

}
