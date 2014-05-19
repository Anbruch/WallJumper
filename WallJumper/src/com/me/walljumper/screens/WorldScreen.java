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
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlice;
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
		rebuildStage();
	}

	private void rebuildStage() {
		SceneObject.setCamera(Scene.camera);
		Image bg = new Image(true, "bg" + WallJumper.WorldNum,
				50, 100, Constants.bgViewportWidth / 2, Constants.bgViewportHeight / 2){
			@Override
			public boolean clickRelease(){
				this.clickable = false;
				ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
				game.setScreen(new LevelMenu(game), transition);
				
				//LOAD ASSETS FOR WORLD SCREEN (int WallJumper.World)-
				Array<String> files = new Array<String>();
				files.add("images/World" + WallJumper.WorldNum + ".pack");
				Assets.instance.init(new AssetManager(), files, false);
				return false;
			}
			
		};
		bg.setToWrite("TAP ME", bg.dimension.x / 2 - 20,  bg.dimension.y / 2, true);
		scene.add(bg);
		
			
			//This button goes to the Next World
			Button nxtWrldButton = new Button(true, "button.down", "button.up",
					Constants.bgViewportWidth - 50f, 0, 150, 125){
				@Override
				public boolean clickRelease(){
					//Increment World and reload the world Screen
					
					WallJumper.WorldNum = WallJumper.WorldNum < WallJumper.numWorlds - 1 ? WallJumper.WorldNum + 1 : 0;
					ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
					game.setScreen(new WorldScreen(game), transition);
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
