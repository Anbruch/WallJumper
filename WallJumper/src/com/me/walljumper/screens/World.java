package com.me.walljumper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.game_objects.terrain.Portal;
import com.me.walljumper.gui.Button;
import com.me.walljumper.gui.PauseButton;
import com.me.walljumper.tools.Assets;
import com.me.walljumper.tools.AudioManager;
import com.me.walljumper.tools.CameraHelper;
import com.me.walljumper.tools.InputManager;
import com.me.walljumper.tools.LevelStage;
import com.me.walljumper.tools.WorldRenderer;

public class World  {
	public static World controller;
	public CameraHelper cameraHelper;
	public LevelStage levelStage;
	private ManipulatableObject player;
	public float countDown, levelTimer;
	public boolean started;
	public boolean blackHoled;
	public static Portal portal;
	public static boolean spiked;
	public Button button;
	private boolean init;
	
	private ManipulatableObject from;
	public AbstractGameObject to;
	private DirectedGame game;
	public GameScreen gameScreen;

	public World(DirectedGame game, GameScreen gameScreen) {
		this.game = game;
		this.gameScreen = gameScreen;
	}

	
	public void init() {
		
		//LOAD ASSETS FOR WORLD SCREEN (int WallJumper.World)-
			Array<String> files = new Array<String>();
			files.add("images/World" + WallJumper.World + ".pack");
			Assets.instance.init(new AssetManager(), files, false);
		
		//PlayMusic
		if(!AudioManager.instance.isPlaying())
			AudioManager.instance.playMusic(Assets.instance.music.world0);
		
		countDown = 0;
		WorldRenderer.renderer.init();
		spiked = false;
		cameraHelper = new CameraHelper();// Essentially makes the camera follow
		levelTimer = 0;
	
					
		InputManager.inputManager.init();
		levelStage = new LevelStage();
		
		// have a player variable here
		player = InputManager.inputManager.getPlayer();
		
		
		//Set up camera helper
		cameraHelper.setTarget(LevelStage.player);
		cameraHelper.applyTo(WorldRenderer.renderer.camera);
		
		
		//Set up pause button
		WallJumper.paused = true;
		WorldRenderer.renderer.pauseButton = new PauseButton();
		
		started = false;
		blackHoled = false;

	}

	public void render(SpriteBatch batch) {
		
		levelStage.render(batch);
	}

	public void update(float deltaTime) {

		levelStage.update(deltaTime);
		cameraHelper.update(deltaTime);
		WorldRenderer.renderer.weather.update(deltaTime);
		blackHoleMovement(deltaTime);
		checkDeath();
		
		
		
		
	}
	private void checkDeath() {
		if(spiked){
			restartLevel();
		}		
	}
	private void restartLevel(){
		
		World.controller.destroy();
		World.controller.init();
	}
	private void blackHoleMovement(float deltaTime){
		if(from != null && !from.continueToHole(deltaTime)){
			if(portal.isDeathPortal()){
				restartLevel();
				return;
			}
			gameScreen.backToLevelMenu();
		}
	}
	
	public void render(float delta) {
		//MAIN GAME UPDATE CALL
		if (!WallJumper.paused && countDown <= 0) {
			
			delta = delta < .25f ? delta : .25f;
			
			
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
				delta *= -1;
			}
			if(!blackHoled)
			levelTimer += delta;

			update(delta);

		}
		//Update countdown
		if (countDown > 0 || WallJumper.paused) {
			countDown -= delta;
			controller.cameraHelper.update(delta);
		}
		
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(WorldRenderer.renderer.camera != null){
			WorldRenderer.renderer.render();
		}

	}
	public InputProcessor getInputProcessor(){
		return InputManager.inputManager;
	}
	public float getLevelTime(){
		return levelTimer;
	}

	public void resize(int width, int height) {
		WorldRenderer.renderer.resize(width, height);
	}
	public boolean handleKeyInput(int keycode){
		if(Keys.SPACE == keycode){
			return startLevel();
		}
		return true;
	}
	
	//return false jumps back to handleTouchInput which 
	//instantle goes to inputManager.touchInput or keyInput
	private boolean startLevel(){
		if(!started){
			levelTimer = 0;
			started = true;
			WallJumper.paused = (WallJumper.paused == true) ? false : true;
			WorldRenderer.renderer.pauseButton.toggle();
			LevelStage.player.moveRight();
			
			return false;
		}
		return true;
	}
	//Return false to do a return in the method calling this
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button) {
		if(!startLevel()){
			return false;
		}
		
		// Top left corner is a pause button
		if (screenX < Gdx.graphics.getWidth() / 10
				&& screenY < Gdx.graphics.getHeight() / 10) {
			WallJumper.paused = (WallJumper.paused == true) ? false : true;
			WorldRenderer.renderer.pauseButton.toggle();
			return false;
			// If paused, don't send touch inputs
		} else if (WallJumper.paused){
			WallJumper.paused = false;
			WorldRenderer.renderer.pauseButton.toggle();

			return false;
		
		}
		
		return true;
	}

	public void show() {
		World.controller.init();
		
	}

	public void hide() {
		World.controller.destroy();
	}

	public void pause() {

	}

	public void resume() {
	}

	public void dispose() {
		destroy();
	}

	public Rectangle getPlayerRect() {
		return player.bounds;
	}

	public void destroy() {
		from = null;
		to = null;
		cameraHelper.destroy();
		if(levelStage != null)
		levelStage.destroy();
		player = null;
		WorldRenderer.renderer.destroy();
		Assets.instance.dispose();
		InputManager.inputManager.controllableObjects.clear();

	}
	//When called, automatically pauses game until countdown < 0
	public void countDown() {
		countDown = 1.5f;
	}
	public void moveTowards(ManipulatableObject from, AbstractGameObject to, float time) {
		if(!blackHoled){
			this.from = from;
			this.to = to;
			from.time = time;
			from.stopMove();
			from.velocity.set(0,0);
			from.setAnimation(from.aniJumping);
			blackHoled = true;
			from.acceleration.set(0, 0);
			from.moveToSomethingOverTime = time;
			from.bhOriginPos = new Vector2(from.position);
			from.deltaPosition = new Vector2(to.position.x + to.dimension.x / 2 - (from.position.x + from.dimension.x / 2) + .5f,
					to.position.y + to.dimension.y / 2  - (from.position.y + from.dimension.y / 2) + .6f);
		
		}
	}
	

}
