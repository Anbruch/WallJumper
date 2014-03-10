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

public class World implements Screen{
	public static World controller = new World();
	public CameraHelper cameraHelper;
	public LevelStage levelStage;
	private ManipulatableObject player;
	public static int levelNum = 0;
	
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
		if(!WallJumper.paused){
			
			delta = delta < .25f ? delta : .25f;
			update(delta);
			
		}
		Gdx.gl.glClearColor(255, 255, 255, 0); //Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		WorldRenderer.renderer.render();
		
	}
	@Override
	public void resize(int width, int height) {
		
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
	
	
}
