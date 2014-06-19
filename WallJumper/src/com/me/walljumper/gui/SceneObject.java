package com.me.walljumper.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.walljumper.Constants;
import com.me.walljumper.tools.WorldRenderer;

public abstract class SceneObject {
	private static OrthographicCamera currentCamera;
	public Rectangle bounds;
	protected TextureRegion cur, up, down;
	public Vector2 position, afterTwnPos;
	public Vector2 dimension;
	protected int number;
	protected boolean clickable;
	public float rotation;
	private boolean flipY;
	public boolean usingScene;
	private Animation animation;
	private Sprite sprite;
	Vector2 currentFrameDimension;
	private String text;
	private float fontOffsetX, fontOffsetY;
	public float alpha, scaleX, scaleY;
	private boolean animating;
	private float stateTime;
	private boolean looping;

	
	public SceneObject(){
		stateTime = 0;
		
	}
	public static void setCamera(OrthographicCamera camera){
		currentCamera = camera;
	}
	
	public SceneObject(boolean clickable, float x, float y, float width, float height){
		this.clickable = clickable;
		currentFrameDimension = new Vector2();
		position = new Vector2(x, y);
		rotation = 0;
		text = "";
		stateTime = 0;
		scaleX = 1; scaleY = 1;
		usingScene = true;

		dimension = new Vector2(width, height);
		bounds = new Rectangle(position.x, position.y, width, height);
		
	}
	public void setAfterTwn(Vector2 afterTwnPos){
		this.afterTwnPos = afterTwnPos;
	}
	public SceneObject(boolean clickable, String image, float x, float y, float width, float height){
		this.clickable = clickable;
		position = new Vector2(x, y);
		currentFrameDimension = new Vector2();
		fontOffsetX = 0; fontOffsetY = 0;
		rotation = 0;
		usingScene = true;
		text = "";
		scaleX = 1; scaleY = 1;
		if(image != null)
		cur = SceneAssets.instance.ui.uiMap.get(image);
		
		sprite = new Sprite();
		dimension = new Vector2(width, height);
		bounds = new Rectangle(position.x, position.y, width, height);
		
	}
	public void setScale(float num){
		scaleX = num;
		scaleY = num;
	}
	public void setToWrite(String text, float fontOffsetX, float fontOffsetY, boolean usingScene){
		this.text = text;
		this.fontOffsetX = fontOffsetX;
		this.fontOffsetY = fontOffsetY;
		this.usingScene = usingScene;
	}
	public void setNum(int number){
		this.number = number;
	}

	public int getNum(){
		return number;
	}
	public abstract boolean clickedDown();
	public abstract boolean clickRelease();
	public  void update(float deltaTime){
		if(animating){
			stateTime += deltaTime;
			if(animation.isAnimationFinished(stateTime)){
				onAnimationComplete();
			}
		}
	}
	
	
	public void render(SpriteBatch batch) {
		//Render Animation
		if(animating){
			renderAnimation(batch);
		}else
			batch.draw(cur, position.x, position.y,
				dimension.x / 2, dimension.y / 2, dimension.x, dimension.y, scaleX, scaleY, rotation);		
		
		//Write to screen
		if(usingScene && Scene.curScene != null)
			Scene.curScene.writeToWorld(text, this, fontOffsetX, fontOffsetY);
		else			
			WorldRenderer.renderer.writeToWorld(text, position.x + fontOffsetX,  position.y + fontOffsetY);
		
		
	}
	
	private void renderAnimation(SpriteBatch batch) {
		cur = animation.getKeyFrame(stateTime, looping);
		/*currentFrameDimension.set(cur.getRegionWidth(),
				cur.getRegionHeight())*/;
		batch.draw(cur, position.x, position.y,
				dimension.x / 2, dimension.y / 2, dimension.x, dimension.y, scaleX, scaleY, rotation);
		
	}
	public void setAnimation(Animation animation){
		if(animation.getPlayMode() == Animation.NORMAL){
			looping = false;
		}else
			looping = true;
		this.animation = animation;
		animating = true;
		stateTime = 0;
	}
	public void onAnimationComplete() {
		
	}
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		try{
		Vector2 x = Constants.screenToCoords(SceneObject.currentCamera, new Vector3(screenX, screenY, 0));
		
			if(bounds.contains(x) && this.clickable){
				cur = down != null ? down : cur;
				animating = false;
				clickedDown();
				
				return true;
			}
		}catch(NullPointerException e){
			
		}
		return false;
	}
	public void touchUp(int screenX, int screenY, int pointer, int button){
		//Convert co-ordinates to units 
		Vector2 x = Constants.screenToCoords(SceneObject.currentCamera, new Vector3(screenX, screenY, 0));
		
		//call on clickRelease if hit, otherwise should display up image
		if(bounds.contains(x) && clickable){
			clickRelease();
			if(up != null)
				cur = up;
		}
		
		
	}	
	public void touchDragged(int screenX, int screenY, int pointer) {
		//While dragging it will check for collisions
		Vector2 x = Constants.screenToCoords(SceneObject.currentCamera, new Vector3(screenX, screenY, 0));
		if(clickable && (down != null && up != null)){
	
			cur = bounds.contains(x) ? down : up;
			animating = false;
		}
		
	}
	
}
