package com.me.walljumper.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.walljumper.Constants;
import com.me.walljumper.tools.Assets;

public abstract class SceneObject {
	public Rectangle bounds;
	protected TextureRegion cur, up, down;
	public Vector2 position, afterTwnPos;
	public Vector2 dimension;
	protected int number;
	protected boolean interactable;
	public float rotation;
	private boolean flipY;
	private boolean flipX;
	
	private String text;
	private float fontOffsetX, fontOffsetY;
	public float alpha, scaleX, scaleY;
	
	public SceneObject(boolean clickable, float x, float y, float width, float height){
		interactable = clickable;
		position = new Vector2(x, y);
		rotation = 0;
		text = "";
		scaleX = 1; scaleY = 1;

		dimension = new Vector2(width, height);
		bounds = new Rectangle(position.x, position.y, width, height);
		
	}
	public void setAfterTwn(Vector2 afterTwnPos){
		this.afterTwnPos = afterTwnPos;
	}
	public SceneObject(boolean clickable, String image, float x, float y, float width, float height){
		interactable = clickable;
		position = new Vector2(x, y);
		fontOffsetX = 0; fontOffsetY = 0;
		rotation = 0;
		text = "";
		scaleX = 1; scaleY = 1;

		cur = SceneAssets.instance.ui.uiMap.get(image);
		dimension = new Vector2(width, height);
		bounds = new Rectangle(position.x, position.y, width, height);
		
	}
	public void setScale(float num){
		scaleX = num;
		scaleY = num;
	}
	public void setToWrite(String text, float fontOffsetX, float fontOffsetY){
		this.text = text;
		this.fontOffsetX = fontOffsetX;
		this.fontOffsetY = fontOffsetY;
	}
	public void setNum(int number){
		this.number = number;
	}
	public int getNum(){
		return number;
	}
	public abstract boolean clickedDown();
	public abstract boolean clickRelease();
	public abstract void update(float deltaTime);
	
	public void render(SpriteBatch batch) {
		batch.draw(cur, position.x, position.y,
				dimension.x / 2, dimension.y / 2, dimension.x, dimension.y, scaleX, scaleY, rotation);
		Scene.curScene.writeToWorld(text, this, fontOffsetX, fontOffsetY);
		
	}
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		Vector2 x = Constants.screenToCoords(Scene.curScene.camera, new Vector3(screenX, screenY, 0));
		if(bounds.contains(x) && this.interactable){
			
			cur = down != null ? down : cur;
			clickedDown();
			
			return true;
		}
		
		return false;
	}
	public void touchUp(int screenX, int screenY, int pointer, int button){
		//Convert co-ordinates to units 
		Vector2 x = Constants.screenToCoords(Scene.curScene.camera, new Vector3(screenX, screenY, 0));
		
		//call on clickRelease if hit, otherwise should display up image
		if(bounds.contains(x) && interactable){
			clickRelease();
		}
		if(up != null)
			cur = up;
		
	}	
	public void touchDragged(int screenX, int screenY, int pointer) {
		//While dragging it will check for collisions
		Vector2 x = Constants.screenToCoords(Scene.curScene.camera, new Vector3(screenX, screenY, 0));
		if(interactable && (down != null && up != null))
			cur = bounds.contains(x) ? down : up;
		
		
	}
	
}
