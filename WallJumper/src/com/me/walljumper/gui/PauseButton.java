package com.me.walljumper.gui;

import com.me.walljumper.game_objects.AbstractGameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.WallJumper;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class PauseButton extends AbstractGameObject {
	private TextureRegion pause;
	private TextureRegion play;
	
	public PauseButton(){
		pause = Assets.instance.pause.pause;
		play = Assets.instance.pause.play;
		image = play;
		
		scale = 10;
		dimension = new Vector2(41,  46);
		position = new Vector2(20, Constants.bgViewportHeight - Constants.bgViewportHeight / 10);
	}
	public void toggle(){
		
		image = !WallJumper.paused  ? pause : play;
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
