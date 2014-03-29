package com.me.walljumper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.WallJumper;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.game_objects.terrain.Portal;
import com.me.walljumper.gui.PauseButton;
import com.me.walljumper.tools.CameraHelper;
import com.me.walljumper.tools.InputManager;
import com.me.walljumper.tools.LevelStage;
import com.me.walljumper.tools.WorldRenderer;

public class World extends ScreenHelper {
	public static World controller = new World();
	public CameraHelper cameraHelper;
	public LevelStage levelStage;
	private ManipulatableObject player;
	public float countDown, levelTimer;
	public boolean started, finishedDestroy;
	public boolean blackHoled;
	public static Portal portal;
	public static boolean spiked;
	
	private ManipulatableObject from;
	public AbstractGameObject to;

	private World() {

	}

	public void init() {
		countDown = 0;
		finishedDestroy = false;
		WorldRenderer.renderer.init();
		spiked = false;
		cameraHelper = new CameraHelper();// Essentially makes the camera follow
		levelTimer = 0;
	
					
		InputManager.inputManager.init();
		levelStage = new LevelStage();
		
		// have a player variable here
		player = InputManager.inputManager.getPlayer();
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
		
		blackHoleMovement(deltaTime);
		if(spiked){
			World.controller.destroy();
			World.controller.init();
		}
		
		
	}
	private void blackHoleMovement(float deltaTime){
		if(from != null && !from.continueToHole(deltaTime)){
			if(portal.isDeathPortal()){
				World.controller.destroy();
				World.controller.init();
				return;
			}
			backToLevelMenu();
		}
	}
	public void backToLevelMenu(){
		World.controller.destroy();
		super.changeScreen(new LevelMenu());
	}
	@Override
	public void render(float delta) {
		//MAIN GAME UPDATE CALL
		if (!WallJumper.paused && countDown <= 0) {
			
			delta = delta < .25f ? delta : .25f;
			
			
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
				delta *= -1;
			}
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
	public float getLevelTime(){
		return levelTimer;
	}

	@Override
	public void resize(int width, int height) {

	}
	@Override
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
	@Override
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

	@Override
	public void show() {
		World.controller.init();
		WallJumper.currentScreen = this;
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		destroy();
	}

	public Rectangle getPlayerRect() {
		// TODO Auto-generated method stub
		return player.bounds;
	}

	public void destroy() {
		from = null;
		to = null;
		cameraHelper.destroy();
		levelStage.destroy();
		player = null;
		WorldRenderer.renderer.destroy();
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
			from.deltaPosition = new Vector2(to.position.x + to.dimension.x / 2 - (from.position.x + from.dimension.x / 2),
					to.position.y + to.dimension.y / 2  - (from.position.y + from.dimension.y / 2));
		
		}
	}
	public void nextLevel() {
		//World.controller.destroy();
	}

}
