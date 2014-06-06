package com.me.walljumper.tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.walljumper.Constants;
import com.me.walljumper.WallJumper;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.terrain.Weather;
import com.me.walljumper.gui.Button;
import com.me.walljumper.gui.Image;
import com.me.walljumper.gui.PauseButton;
import com.me.walljumper.gui.SceneObject;
import com.me.walljumper.screens.LevelMenu;
import com.me.walljumper.screens.World;
import com.me.walljumper.screens.WorldScreen;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;

public class WorldRenderer implements Disposable{
	private SpriteBatch batch;
	public static WorldRenderer renderer = new WorldRenderer();
	public OrthographicCamera camera;
	public OrthographicCamera background_camera;
	public OrthographicCamera guiCamera;
	public TextureRegion background_image, pauseLayer;
	public PauseButton pauseButton;
	public BitmapFont whiteFont, blackFont;
	public Weather weather;
	public boolean weatherBool;
	private Array<SceneObject> sceneObjects;
	private String displayText;
	private boolean displayingText;
	private Vector2 textPos;

	
	private WorldRenderer(){
		
	}
	public void init(){
		
		batch = new SpriteBatch();
		weather = new Weather();
		weatherBool = WallJumper.WorldNum != 2 ? false : true;
		background_image = Assets.instance.nightSky.nightSky;
		pauseLayer = Assets.instance.pause.pauseLayer;
		
		sceneObjects = new Array<SceneObject>();

		
		
		//Initialize main camera
		camera = new OrthographicCamera(Constants.viewportWidth, Constants.viewportHeight);
		camera.position.set(0, 0, 0);
		camera.setToOrtho(false);
		camera.update();
		
		background_camera = new OrthographicCamera(Constants.bgViewportWidth, Constants.bgViewportHeight);
		background_camera.position.set(0,0,0);
		background_camera.setToOrtho(false);
		background_camera.update();
		
		guiCamera = new OrthographicCamera(Constants.bgViewportWidth, Constants.bgViewportHeight);
		guiCamera.position.set(0,0,0);
		guiCamera.setToOrtho(false);
		guiCamera.update();
		
		whiteFont = new BitmapFont(Gdx.files.internal("Font/white.fnt"));
		blackFont = new BitmapFont(Gdx.files.internal("Font/black.fnt"));
		
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
	}
	public void onScreen(AbstractGameObject obj){
		
	}
	public void writeToWorld(String string, float x, float y){
		
		whiteFont.draw(batch, string, x, y);
		
	}

