package com.me.walljumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.screens.MainMenu;
import com.me.walljumper.screens.ScreenHelper;
import com.me.walljumper.screens.WorldScreen;
import com.me.walljumper.tools.Assets;
import com.me.walljumper.tools.WorldRenderer;

public class WallJumper extends DirectedGame {
	public static final int numWorlds = 2;
	public static final int numButtonsPerPage = 18;
	public static boolean paused;//Game being paused handled in main
	public static ScreenHelper currentScreen;
	public static int World = 0, level = 0;
	public static int set;
	
	public static Array<Array> Worlds = new Array<Array>();
	
	
	@Override
	public void create() {	
		Array<String> packs = new Array<String>();
		
		((DirectedGame) Gdx.app.getApplicationListener()).setScreen(new MainMenu(this));
		paused = false;
	}

	@Override
	public void dispose() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		currentScreen.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	public static int getNumLevelsForSet(int i) {
		int count = 0;

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