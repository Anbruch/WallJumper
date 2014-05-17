package com.me.walljumper;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.walljumper.gui.SceneObject;

public class ActorAccessor implements TweenAccessor<SceneObject>{

	
	public static final int XY = 0, RGB = 1, ROTATION = 2, SCALE = 3, ALPHA = 4;

	
	public int getValues(SceneObject target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case XY:
			returnValues[0] = target.position.x;
			returnValues[1] = target.position.y;
			return 2;
		case ROTATION:
			returnValues[0] = target.rotation;
			return 1;
		
	
		case SCALE: 
			returnValues[0] = target.scaleX;
			returnValues[1] = target.scaleY;
			return 2;
			
		case ALPHA:
			returnValues[0] = target.alpha;
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	
	public void setValues(SceneObject target, int tweenType, float[] newValues) {
		switch(tweenType){
		case XY:
			target.position.set(newValues[0], newValues[1]);
			break;
		case ROTATION:
			target.rotation = newValues[0];
			break;
		
		case SCALE:
			target.scaleX = newValues[0];
			target.scaleY = newValues[1];
			break;
		case ALPHA:
			target.alpha = newValues[0];
			break;
		default:
			assert false;
		}
	}



}
