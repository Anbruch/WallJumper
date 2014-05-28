package com.me.walljumper.game_objects.terrain;

import com.me.walljumper.game_objects.AbstractGameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;
import com.me.walljumper.tools.LevelStage;

public class Portal extends AbstractGameObject{
	private boolean death;
	public Portal(){
		
	}
	public Portal(float x, float y, boolean death){
		init(x, y, death);
	}
	public void init(float x, float y, boolean death){
		currentFrameDimension = new Vector2();
		position.set(x, y);
		this.death = death;
		
		
		aniNormal = death ? Assets.instance.portal.aniDeathPortal : Assets.instance.portal.aniPortal;
		setAnimation(aniNormal);
		
		image = animation.getKeyFrame(stateTime);
		scale = death ? 2.5f : 5;
		dimension.set(image.getRegionWidth() / 100.0f * scale, image.getRegionHeight() / 100.0f * scale);
		bounds.set(position.x + dimension.x * .15f, position.y + dimension.y * .2f, dimension.x - dimension.x * .4f, dimension.y - dimension.y * .6f);
	}
	@Override
	public void interact(AbstractGameObject couple){
		
		World.controller.moveTowards(LevelStage.player, this, .7f);
		World.portal = this;
	}

	
	@Override
	public void render(SpriteBatch batch) {
		
		if(onScreen || World.controller.renderAll){
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
	public boolean isDeathPortal() {
		return death;
	}

}
