package com.me.walljumper.game_objects;

import com.me.walljumper.game_objects.classes.ManipulatableObject.VIEW_DIRECTION;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.WorldRenderer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public float scale;
	
	//Physics
	public Vector2 acceleration;
	public Vector2 velocity;
	public Vector2 terminalVelocity; //Objects max speed magnitude
	public Rectangle bounds; // objects bounding box used for collision
	protected TextureRegion image;
	public float stateTime;
	
	public boolean looping;
	public Vector2 currentFrameDimension;
	public Animation aniRunning;
	public Animation aniNormal;
	public float rotation;
	public Animation animation;
	public boolean animationBool;
	protected boolean flipX;
	protected boolean flipY;
	public boolean onScreen;
	
	public AbstractGameObject(){
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = 1;
		rotation = 0;
		
		acceleration = new Vector2();
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		bounds = new Rectangle();
	}
	public AbstractGameObject(float x, float y, float width, float height){
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
	public AbstractGameObject(float x, float y, float width, float height,
			boolean flipX, boolean flipY){
		position = new Vector2(x, y);
		dimension = new Vector2(width, height);
		origin = new Vector2();
		scale = 1;
		rotation = 0;
		
		acceleration = new Vector2();
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		bounds = new Rectangle(x, y, width, height);
		this.flipX = flipX;
		this.flipY = flipY;
	}
	public void update(float deltaTime){
		
		stateTime += deltaTime;
		stateTime = stateTime > 0 ? stateTime : 0;
		
		onScreen = World.controller.cameraHelper.onScreen(this) ? true : false;
			
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		
		
		
		
		
		
	}
	public void interact(AbstractGameObject couple){
		
	}
	public void setAnimation(Animation animation){
		if(animation.getPlayMode() == Animation.NORMAL){
			looping = false;
		}else
			looping = true;
		this.animation = animation;
		stateTime = 0;
		
	}
	public void setImage(TextureRegion image){
		this.image = image;
	}
	protected void updateMotionX(float deltaTime){
		
		//Apply acceleration
		velocity.x += acceleration.x * deltaTime;
		//Make sure the object's velocity does not exceed the terminal velocity
		velocity.x = MathUtils.clamp(velocity.x, - terminalVelocity.x,  terminalVelocity.x);
		
		
		
		
	}
	protected void updateMotionY(float deltaTime){
		
		velocity.y += acceleration.y * deltaTime;
		velocity.y = MathUtils.clamp(velocity.y, - terminalVelocity.y,  terminalVelocity.y);
		
	}
	public void render(SpriteBatch batch){
		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set(image.getRegionWidth() / 10,
				image.getRegionHeight() / 10);
		// Draw
		if(onScreen)
		batch.draw(image.getTexture(), position.x, position.y, origin.x, origin.y,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				flipX, flipY);

	}
}
