package com.me.walljumper.game_objects.classes;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.tools.Assets;

public class ScytheMan extends ManipulatableObject {
	public ScytheMan(){
		
	}
	public ScytheMan(float x, float y, float width, float  height){
		init(x, y, width, height);
		
	}
	private void init(float x, float y, float width, float  height){
		aniRunning = Assets.instance.scytheMan.aniRunning;
		aniNormal = Assets.instance.scytheMan.aniNormal;
		aniJumping = Assets.instance.scytheMan.aniJumping;
		aniWalling = Assets.instance.scytheMan.aniWalling;
		zAttack = Assets.instance.scytheMan.zAttack;
		
		position.set(x, y);
		acceleration.set(0, -900);
		moveSpeed = new Vector2(300, 500);
		setAnimation(aniNormal);
		terminalVelocity.set(400, 600);
		dimension.set(width, height);
		bounds.set(0, 0, width - 43, height - 21);
		
		moveRight();
	}
	
	@Override
	protected void ensureCorrectCollisionBounds() {
		bounds.y = position.y;
		bounds.x = position.x + 3f;
		
	
	}
	@Override
	public void actOnInputKeyDown(int keycode){
		
		super.actOnInputKeyDown(keycode);
		if(keycode == Keys.Z && combatState != COMBAT.ATTACKING){
			combatState = COMBAT.ATTACKING;
			setAnimation(zAttack);
		}
		
	}
	
	@Override
	public void update(float deltaTime){
		
		super.update(deltaTime);
		
	
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}
	public void setMovementSpeed(Vector2 moveSpeed) {
		this.moveSpeed.set(moveSpeed);
	}
	

}
