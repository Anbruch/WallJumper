package com.me.walljumper.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;

public abstract class ScreenHelper implements Screen{
	protected DirectedGame game;
	
	public ScreenHelper(DirectedGame game){
		this.game = game;
	}
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
		WallJumper.paused = true;
	}

	@Override
	public void resume() {
		WallJumper.paused = false;
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
	public void changeScreen(ScreenHelper screen) {
		((DirectedGame) Gdx.app.getApplicationListener()).setScreen(screen);

	}

	public abstract InputProcessor getInputProcessor();

}
