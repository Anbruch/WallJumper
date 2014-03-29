package com.me.walljumper;

import com.me.walljumper.screens.MainMenu;
import com.me.walljumper.screens.ScreenHelper;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.me.walljumper.tools.Assets;
import com.me.walljumper.tools.InputManager;
import com.me.walljumper.tools.WorldRenderer;

public class WallJumper extends Game {
	public static boolean paused;//Game being paused handled in main
	public static ScreenHelper currentScreen;
	public static int World = 0, level = 0;
	public static int set;
	
	
	
	@Override
	public void create() {	
		
		Assets.instance.init(new AssetManager());//Make the Spritesheet to be cut from later
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
		WorldRenderer.renderer.init();
		paused = false;
	}

	@Override
	public void dispose() {
		
	}
	/*
	@Override
	public void render() {		
		if(!paused){
			World.controller.update(Gdx.graphics.getDeltaTime());
		}
		
		Midnight Blue 2F2F4F
		Violet 4F2F4F
		Gdx.gl.glClearColor(255, 255, 255, 0); //Default background color
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		WorldRenderer.renderer.render();

		
	}
	*/
	@Override
	public void resize(int width, int height) {
		WorldRenderer.renderer.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public static int getNumLevelsForSet(int i) {
		int count = 0;
		System.out.println("levels/" + ("World" + WallJumper.World) + ("/s" + i) + "/l" + 0 + ".png");

		while(Gdx.files.internal("levels/" + ("World" + WallJumper.World) + ("/s" + i) + "/l" + count + ".png").exists()){
			count++;
		}
		return count;

	}

	public static int getNumSetsOfLevels() {
		int count = 0;
		while(Gdx.files.internal("levels/" + ("World" + WallJumper.World) + ("/s" + count) + "/l" + 0 + ".png").exists()){
			count++;
		}
		return count;
	}
}