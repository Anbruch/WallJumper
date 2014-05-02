package com.me.walljumper.game_objects.terrain.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class SpikeTrap extends AbstractGameObject{
	private SIDE side;
	private int lengthX, lengthY;
	public enum SIDE{
		TOP, BOT, LEFT, RIGHT
	}
	public SpikeTrap(float x, float y, int lengthX, int lengthY, float width, float height, SIDE side){
		init(x, y, lengthX, lengthY, width, height, side);
	}
	private void init(float x, float y, int lengthX, int lengthY, float width, float height, SIDE side){
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.side = side;
		dimension = new Vector2(width, height);
		switch(side){
		case TOP:
			position = new Vector2(x - 1, y);
			bounds = new Rectangle(position.x + .4f, position.y + .4f, lengthX * dimension.x + dimension.x - .8f, dimension.y - .8f);
			break;
		case BOT:
			position = new Vector2(x - 1, y + .3f);
			flipY = true;
			bounds = new Rectangle(position.x + .2f, position.y, lengthX * dimension.x + dimension.x - .2f, dimension.y);
			break;
		case LEFT:
			position = new Vector2(x, y - lengthY * dimension.y + 1.25f);
			rotation = 90;
			flipY = true;
			bounds = new Rectangle(position.x, position.y + .2f, lengthX - .35f, dimension.y * lengthY - .2f);
			break;
		case RIGHT:
			position = new Vector2(x, y - lengthY * dimension.y + 1.25f);
			rotation = 90;
			bounds = new Rectangle(position.x - .35f, position.y + .2f, lengthX, dimension.y * lengthY - .2f);
			break;
		}
		
		
		image = Assets.instance.trap.spike;
		
		
	}
	@Override 
	public void interact(AbstractGameObject couple){
		World.spiked = true;
		
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
