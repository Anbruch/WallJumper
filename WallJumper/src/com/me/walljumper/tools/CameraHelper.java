package com.me.walljumper.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.game_objects.AbstractGameObject;

public class CameraHelper {

	private static final String TAG = CameraHelper.class.getName();
	private final float MAX_ZOOM_IN = .02f;
	private final float MAX_ZOOM_OUT = 10.0f;
	private Vector2 position;
	private float zoom;
	private float zoomDifference;
	private float transitionTime, cur;
	private AbstractGameObject target;
	private float rate;
	Rectangle rect;
	
	public CameraHelper(){
		position = new Vector2();
		zoom = .65f;
		rect = new Rectangle(-5, 0, Constants.viewportWidth + 10, Constants.viewportHeight);
		rate = 0;
	}
	public void update(float deltaTime){
		if(transitionTime > 0){
			zoom += rate * deltaTime;
			transitionTime -= deltaTime;
		}
		if(!hasTarget()){
			return;
		}
		float deltaX = (target.position.x - position.x) / 10,
				deltaY = (target.position.y - position.y) / 10;
		position.x += deltaX;
		position.y += deltaY;
		
		rect.x += deltaX;
		rect.y += deltaY;
		

		
	}
	public void setTarget(AbstractGameObject target){
		this.target = target;
	}
	public boolean onScreen(AbstractGameObject obj){
		//Checks if camera bounds overlaps with 
		if(obj.bounds.overlaps(rect))
			return true;
		
		return false;
		
	}
	public void setPosition(float x, float y){
		this.position.set(x, y);
		rect.setPosition(x - Constants.viewportWidth / 2 - 5, y - Constants.viewportHeight / 2);
		
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
	public void transitionToZoom(float zoomTo, float transitionTime) {
		
		//timePassed * rate
		zoomDifference = zoomTo - zoom;
		this.transitionTime = transitionTime;
		rate = zoomDifference / transitionTime;
	}
}
