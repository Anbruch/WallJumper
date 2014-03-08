package walljumper.tools;

import walljumper.screens.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.me.walljumper.Constants;

public class WorldRenderer implements Disposable{
	private SpriteBatch batch;
	public static WorldRenderer renderer = new WorldRenderer();
	public OrthographicCamera camera;
	public OrthographicCamera background_camera;
	public TextureRegion background_image;
	
	private WorldRenderer(){
	}
	public void init(){
		
		batch = new SpriteBatch();
		background_image = Assets.instance.nightSky.nightSky;
		
		//Initialize main camera
		camera = new OrthographicCamera(Constants.viewportWidth, Constants.viewportHeight);
		camera.position.set(0, 0, 0);
		camera.setToOrtho(false);
		camera.update();
		
		background_camera = new OrthographicCamera(Constants.bgViewportWidth, Constants.bgViewportHeight);
		background_camera.position.set(0,0,0);
		background_camera.setToOrtho(false);
		background_camera.update();
		
	}
	
	private void renderWorld(){
		renderBackground(batch);
		
		World.controller.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		World.controller.render(batch);
		batch.end();
		
	}
	private void renderBackground(SpriteBatch batch) {
		// TODO Auto-generated method stub
		batch.setProjectionMatrix(background_camera.combined);
		batch.begin();
		
		batch.draw(background_image.getTexture(), 0, 0, background_camera.viewportWidth,
				background_camera.viewportHeight, background_image.getRegionX(), background_image.getRegionY(),
				background_image.getRegionWidth(), background_image.getRegionHeight(), false, false);
		batch.end();
	}
	public void resize(int width, int height){
		
		//Sets our units to be in relation to screen size
		camera.viewportHeight = (float)Constants.viewportHeight;
		camera.viewportWidth = (Constants.viewportHeight / (float)height) * (float)width;
		camera.update();
		
		background_camera.viewportHeight = Constants.bgViewportHeight;
		background_camera.viewportWidth = (Constants.bgViewportHeight / (float) height) * (float)width;
		background_camera.position.set(background_camera.viewportWidth / 2, background_camera.viewportHeight / 2, 100);
		background_camera.update();
		
	}
	public void render(){
		
		renderWorld();
	}
	@Override
	public void dispose() {
		batch.dispose();
	}

}
