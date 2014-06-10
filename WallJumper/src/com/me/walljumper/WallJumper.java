package com.me.walljumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.me.walljumper.screens.GameScreen;
import com.me.walljumper.screens.MainMenu;
import com.me.walljumper.screens.ScreenHelper;
import com.me.walljumper.screens.TutorialScreen;
import com.me.walljumper.screens.World;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;
import com.me.walljumper.tools.WorldRenderer;

public class WallJumper extends DirectedGame {
	public static final int numWorlds = 2;
	public static final int numButtonsPerPage = 18;
	public static boolean paused;//Game being paused handled in main
	public static ScreenHelper currentScreen;
	public static int WorldNum = 0, level = 0, completedLevels;
	public static Profile profile;
	
	
	
	@Override
	public void create() {	
		
		((DirectedGame) Gdx.app.getApplicationListener()).setScreen(new MainMenu(this));
		paused = false;
	}

	@Override
	public void dispose() {
		if(World.controller != null)
		World.controller.dispose();
		World.controller = null;
		WorldNum = 1;
	}
	
	@Override
	public void resize(int width, int height) {
		currentScreen.resize(width, height);
	}

	@Override
	public void pause() {
		WallJumper.paused = true;
		if(WorldRenderer.renderer != null && WorldRenderer.renderer.pauseButton != null)
			WorldRenderer.renderer.pauseButton.toggle();
	}	

	@Override
	public void resume() {
		WallJumper.paused = false;
		if(WorldRenderer.renderer != null && WorldRenderer.renderer.pauseButton != null)
			WorldRenderer.renderer.pauseButton.toggle();
	}

	public static int getNumLevelsForSet(int i) {
		int count = 0;

		while(Gdx.files.internal("levels/" + ("World" + WallJumper.WorldNum) + ("/s" + i) + "/l" + count + ".png").exists()){
			count++;
		}
		return count;

	}

	public static int getNumSetsOfLevels() {
		int count = 0;
		
		while(Gdx.files.internal("levels/" + ("World" + WallJumper.WorldNum) + ("/s" + count) + "/l" + 0 + ".png").exists()){
			count++;
		}
		return count;
	}
}