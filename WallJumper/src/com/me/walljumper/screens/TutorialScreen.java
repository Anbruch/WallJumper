package com.me.walljumper.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;
import com.me.walljumper.tools.Assets;

public class TutorialScreen extends ScreenHelper {

	public TutorialScreen(DirectedGame game) {
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
		//LOAD ASSETS FOR WORLD SCREEN (int WallJumper.World)-
		Array<String> files = new Array<String>();
		files.add("images/World" + WallJumper.WorldNum + ".pack");
		Assets.instance.init(new AssetManager(), files, false);
		
		WallJumper.WorldNum = 0;
		WallJumper.level = 0;
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

	@Override
	public InputProcessor getInputProcessor() {

		return null;
	}

}
