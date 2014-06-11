package com.me.walljumper.game_objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.walljumper.tools.Assets;

public class RiftFragment extends AbstractGameObject {
	
	public RiftFragment(float x, float y, float width, float height){
		
		//set up
		super(x, y, width, height);
		setRotationalVelocity(180);
		image = Assets.instance.pause.levelScroll;
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	//Use this override for single images that don't animate
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(image.getTexture(), position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				flipX, flipY);
	}

}
