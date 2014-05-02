package com.me.walljumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.ActorAccessor;
import com.me.walljumper.Constants;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;
import com.me.walljumper.gui.Image;
import com.me.walljumper.gui.Scene;
import com.me.walljumper.gui.SceneAssets;
import com.me.walljumper.gui.SceneObject;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;

public class MainMenu extends ScreenHelper{
	Image title, bg, platform;
	public MainMenu(DirectedGame game) {
		super(game);
	}

	private Scene scene;
	
	private TweenManager twnManager;

	@Override
	public void show() {
		//LOAD ASSETS FOR UI
		Array<String> paths = new Array<String>();
		paths.add("ui/MenuSkin.pack");
		SceneAssets.instance = new SceneAssets(new AssetManager(), paths);
		
		//Set up scene
		scene = new Scene(this, game);
		Gdx.input.setInputProcessor(scene);
		WallJumper.currentScreen = this;
		rebuildStage();
		
	}
	private void rebuildStage(){
		
		//Background image
		bg = new Image(true, "bg" + (int)(Math.random() * WallJumper.numWorlds), 0, 0, Constants.bgViewportWidth, Constants.bgViewportHeight){
			
			@Override
			public boolean clickRelease() {
				this.interactable = false;
				ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
				game.setScreen(new WorldScreen(game), transition);
				return false;
			}
		};
		scene.add(bg);
		
		//platform + runner image
		platform = new Image(false, "startscreen", 0, 0, Constants.bgViewportWidth, Constants.bgViewportHeight);
		scene.add(platform);
		
		//Make title, set scale to zero so the tweenManager brings it up to 1
		float titleWidth = 666, titleHeight = 271;
		title = new Image(false, "title", (float) (Constants.bgViewportWidth * Math.random()),
				(float) (Constants.bgViewportHeight * Math.random()), titleWidth, titleHeight);
		title.setScale(0);
		scene.add(title);
		
		buildTween();

	}

	@Override
	public void hide() {
		scene.destroy();
		twnManager = null;
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
		
	}
	
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button){

		return false;
	}
	@Override
	public InputProcessor getInputProcessor() {
		return scene;
	}



	
	@Override
	public void render(float delta) {
		
		//Have to do this
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		scene.update(delta);
		scene.render();
		
		
		twnManager.update(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}
	
	private void buildTween() {
		twnManager = new TweenManager();
		Tween.registerAccessor(SceneObject.class, new ActorAccessor());
		
		Timeline.createSequence()
		.beginParallel()
			.push(Tween.to(title, ActorAccessor.SCALE, 1).target(1.1f, 1.1f))
			.push(Tween.to(title, ActorAccessor.ROTATION, 1).target(360))
			.push(Tween.to(title, ActorAccessor.XY, 1)
					.target(Constants.bgViewportWidth / 2 - title.dimension.x / 2, Constants.bgViewportHeight / 3))
		.end()
		
		.beginSequence()
			.push(Tween.to(title, ActorAccessor.SCALE, .2f).target(1, 1))
		.end().start(twnManager);
		
	}

}

	