	public void updateScene(float deltaTime){
		for(SceneObject objs:sceneObjects){
			objs.update(deltaTime);
		}
	}
	public void clearScene(){
		getSceneObjects().clear();
	}
	private void renderWorld(){
		//apply changes to camera, render level
		World.controller.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		World.controller.render(batch);
		weather.render(batch);

		batch.end();
		
	}
	private void renderBackground() {
		batch.setProjectionMatrix(background_camera.combined);
		batch.begin();
		
		batch.draw(background_image.getTexture(), 0, 0, background_camera.viewportWidth,
				background_camera.viewportHeight, background_image.getRegionX(), background_image.getRegionY(),
				background_image.getRegionWidth(), background_image.getRegionHeight(), false, false);
		batch.end();
	}
	private void renderGUI() {
		batch.setProjectionMatrix(guiCamera.combined);
		batch.begin();
		
		renderTapToStart();
		renderTimer();
		pauseButton.render(batch);

		
		renderTransparency();
		otherRenders();

			
		
		batch.end();
	}
	private void otherRenders() {
		//Render menu screen
		for(SceneObject objects: getSceneObjects()){
			objects.update(0);
			objects.render(batch);
		}
		
		if(displayingText)
			WorldRenderer.renderer.writeToWorld(displayText, textPos.x, textPos.y);

	}
	private void renderTimer() {
		if(World.controller.started){
			float curTime = World.controller.getLevelTime();
			
			//if curTime > 1, multiplying by 100 will move the the numbers after the decimal to 
			//front of decimal so they can be placed to the right of the decimal
			float afterDecimal = curTime > 1 ? (curTime % (int)(curTime)) * 100 : curTime * 100;
			String time = "" + (int)(curTime) + "." + (int)(afterDecimal);
			writeToWorld(time, Constants.bgViewportWidth / 2 - 30, Constants.bgViewportHeight - 50);
		}
	}
	private void renderTapToStart() {
		if(!World.controller.started)
			writeToWorld("Tap to start!", Constants.bgViewportWidth / 2 - 50, Constants.bgViewportHeight / 2 + 150);
			
	}
	public void displayToWorld(String text, Vector2 textPos){
		this.textPos = textPos;
		displayingText = true;
		displayText = text;
	}
	public void unDisplayToWorld(){
		displayText = null;
		this.textPos = null;
		displayingText = false;
	}
	public void resize(int width, int height){
		
		//Sets our units to be in relation to screen size
		camera.viewportHeight = (float)Constants.viewportHeight;
		camera.viewportWidth = (Constants.viewportHeight / (float)height) * (float)width;
		camera.update();
		
		background_camera.viewportHeight =  Constants.bgViewportHeight;
		background_camera.viewportWidth = (Constants.bgViewportHeight / (float) height) * (float)width;
		background_camera.position.set(background_camera.viewportWidth / 2, background_camera.viewportHeight / 2, 100);
		background_camera.update();
		
		guiCamera.viewportHeight = Constants.bgViewportHeight;
		guiCamera.viewportWidth = (Constants.bgViewportHeight / (float) height) * (float)width;
		guiCamera.position.set(guiCamera.viewportWidth / 2, guiCamera.viewportHeight / 2, 100);
		guiCamera.update();
		
	}
	public void render(){
		renderBackground();
		renderWorld();
		renderGUI();
	}
	private void renderTransparency() {
		/*if(WallJumper.paused)
			batch.draw(pauseLayer.getTexture(), 0, 0, guiCamera.viewportWidth,  guiCamera.viewportHeight,
					 pauseLayer.getRegionX(), pauseLayer.getRegionY(),
						pauseLayer.getRegionWidth(), pauseLayer.getRegionHeight(), false, false);
			*/
	}
	public void destroy(){
		guiCamera = null;
		pauseButton = null;
		camera = null;
		pauseLayer = null;
		background_image = null;
		background_camera = null;
		unDisplayToWorld();
		weather.destroy();
		clearScene();
	}
	@Override
	public void dispose() {
		batch.dispose();
		unDisplayToWorld();
	}
	public void pauseMenu(){
		SceneObject.setCamera(guiCamera);
		//Making Zoomout and Zoomin buttons
		Button zoomOut = new Button(true, Assets.instance.pause.zoomOut_Up, Assets.instance.pause.zoomOut_Down, 20, 20, 121, 65){
			@Override
			public void update(float deltaTime) {
				super.update(0);
				if(cur == down){
					World.controller.cameraHelper.zoomBy(.15f);
				}
				return;
			}
			
		};
		getSceneObjects().add(zoomOut);
		
		Button zoomIn = new Button(true, Assets.instance.pause.zoomIn_Up, Assets.instance.pause.zoomIn_Down, zoomOut.position.x + zoomOut.dimension.x, 20, 121, 65){
			@Override
			public void update(float deltaTime) {
				super.update(0);
				if(cur == down){
					World.controller.cameraHelper.zoomBy(-.15f);
				}
				return;
			}
			
		};
		getSceneObjects().add(zoomIn);
		
		//Magnifying glass		
		Image magnifyingGlass = new Image(false, Assets.instance.pause.magnifyingGlass, zoomIn.position.x - 35, zoomIn.position.y + 10, 70, 71);
		getSceneObjects().add(magnifyingGlass);
		
		
		
		//MAKING THE DPAD CAMERA POSITION CONTROLS
		Image mPad = new Image(false, Assets.instance.pause.mPad, Constants.bgViewportWidth * 5 / 7 + 85, 30, 215 * 1.3f, 215 * 1.3f);
		getSceneObjects().add(mPad);
		
		float width = 85 * 1.3f;
		//UP ZOOM
		Button zoomUp = new Button(true, Assets.instance.pause.mUp_up, Assets.instance.pause.mUp_down, mPad.position.x + mPad.dimension.x / 2 - width / 2, mPad.position.y + width / 4 + mPad.dimension.y / 2, width, width){
			@Override
			public void update(float deltaTime) {
				super.update(0);
				if(cur == down){
					World.controller.cameraHelper.setTarget(null);
					World.controller.cameraHelper.movePositionBy(new Vector2(0, 10));				
				}

			}
			
			
		};
		getSceneObjects().add(zoomUp);
		
		
		//DOWN ZOOM
		Button zoomDown = new Button(true, Assets.instance.pause.mDown_up, Assets.instance.pause.mDown_down, zoomUp.position.x, mPad.position.y, width, width){
			@Override
			public void update(float deltaTime) {
				super.update(0);
				if(cur == down){
					World.controller.cameraHelper.setTarget(null);
					World.controller.cameraHelper.movePositionBy(new Vector2(0, -10));				
				}

			}
			
			
		};
		getSceneObjects().add(zoomDown);
		
		
		//LEFT ZOOM
		Button zoomLeft = new Button(true, Assets.instance.pause.mLeft_up, Assets.instance.pause.mLeft_down, mPad.position.x, mPad.position.y + mPad.dimension.y / 2 - zoomDown.dimension.y / 2, width, width){
			@Override
			public void update(float deltaTime) {
				super.update(0);
				if(cur == down){
					World.controller.cameraHelper.setTarget(null);
					World.controller.cameraHelper.movePositionBy(new Vector2(-10, 0));				
				}

			}
			
		};
		getSceneObjects().add(zoomLeft);
		
		
		//RIGHT ZOOM
		Button zoomRight = new Button(true, Assets.instance.pause.mRight_up, Assets.instance.pause.mRight_down, mPad.position.x + mPad.dimension.x / 2 + zoomLeft.dimension.x / 4, zoomLeft.position.y, width, width){
			@Override
			public void update(float deltaTime) {
				super.update(0);
				if(cur == down){
					World.controller.cameraHelper.setTarget(null);
					World.controller.cameraHelper.movePositionBy(new Vector2(10, 0));				
				}

			}
			
		};
		getSceneObjects().add(zoomRight);
	}
	public void levelCompleteMenu() {
		SceneObject.setCamera(guiCamera);
		Image backgroundWindow = new Image(false, Assets.instance.pause.aniScroll, 0, 0, 400, 400){
			@Override
			public void onAnimationComplete() {
				Button levelMenu = new Button(true, Assets.instance.pause.menuButton_Down, Assets.instance.pause.menuButton_Up, 0, 0, 164 * .93f, 63 * .93f){
					@Override
					public boolean clickRelease() {
						World.controller.backTolevelMenu = true;
						return false;
						
					}
				};
				
				levelMenu.position.set(this.position.x - 33, this.position.y - 60);
				levelMenu.bounds.setPosition(levelMenu.position.x, levelMenu.position.y);
				getSceneObjects().add(levelMenu);
				
				Button homeMenu = new Button(true, Assets.instance.pause.homeButton_Down, Assets.instance.pause.homeButton_Up, 0, 0, 164 * .93f, 63 * .93f){
					@Override
					public boolean clickRelease() {
						World.controller.backToHomeMenu = true;
						return false;
						
					}
				};
				
				homeMenu.position.set(levelMenu.position.x + levelMenu.dimension.x + 3, levelMenu.position.y);
				homeMenu.bounds.setPosition(homeMenu.position.x, homeMenu.position.y);
				getSceneObjects().add(homeMenu);
				
				Button nextLevelButton = new Button(true, Assets.instance.pause.nextLevelButton_Down, Assets.instance.pause.nextLevelButton_Up, 0, 0, 164 * .93f, 63 * .93f){
					@Override
					public boolean clickRelease() {
						World.controller.nextLevel = true;
						return false;
						
					}
				};
				nextLevelButton.position.set(homeMenu.position.x + homeMenu.dimension.x + 3, homeMenu.position.y);
				nextLevelButton.bounds.setPosition(nextLevelButton.position.x, nextLevelButton.position.y);
				getSceneObjects().add(nextLevelButton);
			}
		};
		backgroundWindow.setScale(1.8f);
		backgroundWindow.position.set(Constants.bgViewportWidth / 2 - backgroundWindow.dimension.x / 2, Constants.bgViewportHeight / 5);
		getSceneObjects().add(backgroundWindow);
		
	}
	public Array<SceneObject> getSceneObjects() {
		return sceneObjects;
	}
	

}
