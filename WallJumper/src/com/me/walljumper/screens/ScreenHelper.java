package com.me.walljumper.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;
import com.me.walljumper.screens.screentransitions.ScreenTransition;
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlice;
import com.me.walljumper.tools.AudioManager;

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
	public void backToLevelMenu(){
		AudioManager.instance.stopMusic();
		ScreenTransition transition = ScreenTransitionSlice.init(.6f, ScreenTransitionSlice.UP_DOWN, 10,
				Interpolation.pow2Out);
		game.setScreen(new LevelMenu(game), transition);
	}
	public void nextLevel() {
		
	}
	public void restartLevel(){
		System.out.println("ouesnhtaoeunsht");

		World.controller.destroy();
		World.controller.init();
	}
	public void backToMainMenu() {
		System.out.println("eousaoeut");
		AudioManager.instance.stopMusic();
		ScreenTransition transition = ScreenTransitionSlice.init(.6f, ScreenTransitionSlice.UP_DOWN, 10,
				Interpolation.pow2Out);
		game.setScreen(new MainMenu(game), transition);
	}

}
