package com.me.walljumper.screens;

import aurelienribon.tweenengine.TweenManager;

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
import com.me.walljumper.gui.Image;
import com.me.walljumper.gui.Scene;
import com.me.walljumper.gui.SceneAssets;
import com.me.walljumper.gui.SceneObject;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlide;
import com.me.walljumper.tools.Assets;


public class WorldScreen extends ScreenHelper {

	public WorldScreen(DirectedGame game) {
		super(game);
	}
	
	private Scene scene;
	//private TweenManager tweenManager;
	
	private TweenManager twnManager;

	
	
	@Override
	public InputProcessor getInputProcessor() {
		return scene;
	}
	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}
	@Override
	public void show() {
		//LOAD ASSETS FOR UI
		Array<String> paths = new Array<String>();
		paths.add("ui/MenuSkin.pack");
		
		
		SceneAssets.instance.dispose();
		SceneAssets.instance = new SceneAssets(new AssetManager(), paths);
		
		//More setup
		scene = new Scene(this, game);
		WallJumper.currentScreen = this;
		if(WallJumper.WorldNum < 1)
			WallJumper.WorldNum = 1;
		rebuildStage();
	}

	private void rebuildStage() {
		SceneObject.setCamera(Scene.camera);
		Image bg = new Image(false, "bg" + WallJumper.WorldNum,
				50, 100, Constants.bgViewportWidth / 2, Constants.bgViewportHeight / 2);
		scene.add(bg);
			//This button goes to the Next World
			Button nxtWrldButton = new Button(true, "NextWorldButton", "NextWorldButton",
					Constants.bgViewportWidth - 150f, 0, 150, 125){
				@Override
				public boolean clickRelease(){
					ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
					game.setScreen(new LevelMenu(game), transition);
					
					//LOAD ASSETS FOR WORLD SCREEN (int WallJumper.World)-
					Array<String> files = new Array<String>();
					files.add("images/World" + WallJumper.WorldNum + ".pack");
					Assets.instance.init(new AssetManager(), files, false);
					
					//Increment World and reload the world Screen
					/*WallJumper.WorldNum = WallJumper.WorldNum < WallJumper.numWorlds ? WallJumper.WorldNum + 1 : 1;
					ScreenTransitionSlide transition = ScreenTransitionSlide.init(.75f, ScreenTransitionSlide.RIGHT, false, Interpolation.exp5);
					game.setScreen(new WorldScreen(game), transition);*/
					return false;
				}
			};
			scene.add(nxtWrldButton);
			
	}

	@Override
	public void hide() {
		scene.destroy();
		scene = null;
	}
	
	@Override
	public void render(float delta) {

		//Have to do this
		Gdx.gl.glClearColor(0, 0, 0, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		scene.update(delta);
		scene.render();
				
	}

	

}
