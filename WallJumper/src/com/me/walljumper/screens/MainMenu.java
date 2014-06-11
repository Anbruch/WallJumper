package com.me.walljumper.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.me.walljumper.ActorAccessor;
import com.me.walljumper.Constants;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.Profile;
import com.me.walljumper.ProfileLoader;
import com.me.walljumper.WallJumper;
import com.me.walljumper.gui.Button;
import com.me.walljumper.gui.Image;
import com.me.walljumper.gui.Scene;
import com.me.walljumper.gui.SceneAssets;
import com.me.walljumper.gui.SceneObject;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;
import com.me.walljumper.tools.Assets;

public class MainMenu extends ScreenHelper{

	Image title, bg, platform;
	Button play, tutorial, options;
	public MainMenu(DirectedGame game) {
		super(game);
	}

	private Scene scene;
	
	private TweenManager twnManager;

	@Override
	public void show() {
		//LOAD ASSETS FOR UI
		Array<String> paths = new Array<String>();
		paths.add("images/ui.pack");
		SceneAssets.instance = new SceneAssets(new AssetManager(), paths);
		
		//Set up scene
		scene = new Scene(this, game);
		Gdx.input.setInputProcessor(scene);
		WallJumper.currentScreen = this;
		
		ProfileLoader.init();
		rebuildStage();
		
	}
	private void rebuildStage(){
		SceneObject.setCamera(Scene.camera);
		//Background image
		bg = new Image(true, "bg" + (int)(Math.random() * WallJumper.numWorlds + 1), 
				-60, 0, Constants.bgViewportWidth + 120, Constants.bgViewportHeight);
		scene.add(bg);
		
		//PLATFORM/RIFTRUNNER IMAGE
		//platform + runner image
		platform = new Image(false, "startscreen", 0, 0, Constants.bgViewportWidth, Constants.bgViewportHeight);
		scene.add(platform);
		
		
		//TITLE IMAGE
		//Make title, set scale to zero so the tweenManager brings it up to 1
		float titleWidth = 666, titleHeight = 271;
		title = new Image(false, "title", (float) (Constants.bgViewportWidth * Math.random()),
				(float) (Constants.bgViewportHeight * Math.random()), titleWidth, titleHeight);
		title.setScale(0);
		title.setAfterTwn(new Vector2(Constants.bgViewportWidth / 2 - title.dimension.x / 2, Constants.bgViewportHeight / 2 + 20));
		scene.add(title);
		
		
		//PLAY BUTTON
		play = new Button(true, "button.up", "button.down", (float)(Math.random() * Constants.bgViewportWidth), 
				(float)(Math.random()) * Constants.bgViewportHeight, 200, 60){
			@Override
			public boolean clickRelease(){
				
				//Go straight to the Level Menu
				WallJumper.WorldNum = 1;
				Array<String> files = new Array<String>();
				files.add("images/World" + WallJumper.WorldNum + ".pack");
				Assets.instance.init(new AssetManager(), files, false);
				
				
				ScreenTransitionFade transition = ScreenTransitionFade.init(.4f);
				game.setScreen(new LevelMenu(game), transition);
				
				return false;
			}
		};
		//play.setToWrite("Play", play.dimension.x / 2 - 40, play.dimension.y /2 + 5);
		play.setScale(0);
		play.setAfterTwn(new Vector2(Constants.bgViewportWidth / 2 - play.dimension.x / 2, Constants.bgViewportHeight / 2 - 75));
		scene.add(play);
		
		//TUTORIAL BUTTON
		tutorial = new Button(true, "button.up", "button.down", (float)(Math.random() * Constants.bgViewportWidth), 
				(float)(Math.random()) * Constants.bgViewportHeight, 200, 60){
			@Override
			public boolean clickRelease(){
				ScreenTransitionFade transition = ScreenTransitionFade.init(.4f);
				game.setScreen(new TutorialScreen(game), transition);
				return false;
			}
		};
		//Go from small to big
		tutorial.setScale(0);
		tutorial.setAfterTwn(new Vector2(Constants.bgViewportWidth / 2 - tutorial.dimension.x / 2, Constants.bgViewportHeight / 2 - 175));
		scene.add(tutorial);
				
		
		buildTween();
		
		

	}

