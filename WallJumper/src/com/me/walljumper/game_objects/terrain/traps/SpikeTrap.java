package com.me.walljumper.game_objects.terrain.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class SpikeTrap extends AbstractGameObject{
	
	private int lengthX, lengthY;
	
	public SpikeTrap(float x, float y, int lengthX, int lengthY, float width, float height, boolean flipX, boolean flipY, float rotation){
		init(x, y, lengthX, lengthY, width, height, flipX, flipY, rotation);
	}
	private void init(float x, float y, int lengthX, int lengthY, float width, float height, boolean flipX, boolean flipY, float rotation){
		position = new Vector2(x, y - lengthY * height + 1f);
		dimension = new Vector2(width, height);
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.flipX = flipX;
		this.flipY = flipY;
		if(!flipY && rotation < 90){
			position.y -= .3f;
			
		}
		position.x = rotation < 90 ? position.x - 1 : position.x;
		
		this.rotation = rotation;
		image = Assets.instance.trap.spike;
		bounds = new Rectangle(position.x + .5f, position.y, dimension.x * lengthX - .5f + 1, dimension.y * lengthY - .2f);
		
		if(lengthY > 1){
			bounds.width -= 1;
		}
	}
	@Override 
	public void interact(AbstractGameObject couple){
		World.controller.spiked = true;
		
	}
	@Override
	public void render(SpriteBatch batch){
		float relX = 0;
		
			//Top Row
		for(int j = 0; j < lengthY; j++){
			
			for(int i = 0; i < lengthX; i++){
				batch.draw(image.getTexture(), position.x + i * dimension.x + dimension.x,
						position.y + j * dimension.y, origin.x, origin.y, dimension.x, 
						dimension.y, 1, 1, rotation, image.getRegionX(), image.getRegionY(),
						image.getRegionWidth(), image.getRegionHeight(), flipX, flipY);
				relX += dimension.x;
			}
		}
	}
}
