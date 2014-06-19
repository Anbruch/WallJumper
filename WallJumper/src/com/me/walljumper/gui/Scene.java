package com.me.walljumper.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.Constants;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.screens.GameScreen;
import com.me.walljumper.screens.AbstractScreen;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;
import com.me.walljumper.tools.CameraHelper;

public class Scene implements InputProcessor {
	public static Scene curScene;
	public static String lastScene;
	public static BitmapFont curFont;
	private SpriteBatch batch;
	private Array<SceneObject> children;
	public static OrthographicCamera camera;
	public CameraHelper cameraHelper;
	public static Button clickedButton;
	private AbstractScreen screenHelper;
	private DirectedGame game;
	
	
	/**How to use this scene: 
	 * 1st step, create a Scene object.
	 * 2nd step, add SceneObject children by doing scene.add(child);
	 * Now, all your children are on the screen and clickable or not
	 * You can override specific event methods such as
	 * clickedDown() || clickedRelease()  as so
	 * Button button = new Button(params...){
	 * @override
	 * public void clickedDown(){
	 * 	etc..
	 *  etc..
	 * 
	 * }
	 */
	
	public Scene(AbstractScreen screenHelper, DirectedGame game){
		curScene = this;
		children = new Array<SceneObject>();
		batch = new SpriteBatch();
		this.screenHelper = screenHelper;
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		curFont = new BitmapFont(Gdx.files.internal("Font/white.fnt"));

		
		camera = new OrthographicCamera(Constants.bgViewportWidth, Constants.bgViewportHeight);
		camera.position.set(0, 0, 0);
		camera.setToOrtho(false);
		cameraHelper = new CameraHelper();
		cameraHelper.setZoom(1, true);
		cameraHelper.setPosition(Constants.sceneCamX, Constants.sceneCamY);
		SceneObject.setCamera(camera);
		//Set up camera helper
		cameraHelper.applyTo(camera);
		camera.update();

	}
	
	public void add(SceneObject child){
		children.add(child);
	}
	public Array<SceneObject> getArray(){
		return children;
	}
	
	//Draws font at bottom left corner of the object + the offsets
	public void writeToWorld(String str, SceneObject obj, float xOffset, float yOffset){
		if(batch.isDrawing())
		curFont.draw(batch, str, obj.position.x + xOffset, obj.position.y + yOffset);
		
	}
	
	/*
	 * updates the entire array
	 */
	public void update(float deltaTime){

		for(SceneObject obj : children){
			obj.update(deltaTime);
		}
		cameraHelper.update(deltaTime);
		cameraHelper.applyTo(camera);
	}	
	

	public void render(){
		
		//Apply camera changes from cameraHelper, and start drawing
		cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(SceneObject obj : children){
			obj.render(batch);
		}
		batch.end();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		
		switch (keycode){
			case Keys.UP:
				cameraHelper.setPosition(camera.position.x, camera.position.y + Constants.cameraPanVal);
				break;
			case Keys.DOWN:
				cameraHelper.setPosition(camera.position.x, camera.position.y - Constants.cameraPanVal);
				break;
			case Keys.RIGHT:
				cameraHelper.setPosition(camera.position.x + Constants.cameraPanVal, camera.position.y);
				break;
			case Keys.LEFT:
				cameraHelper.setPosition(camera.position.x - Constants.cameraPanVal, camera.position.y);
		
		}
				
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
		
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//Sends all touch down coordinates to the children
		for(SceneObject objects : children){
			if(objects.touchDown(screenX, screenY, pointer, button)){
				break;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//Sends all the on release touch coordinates to children
		for(SceneObject objects : children){
			objects.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//Sends all the on release touch coordinates to children
		for(SceneObject objects : children){
			objects.touchDragged(screenX, screenY, pointer);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		return false;
	}

	
	public void resize(int width, int height) {
		
		camera.viewportHeight = Constants.bgViewportHeight;
		camera.viewportWidth = (Constants.bgViewportHeight / (float) height) * (float)width;
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 100);
		camera.update();		
	}

	public void destroy() {
		children = null;
		cameraHelper = null;
		batch.dispose();
		
	}

	public int numChildren() {
		return children.size;
	}

}
