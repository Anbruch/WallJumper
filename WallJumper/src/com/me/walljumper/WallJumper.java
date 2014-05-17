package com.me.walljumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.screens.MainMenu;
import com.me.walljumper.screens.ScreenHelper;
import com.me.walljumper.screens.World;

public class WallJumper extends DirectedGame {
	public static final int numWorlds = 2;
	public static final int numButtonsPerPage = 18;
	public static boolean paused;//Game being paused handled in main
	public static ScreenHelper currentScreen;
	public static int WorldNum = 0, level = 0;
	
	
	
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
		WorldNum = 0;
	}
	
	@Override
	public void resize(int width, int height) {
		currentScreen.resize(width, height);
	}

	@Override
	public void pause() {
		WallJumper.paused = true;
	}

	@Override
	public void resume() {

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