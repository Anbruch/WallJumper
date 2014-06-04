package com.me.walljumper.game_objects.terrain.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject;

public class Turret extends AbstractGameObject {
	
	private float timeBetweenShots, curTime;
	private ManipulatableObject target;
	private float projectileVelocity;
	

	public Turret(float x, float y, float width, float height,
			boolean flipX, boolean flipY, float timeBetweenShots, float projectileVelocity,
			float targetX, float targetY){
		position = new Vector2(x, y);
		dimension = new Vector2(width, height);
		origin = new Vector2();
		scale = 1;
		this.timeBetweenShots = timeBetweenShots;
		
		acceleration = new Vector2();
		this.projectileVelocity = projectileVelocity;
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		bounds = new Rectangle(x, y, width, height);
		this.flipX = flipX;
		this.flipY = flipY;
		rotation = (float) Math.atan2(targetY - y, targetX - x);	

	}
	public void setFireDirection(float direction){
		
	}
	
	public void update(float deltaTime){
		if(onScreen){
			if(!hasTarget())
				consistentFire(deltaTime);
		}
		super.update(deltaTime);
		
		
	}
	@Override
	public void render(SpriteBatch batch) {
		//Draw 
		if(onScreen)
			batch.draw(image.getTexture(), position.x, position.y, origin.x, origin.y,
					currentFrameDimension.x, currentFrameDimension.y, 1, 1,
					rotation, image.getRegionX(), image.getRegionY(),
					image.getRegionWidth(), image.getRegionHeight(),
					flipX, flipY);
	}
	private void consistentFire(float deltaTime) {
		
		//Fire bullets on a sequence
		if(curTime > timeBetweenShots){
			fire();
			curTime = 0;
			return;
		}
		
		curTime += deltaTime;
	}

	private void fire() {
		
	}

	public boolean hasTarget(){
		
		return false;
	}
	
}