	@Override
	public void hide() {
		scene.destroy();
		twnManager.killAll();
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
		
		//CALLED AFTER TWEEN FINISHES, ClEAN UP CODE
		TweenCallback myCallBack = new TweenCallback(){
			@Override
			 public void onEvent(int type, BaseTween<?> source) {
				//WallJumper.profile = new Profile();
				//WallJumper.profile.setFile("data/profile.json");
				if(WallJumper.profile != null && WallJumper.profile.tutorial == 0){
					WallJumper.profile.tutorial = 1;
					
					ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
					game.setScreen(new TutorialScreen(game), transition);
					return;
				}
				play.bounds.setPosition(play.afterTwnPos);
				play.setToWrite("Play", play.dimension.x / 2 - 40, play.dimension.y /2 + 5, true);
				
				tutorial.bounds.setPosition(tutorial.afterTwnPos);
				tutorial.setToWrite("Tutorial", tutorial.dimension.x / 2 - 70, tutorial.dimension.y / 2 + 5, true);
				
				
				//BUILD ANOTHER TWEEN EVER 15 SECONDS
				Tween.registerAccessor(SceneObject.class, new ActorAccessor());
				Timeline.createSequence()
				.delay(5)
				
				.push(Tween.set(title, ActorAccessor.ROTATION).target(0))
				.push(Tween.set(play, ActorAccessor.ROTATION).target(0))
				.push(Tween.set(tutorial, ActorAccessor.ROTATION).target(0))

				.beginSequence()
					.push(Tween.to(title, ActorAccessor.ROTATION, .5f).target(180))
					.push(Tween.to(play, ActorAccessor.ROTATION, .7f).target(180))
					.push(Tween.to(tutorial, ActorAccessor.ROTATION, .7f).target(180))
					.end()
					
				.pushPause(1)
				
				.beginSequence()
					.push(Tween.to(tutorial, ActorAccessor.ROTATION, .7f).target(360))
					.push(Tween.to(play, ActorAccessor.ROTATION, .7f).target(360))
					.push(Tween.to(title, ActorAccessor.ROTATION, .5f).target(360))

					.end()
					.repeat(1000, 5).
					start(twnManager);
			}
		};
		
		//Set up register... value manipulator during tween
		Tween.registerAccessor(SceneObject.class, new ActorAccessor());
		
		Timeline.createSequence()
		.beginParallel()
			.push(Tween.to(title, ActorAccessor.SCALE, .5f).target(1.1f, 1.1f))
			.push(Tween.to(title, ActorAccessor.ROTATION, .5f).target(360))
			.push(Tween.to(title, ActorAccessor.XY, .5f)
					.target(title.afterTwnPos.x, title.afterTwnPos.y))
		.end()
		.beginParallel()
			.push(Tween.to(title, ActorAccessor.SCALE, .2f).target(1, 1))
			
			.push(Tween.to(play, ActorAccessor.SCALE, .7f).target(1.1f, 1.1f))
			.push(Tween.to(play, ActorAccessor.ROTATION, .7f).target(360))
			.push(Tween.to(play, ActorAccessor.XY, .7f)
					.target(play.afterTwnPos.x, play.afterTwnPos.y))
		.end()	
		.beginParallel()
			.push(Tween.to(play, ActorAccessor.SCALE, .2f).target(1, 1))
		
			.push(Tween.to(tutorial, ActorAccessor.SCALE, .2f).target(1, 1))
			.push(Tween.to(tutorial, ActorAccessor.SCALE, .7f).target(1.1f, 1.1f))
			.push(Tween.to(tutorial, ActorAccessor.ROTATION, .7f).target(360))
			.push(Tween.to(tutorial, ActorAccessor.XY, .7f)
					.target(tutorial.afterTwnPos.x, tutorial.afterTwnPos.y))
		.end()
		.beginSequence()
			.push(Tween.to(tutorial, ActorAccessor.SCALE, .2f).target(1, 1))
			
		.end()

		.setCallback(myCallBack).setCallbackTriggers(TweenCallback.COMPLETE).start(twnManager);
		
	}

}

	