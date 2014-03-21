package com.me.walljumper.game_objects.terrain;

import com.me.walljumper.game_objects.AbstractGameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class Portal extends AbstractGameObject{
	
	public Portal(){
		
	}
	public Portal(float x, float y){
		init(x, y);
	}
	public void init(float x, float y){
		currentFrameDimension = new Vector2();
		position.set(x, y - 1);
		
		
		aniNormal = Assets.instance.portal.aniPortal;
		setAnimation(aniNormal);
		
		image = animation.getKeyFrame(stateTime);
		scale = 5;
		dimension.set(image.getRegionWidth() / 100.0f * scale, image.getRegionHeight() / 100.0f * scale);
		bounds.set(position.x, position.y, dimension.x - dimension.x / 3, dimension.y);
	}
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		if(bounds.overlaps(World.controller.getPlayerRect())){
			World.levelNum++;
			World.controller.destroy();
			World.controller.init();
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		
		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set((image.getRegionWidth() / 100.0f) * scale,
				(image.getRegionHeight() / 100.0f) * scale);
		// Draw
		batch.draw(image.getTexture(), position.x, position.y, 0, 0,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				false, false);

	}

}
