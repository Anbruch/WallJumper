package com.me.walljumper.tools;

import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.gui.SceneObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.WallJumper;
import com.me.walljumper.screens.World;

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
		//Sends all touch down coordinates to the children
		for(SceneObject objects : WorldRenderer.renderer.getSceneObjects()){
			if(objects.touchDown(screenX, screenY, pointer, button)){
				break;
			}
		}
		
		if(!WallJumper.currentScreen.handleTouchInput(screenX, screenY, pointer, button)){
			
			return false;
		}
		
		for(ManipulatableObject target:controllableObjects){
			target.actOnInputTouch(screenX, screenY, pointer, button);
		}
		
		
		
		
		
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//Sends all the on release touch coordinates to children
		for(SceneObject objects : WorldRenderer.renderer.getSceneObjects()){
			objects.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//Sends all the on release touch coordinates to children
		for(SceneObject objects : WorldRenderer.renderer.getSceneObjects()){
			objects.touchDragged(screenX, screenY, pointer);
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
