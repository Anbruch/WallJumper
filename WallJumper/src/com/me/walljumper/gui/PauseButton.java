package com.me.walljumper.gui;

import walljumper.game_objects.AbstractGameObject;
import walljumper.screens.World;
import walljumper.tools.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.WallJumper;

public class PauseButton extends AbstractGameObject {
	private TextureRegion pause;
	private TextureRegion play;
	
	public PauseButton(){
		pause = Assets.instance.pause.pause;
		play = Assets.instance.pause.play;
		image = pause;
		
		scale = 10;
		dimension = new Vector2(41,  46);
		position = new Vector2(20, Constants.bgViewportHeight - Constants.bgViewportHeight / 10);
	}
	public void toggle(){
		image = !WallJumper.paused && World.controller.countDown <= 0 ? pause : play;
	}
	@Override
	public void render(SpriteBatch batch){
		// get correct image and draw the current proportions
		// Draw
		batch.draw(image.getTexture(), position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				false, false);

	}
	
	
}
