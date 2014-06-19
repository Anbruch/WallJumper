package com.me.walljumper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.Constants;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;
import com.me.walljumper.gui.Button;
import com.me.walljumper.gui.Scene;
import com.me.walljumper.gui.SceneAssets;
import com.me.walljumper.screens.screentransitions.ScreenTransition;
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlice;
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlide;

public class StoryScreen extends AbstractScreen {
	Scene stage;
	public StoryScreen(DirectedGame game) {
		super(game);
		//LOAD ASSETS FOR UI
		Array<String> paths = new Array<String>();
		paths.add("images/ui.pack");
		SceneAssets.instance.dispose();
		SceneAssets.instance = new SceneAssets(new AssetManager(), paths);
		WallJumper.currentScreen = this;
		stage = new Scene(this, game);
		
		
		rebuildStage();
		
	}

	private void rebuildStage() {
		
		Button toLevelButton = new Button(true,"button.up", "button.down", Constants.bgViewportWidth - 100, 0, 100, 30){
			@Override
			public boolean clickRelease() {
				ScreenTransition transition =  ScreenTransitionSlide.init(.5f, ScreenTransitionSlide.DOWN, true, Interpolation.elastic);
				game.setScreen(new GameScreen(game), transition);
				return false;
			}
		};
		stage.add(toLevelButton);
	}
	@Override
	public void render(float delta) {
		//Have to do this
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		stage.update(delta);
		stage.render();
		
		//twnManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.resize(width, height);
	}
	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}

}
