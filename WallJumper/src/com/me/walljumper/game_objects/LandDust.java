package com.me.walljumper.game_objects;

import com.me.walljumper.tools.Assets;

public class LandDust extends AbstractGameObject {
	
	public LandDust(float x, float y, float width, float height,
			boolean flipX, boolean flipY){
		super(x, y, width, height, flipX, flipY);
		setAnimation(Assets.instance.particle.landAnimation);
		
	}

}
