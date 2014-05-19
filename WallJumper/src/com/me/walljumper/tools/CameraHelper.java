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
	public float zoom;
	private Vector2 targetSpot;
	private AbstractGameObject target;
	private static final float rate = 2;
	Rectangle rect;
	private float zoomTarget;
	private boolean zooming;

	public CameraHelper() {
		position = new Vector2();
		zoom = Constants.defaultZoom;
		zoomTarget = zoom;
		targetSpot = new Vector2();
		rect = new Rectangle(-5, 0, Constants.viewportWidth + 10,
				Constants.viewportHeight);
	}

	public void update(float deltaTime) {

		// Zoom out/in as much as needed
		float deltaZoom = rate * deltaTime;
		if (zoom > zoomTarget) {
			zoom = deltaZoom > zoom - zoomTarget ? zoomTarget : zoom - deltaZoom;
		} else if(zoom < zoomTarget) {
			zoom = deltaZoom > zoomTarget - zoom ? zoomTarget : zoom + deltaZoom;
		}
		

	

		float deltaX = !hasTarget() ? (targetSpot.x - position.x) / 10
				: (target.position.x - position.x) / 10, deltaY = !hasTarget() ? (targetSpot.y - position.y) / 10
				: (target.position.y - position.y) / 10;
		position.x += deltaX;
		position.y += deltaY;

		rect.x += deltaX;
		rect.y += deltaY;

	}

	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}

	public boolean onScreen(AbstractGameObject obj) {
		// Checks if camera bounds overlaps with
		if (obj.bounds.overlaps(rect))
			return true;

		return false;

	}

	public void setPosition(float x, float y) {
		this.position.set(x, y);
		this.targetSpot.set(position);
		rect.setPosition(x - Constants.viewportWidth / 2 - 5, y
				- Constants.viewportHeight / 2);

	}

	public Vector2 getPosition() {
		return position;
	}

	public void addZoom(float amount) {
		setZoom(zoom + amount);

	}

	public void setZoom(float zoom) {

		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
		zoomTarget = this.zoom;

	}

	public float getZoom() {
		return zoom;
	}

	public AbstractGameObject getTarget() {
		return target;
	}

	public boolean hasTarget() {
		return target != null;
	}

	public boolean hasTarget(AbstractGameObject target) {
		return hasTarget() && this.target.equals(target);
	}

	public void applyTo(OrthographicCamera camera) {
		camera.position.x = this.position.x;
		camera.position.y = this.position.y;
		camera.zoom = zoom;
		camera.update();

	}

	public void destroy() {
		target = null;
		position = null;
	}

	public void movePositionBy(Vector2 deltaPos) {
		moveTowardsPosition(deltaPos.add(position));
	}

	public void moveTowardsPosition(Vector2 targetSpot) {
		this.targetSpot = targetSpot;
	}

	public void zoomBy(float zoomBy) {
		transitionToZoom(zoom + zoomBy);
	}

	public void transitionToZoom(float zoomTo) {

		// timePassed * rate
		zoomTarget = zoomTo;
	}
}
