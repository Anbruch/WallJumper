package com.me.walljumper.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Interpolation;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.ProfileLoader;
import com.me.walljumper.WallJumper;
import com.me.walljumper.screens.screentransitions.ScreenTransition;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlice;
import com.me.walljumper.tools.AudioManager;
import com.me.walljumper.tools.InputManager;

public class GameScreen extends AbstractScreen {
	
	public GameScreen(DirectedGame game) {
		super(game);
	}
	@Override
	public void render(float delta) {
		World.controller.render(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		World.controller.resize(width, height);
	}

	@Override
	public void show() {
		World.controller = new World(game, this);
		World.controller.show();
		WallJumper.currentScreen = this;
		
	}

	@Override
	public void hide() {
		World.controller.hide();
		
		
	}

	@Override
	public void pause() {
		super.pause();
		World.controller.pause();
	}

	@Override
	public void resume() {
		World.controller.resume();
	}

	@Override
	public void dispose() {
		World.controller.dispose();
	}
	
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button){
		return World.controller.handleTouchInput(screenX, screenY, pointer, button);
		
	}

	public boolean handleKeyInput(int keycode) {
		
		return World.controller.handleKeyInput(keycode);
	}
	
	//CHANGE LEVEL METHODS
	public void nextScreen(){
		ScreenTransition transition = ScreenTransitionSlice.init(.6f, ScreenTransitionSlice.UP_DOWN, 10,
				Interpolation.pow2Out);
		game.setScreen(new StoryScreen(game), transition);
	}
	
	
	//Set spawnpoint to null, destroy and init world controller and go to next level
	public void nextLevel(){
		
		WallJumper.level++;
		World.controller.setSpawnPoint(null, false);
		World.controller.destroy();
//		World.controller.init();
		nextScreen();
	}
	
	public void changeScreen(AbstractScreen screen) {
		((Game) Gdx.app.getApplicationListener()).setScreen(screen);
	}

	@Override
	public InputProcessor getInputProcessor() {
		return InputManager.inputManager;
	}

}
