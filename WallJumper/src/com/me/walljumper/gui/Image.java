package com.me.walljumper.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.walljumper.tools.Assets;

public class Image extends SceneObject {

	public Image(boolean clickable, String image,  float x, float y, float width, float height) {
		super(clickable, image, x, y, width, height);
		
	}

	@Override
	public boolean clickedDown() {

		return false;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}



	@Override
	public boolean clickRelease() {
		// TODO Auto-generated method stub
		return false;
	}

}
