package com.me.walljumper.gui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.tools.Assets;

public class Image extends SceneObject {
	private boolean looping;
	private Animation animation;
	private float stateTime;
	private Vector2 currentFrameDimension;
	private boolean animationCompleted;

	public Image(){
		
	}
	public Image(boolean clickable, String imageUp,  float x, float y, float width, float height) {
		super(clickable, imageUp, x, y, width, height);
	
		
	}
	

	public Image(boolean clickable, TextureRegion goalBackground, float x, float y, float width,
			float height) {
		super(clickable, null, x, y, width, height);
		cur = goalBackground;

	}
	
	public Image(boolean clickable, Animation animation,  float x, float y, float width,
			float height) {
		super(clickable, null, x, y, width, height);
		setAnimation(animation);
		currentFrameDimension = new Vector2();

	}
	public void setAnimation(Animation animation){
		if(animation.getPlayMode() == Animation.NORMAL){
			looping = false;
		}else
			looping = true;
		this.animation = animation;
		stateTime = 0;
		
	}
	@Override
	public void render(SpriteBatch batch) {
		if(animation == null){
			super.render(batch);
			return;
		}
		cur = null;
		cur = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set(cur.getRegionWidth(),
				cur.getRegionHeight());
		batch.draw(cur, position.x, position.y,
				currentFrameDimension.x / 2, currentFrameDimension.y / 2, currentFrameDimension.x, currentFrameDimension.y, scaleX, scaleY, rotation);
		return;
	}

	@Override
	public void update(float deltaTime) {
		if(usingScene){

			super.update(deltaTime);
			return;
		}
		
		//if animation, increment stateTime and then run any code for 
		//completion of animation
		if(animation != null){
			stateTime += deltaTime;
			if(animation.isAnimationFinished(stateTime) && !animationCompleted){
				onAnimationComplete();
				animationCompleted = true;
			}
		}
	}



	public void onAnimationComplete() {
		
	}
	@Override
	public boolean clickRelease() {

		return false;
	}
	@Override
	public boolean clickedDown() {
		// TODO Auto-generated method stub
		return false;
	}
	public TextureRegion getTexture() {
		return cur;
	}

}
