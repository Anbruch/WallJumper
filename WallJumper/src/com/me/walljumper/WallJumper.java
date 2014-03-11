package com.me.walljumper;

import walljumper.screens.World;
import walljumper.tools.Assets;
import walljumper.tools.InputManager;
import walljumper.tools.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class WallJumper extends Game {
	public static boolean paused;//Game being paused handled in main
	
	//Variables for the world
	
	
	
	
	@Override
	public void create() {	
		
		Assets.instance.init(new AssetManager());//Make the Spritesheet to be cut from later
		InputManager.inputManager.init();
		((Game) Gdx.app.getApplicationListener()).setScreen(World.controller);

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
		WallJumper.paused = true;
	}

	@Override
	public void resume() {
		WallJumper.paused = false;

	}
}