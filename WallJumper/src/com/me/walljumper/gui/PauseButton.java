package com.me.walljumper.gui;

import com.me.walljumper.game_objects.AbstractGameObject;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.WallJumper;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;
import com.me.walljumper.tools.InputManager;
import com.me.walljumper.tools.WorldRenderer;

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
		
		if(World.controller.blackHoled)
			return;
		
		if(!WallJumper.paused){
			pause();
		}else{
			play();
		}
	}
	public void pause(){
		image = play;
		WallJumper.paused = true;
		WorldRenderer.renderer.pauseMenu();
		World.controller.cameraHelper.moveTowardsPosition(World.controller.cameraHelper.getTarget().position);
		World.controller.cameraHelper.setTarget(null);
		World.controller.renderAll = true;
		
	}
	public void play(){
		
		WorldRenderer.renderer.unDisplayToWorld();
		WallJumper.paused = false;
		image = pause;
		World.controller.renderAll = false;
		World.controller.cameraHelper.setTarget(InputManager.inputManager.getPlayer());
		World.controller.cameraHelper.transitionToZoom(Constants.defaultZoom);
		WorldRenderer.renderer.clearScene();
		
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
