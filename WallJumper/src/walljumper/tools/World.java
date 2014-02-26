package walljumper.tools;

import walljumper.game_objects.classes.ManipulatableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World implements Screen{
	public static World controller = new World();
	public CameraHelper cameraHelper;
	public LevelStage levelStage;
	private ManipulatableObject player;
	
	private World(){
		
	}
	public void init(){
		
		cameraHelper = new CameraHelper();//Essentially makes the camera follow player
		levelStage = new LevelStage();
		
		//have a player variable here
		player = InputManager.inputManager.getPlayer();
		cameraHelper.setTarget(player);	
		
	}
	
	public void render(SpriteBatch batch){
		levelStage.render(batch);
	}
	public void update(float deltaTime){
		
		levelStage.update(deltaTime);
		cameraHelper.update(deltaTime);
	}
	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(255, 255, 255, 0); //Default background color
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		WorldRenderer.renderer.render();
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		World.controller.init();

	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	
}
