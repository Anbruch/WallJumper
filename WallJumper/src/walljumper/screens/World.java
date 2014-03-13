package walljumper.screens;

import walljumper.game_objects.classes.ManipulatableObject;
import walljumper.tools.CameraHelper;
import walljumper.tools.InputManager;
import walljumper.tools.LevelStage;
import walljumper.tools.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.me.walljumper.WallJumper;

public class World extends ScreenHelper {
	public static World controller = new World();
	public CameraHelper cameraHelper;
	public LevelStage levelStage;
	private ManipulatableObject player;
	public static int levelNum = 0;
	public float countDown;

	private World() {

	}

	public void init() {
		countDown = 0;
		cameraHelper = new CameraHelper();// Essentially makes the camera follow
											// player
		levelStage = new LevelStage();

		// have a player variable here
		player = InputManager.inputManager.getPlayer();
		cameraHelper.setTarget(player);
		controller.countDown();

	}

	public void render(SpriteBatch batch) {
		
		levelStage.render(batch);
	}

	public void update(float deltaTime) {

		levelStage.update(deltaTime);
		cameraHelper.update(deltaTime);
	}

	@Override
	public void render(float delta) {
		//MAIN GAME UPDATE CALL
		if (!WallJumper.paused && countDown <= 0) {
			delta = delta < .25f ? delta : .25f;
			update(delta);

		}
		//Update countdown
		if (countDown > 0 || WallJumper.paused) {
			System.out.println(countDown);
			countDown -= delta;
			controller.cameraHelper.update(delta);
		}
		
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		WorldRenderer.renderer.render();
		

	}

	@Override
	public void resize(int width, int height) {

	}

	//Return false to do a return in the method calling this
	@Override
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button) {
		
		// Top left corner is a pause button
		if (screenX < Gdx.graphics.getWidth() / 10
				&& screenY < Gdx.graphics.getHeight() / 10) {
			WallJumper.paused = (WallJumper.paused == true) ? false : true;
			WorldRenderer.renderer.pauseButton.toggle();
			if (!WallJumper.paused)
				World.controller.countDown();
			return false;
			// If paused, don't send touch inputs
		} else if (WallJumper.paused || World.controller.countDown > 0)
			return false;
		
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
		// TODO Auto-generated method stub

	}

	public Rectangle getPlayerRect() {
		// TODO Auto-generated method stub
		return player.bounds;
	}

	public void destroy() {
		cameraHelper.destroy();
		levelStage.destroy();
		player = null;

	}
	//When called, automatically pauses game until countdown < 0
	public void countDown() {
		countDown = 1f;
	}

}
