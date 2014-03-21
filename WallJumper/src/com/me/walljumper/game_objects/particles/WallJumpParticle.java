package com.me.walljumper.game_objects.particles;

import com.me.walljumper.game_objects.Particle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.tools.Assets;
import com.me.walljumper.tools.LevelStage;

public class WallJumpParticle extends Particle{
	private boolean FlipX;
	public WallJumpParticle(float x, float y, float width, float height, boolean flipX){
		FlipX = flipX;
		init(x, y, width, height, flipX);
	}
	public void init(float x, float y, float width, float height, boolean flipX){
		aniNormal = Assets.instance.wallJumpParticle.wallJumpParticle;
		setAnimation(aniNormal);
		currentFrameDimension = new Vector2();
		position = new Vector2(x, y);
		dimension = new Vector2(width, height);
		origin = new Vector2();
		scale = 1;
		rotation = 0;
		
		acceleration = new Vector2();
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		bounds = new Rectangle(x, y, width, height);
	}
	@Override 
	public void update(float deltaTime){
		super.update(deltaTime);
		if(animation.isAnimationFinished(stateTime)){
			int index = 0;
			while(index < LevelStage.uncollidableObjects.size && 
					LevelStage.uncollidableObjects.get(index) != this){
				index++;
			}
			LevelStage.uncollidableObjects.removeIndex(index);
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set((image.getRegionWidth() / 10.0f) * scale,
				 (image.getRegionHeight()/10.0f) * scale);
		
		// Draw
		batch.draw(image.getTexture(), position.x, position.y, 0, 0,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				FlipX, false);

	}
}
