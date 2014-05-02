package com.me.walljumper.tools;

import com.me.walljumper.game_objects.AbstractGameObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;

public class CameraHelper {

	private static final String TAG = CameraHelper.class.getName();
	private final float MAX_ZOOM_IN = .02f;
	private final float MAX_ZOOM_OUT = 10.0f;
	private Vector2 position;
	private float zoom;
	private boolean chasingTarget;
	private AbstractGameObject target;
	
	public CameraHelper(){
		position = new Vector2();
		zoom = .65f;
		chasingTarget = false;
	}
	public void update(float deltaTime){
		if(!hasTarget()){
			return;
		}
		position.x += (target.position.x - position.x) / 10;
		position.y += (target.position.y - position.y) / 10;
	/*
		position.x = target.position.x + target.origin.x + target.dimension.x / 2;
		position.y = target.position.y + target.origin.y ;*/
		
	}
	public void setTarget(AbstractGameObject target){
		this.target = target;
		chasingTarget = true;
	}
	public void setPosition(float x, float y){
		this.position.set(x, y);
		
	}
	public Vector2 getPosition(){
		return position;
	}
	public void addZoom(float amount){
		setZoom(zoom + amount);
		
	}
	public void setZoom(float zoom){

		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
		
	}
	public float getZoom(){
		return zoom;
	}
	
	public AbstractGameObject getTarget(){
		return target;
	}
	
	public boolean hasTarget(){
		return target != null;
	}
	public boolean hasTarget(AbstractGameObject target){
		return hasTarget() && this.target.equals(target);
	}
	public void applyTo(OrthographicCamera camera){
		camera.position.x = this.position.x;
		camera.position.y = this.position.y;
		camera.zoom = zoom;
		
		camera.update();
		
	}
	public void destroy() {
		target = null;
		position = null;
	}

	
}
