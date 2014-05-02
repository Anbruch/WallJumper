package com.me.walljumper;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.me.walljumper.screens.ScreenHelper;
import com.me.walljumper.screens.World;
import com.me.walljumper.screens.screentransitions.ScreenTransition;

public class DirectedGame implements ApplicationListener {
	private boolean init;
	private ScreenHelper curScreen, nextScreen;
	private FrameBuffer currFbo, nextFbo;
	private SpriteBatch batch;
	private float t;
	private ScreenTransition screenTransition;
	
	public DirectedGame(){
		
	}
	
	public void setScreen(ScreenHelper screen){
		setScreen(screen, null);
	}
	public void setScreen(ScreenHelper screen, ScreenTransition screenTransition){
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		if(!init){
			currFbo = new FrameBuffer(Format.RGB888, w, h, false);
			nextFbo = new FrameBuffer(Format.RGB888, w, h, false);
			batch = new SpriteBatch();
			init = true;
		}
		nextScreen = screen;
		nextScreen.show();
		nextScreen.resize(w, h);
		nextScreen.render(0);
		
		if(curScreen != null){
			
			curScreen.pause();
			nextScreen.pause();
			Gdx.input.setInputProcessor(null);
			this.screenTransition = screenTransition;
			t = 0;
		}
	}
	
	@Override
	public void create() {
		
	}

	@Override
	public void resize(int width, int height) {
		if(curScreen != null)
			curScreen.resize(width, height);
		if(nextScreen != null)
			nextScreen.resize(width, height);
		
	}

	@Override
	public void render() {
		float deltaTime = Math.min(1 / 60.0f, Gdx.graphics.getDeltaTime());
		if(nextScreen == null){
			if(curScreen != null)
				curScreen.render(deltaTime);
		}else{
			float duration = screenTransition != null ? screenTransition.getDuration() : 0;
			
			//update progress of ongoing transition
			//This removes screen transition
			t = Math.min(t + deltaTime, duration);
			if(screenTransition == null || t >= duration){
				
				if(curScreen != null)
					curScreen.hide();
				nextScreen.resume();
				Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
				curScreen = nextScreen;
				nextScreen = null;
				screenTransition = null;
				
			//Render the screen transition
			}else{
				currFbo.begin();
				if(curScreen != null){
					curScreen.render(deltaTime);
				}
				currFbo.end();
				nextFbo.begin();
				nextScreen.render(deltaTime);
				nextFbo.end();
				
				float alpha = t / duration;
				screenTransition.render(batch, currFbo.getColorBufferTexture(),
						nextFbo.getColorBufferTexture(), alpha);
				
			}
		}
	}

	@Override
	public void pause() {
		if(curScreen != null)
			curScreen.pause();
		if(nextScreen != null)
			nextScreen.pause();
	}

	@Override
	public void resume() {
		if(curScreen != null)
			curScreen.resume();
		if(nextScreen != null)
			nextScreen.resume();
		
	}

	@Override
	public void dispose() {
		if(curScreen != null)
			curScreen.hide();
		if(nextScreen != null)
			nextScreen.hide();
		
		if(init){
			currFbo.dispose();
			curScreen = null;
			nextFbo.dispose();
			nextScreen = null;
			batch.dispose();
			init = false;
		}
		
	}

}
