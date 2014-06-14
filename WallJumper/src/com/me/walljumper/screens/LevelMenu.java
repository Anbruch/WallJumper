package com.me.walljumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.ActorAccessor;
import com.me.walljumper.Constants;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;
import com.me.walljumper.gui.Button;
import com.me.walljumper.gui.Image;
import com.me.walljumper.gui.Scene;
import com.me.walljumper.gui.SceneAssets;
import com.me.walljumper.gui.SceneObject;
import com.me.walljumper.screens.screentransitions.ScreenTransition;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;
import com.me.walljumper.tools.Assets;

public class LevelMenu extends ScreenHelper {
	
	
	private Image bg;
	private Button backButton;
	private Scene scene;
	private TweenManager twnManager;
	

	
	public LevelMenu(DirectedGame game) {
		super(game);
	}

	@Override
	public void show() {
		//LOAD ASSETS FOR UI
		Array<String> paths = new Array<String>();
		paths.add("images/ui.pack");
		SceneAssets.instance.dispose();
		SceneAssets.instance = new SceneAssets(new AssetManager(), paths);
		
		scene = new Scene(this, game);
		WallJumper.currentScreen = this;
		rebuildStage();
	}



	@Override
	public void render(float delta) {
		//Have to do this
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		scene.update(delta);
		scene.render();
		
		//twnManager.update(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}

	private void rebuildStage() {
		
		//Make background image 
		bg = new Image(false, "bg" + WallJumper.WorldNum, -60 , 0, Constants.bgViewportWidth + 120, Constants.bgViewportHeight);
		bg.setToWrite("World " + WallJumper.WorldNum, Constants.bgViewportWidth / 2 - 60, Constants.bgViewportHeight - 50, true);
		scene.add(bg);
		
		//Make each level button
		for(int i = 0; i < WallJumper.numButtonsPerPage; i++){
			float buttonWidth = 94 * 1.7f, buttonHeight = 104 * 1.7f;
			Button button = new Button(i < WallJumper.profile.lastLevelCompleted + 1 ? true : false, i < WallJumper.profile.lastLevelCompleted + 1 ? "levelbutton.up" : "levelButton_locked", "levelbutton.down", 
					Constants.levelOffsetX + (i % 6) * (buttonWidth+ Constants.buttonSpacingX), 
					Constants.bgViewportHeight + Constants.levelOffsetY - (i / 6 + 1) * (buttonHeight + Constants.buttonSpacingY),
					buttonWidth, buttonHeight){
				@Override
				public boolean clickRelease() {
					
					//Set level to the button's number
					WallJumper.level = this.getNum();
					SceneAssets.instance.dispose();
					ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
					game.setScreen(new GameScreen(game), transition);
					return false;
				}
			};
			button.setNum(i + 1);
			scene.add(button);
			if(i > Constants.lastSeenLevelInMenu && i <= WallJumper.profile.lastLevelCompleted){
				button.setAnimation(SceneAssets.instance.ui.buttonAnimation);
			}
			if(i > WallJumper.profile.lastLevelCompleted)
				continue;
			
			//Setup the text  for each button
			float textOffsetX = button.getNum()	< 10 ? 8 : 15;
			button.setToWrite("" + button.getNum(), button.dimension.x / 2 - textOffsetX, button.dimension.y / 2 + 35, true);
			Image numRiftFrags = new Image(false, "riftFrag", button.position.x + button.dimension.x / 4, 
					button.position.y + button.dimension.y / 4, 30, 30);
			numRiftFrags.setToWrite("x" + WallJumper.profile.World1.get(i).getRiftFrags(),  20, 0, true);
			scene.add(numRiftFrags);
		}
		Constants.lastSeenLevelInMenu = WallJumper.profile.lastLevelCompleted;
		
		//Making back button
		backButton = new Button(true, "homeButton", "homeButton", 0,
				Constants.bgViewportHeight - 75, 77, 75) {
			@Override
			public boolean clickRelease() {
				
				//Transition back to the WorldScreen
				
				//Delete this world's assets
				Assets.instance.dispose();
				ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
				game.setScreen(new MainMenu(game), transition);
				return false;
			}
		};
		scene.add(backButton);
		
		
		
	}
	private void buildTween() {
		Tween.registerAccessor(SceneObject.class, new ActorAccessor());
		Array<SceneObject> children = scene.getArray();
				
		
		
	/*	.beginParallel()
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

		.setCallback(myCallBack).setCallbackTriggers(TweenCallback.COMPLETE).start(twnManager);	*/	
	}

	@Override
	public void hide() {
		scene.destroy();
		twnManager = null;
		scene = null;
	}

	@Override
	public void pause() {
		
	}
	@Override
	public InputProcessor getInputProcessor(){
		return scene;
	}
	@Override
	public void resume() {
		
	}

	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button){

		return false;
	}

	public boolean handleKeyInput(int keycode) {
		return false;
	}


}
