package walljumper.tools;

import walljumper.game_objects.classes.ManipulatableObject;
import walljumper.screens.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.WallJumper;

public class InputManager extends InputAdapter {
	
	
	public static InputManager inputManager = new InputManager();
	
	public Array<ManipulatableObject> controllableObjects;
	
	private InputManager(){
		
		
	}
	public void init(){
		
		//Will send input to any objects that take it
		controllableObjects = new Array<ManipulatableObject>();
		Gdx.input.setInputProcessor(this); //Only this object receives player input
	}
	public void addObject(ManipulatableObject object){
		controllableObjects.add(object);
	}
	@Override
	public boolean keyDown(int keycode) {

		if(keycode == Keys.ESCAPE){
			Gdx.app.exit();
			return false;
		}
		
		if(!WallJumper.currentScreen.handleKeyInput(keycode))
			return false;

		
		if(WallJumper.paused || World.controller.countDown > 0){
			return false;
		}
		//Send input to the controlled objects
		for(ManipulatableObject target:controllableObjects){
			target.actOnInputKeyDown(keycode);
		}
		
		return false;
	}

	
	@Override
	public boolean keyUp(int keycode) {
		
		//Send key up input to controlled objects
		for(ManipulatableObject target:controllableObjects){
			target.actOnInputKeyUp(keycode);
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		
		if(!WallJumper.currentScreen.handleTouchInput(screenX, screenY, pointer, button)){
			
			return false;
		}
		
		for(ManipulatableObject target:controllableObjects){
			target.actOnInputTouch(screenX, screenY, pointer, button);
		}
		
		
		
		
		return false;
	}


	private void checkPausePressed() {
		
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	public ManipulatableObject getPlayer() {
		// TODO Auto-generated method stub
		return controllableObjects.get(0);
	}

}
