package com.me.walljumper.gui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.tools.Assets;

public class Image extends SceneObject {
	private Sprite sprite;

	public Image(){
		
	}
	public Image(boolean clickable, String imageUp,  float x, float y, float width, float height) {
		super(clickable, imageUp, x, y, width, height);
	
		
	}
	

	public Image(boolean clickable, AtlasRegion goalBackground, float x, float y, float width,
			float height) {
		super(clickable, null, x, y, width, height);
		cur = goalBackground;

	}
	@Override
	public boolean clickedDown() {

		return false;
	}

	@Override
	public void update(float deltaTime) {
		
	}



	@Override
	public boolean clickRelease() {
		// TODO Auto-generated method stub
		return false;
	}

}
