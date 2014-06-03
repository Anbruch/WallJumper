package com.me.walljumper.game_objects.terrain.traps;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject;

public class Turret extends AbstractGameObject {
	
	private float timeBetweenShots, curTime;
	private ManipulatableObject target;
	

	public Turret(float x, float y, float width, float height,
			boolean flipX, boolean flipY, float timeBetweenShots){
		position = new Vector2(x, y);
		dimension = new Vector2(width, height);
		origin = new Vector2();
		scale = 1;
		rotation = 0;
		this.timeBetweenShots = timeBetweenShots;
		
		acceleration = new Vector2();
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		bounds = new Rectangle(x, y, width, height);
		this.flipX = flipX;
		this.flipY = flipY;
	}
	public void setFireDirection(Vector2 direction){
		
	}
	
	public void update(float deltaTime){
		if(onScreen){
			if(!hasTarget())
				consistentFire();
		}
		
		
	}
	private void consistentFire() {
		if(curTime > timeBetweenShots){
			fire();
			curTime = 0;
		}
	}

	private void fire() {
		
	}

	public boolean hasTarget(){
		
		return false;
	}
	
}
