package com.me.walljumper.screens;

import com.badlogic.gdx.Screen;

public abstract class ScreenHelper implements Screen{

	@Override
	public void render(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
	
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button){
		return false;
	}

	public boolean handleKeyInput(int keycode) {
		return false;
	}

}
