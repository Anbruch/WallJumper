package com.me.walljumper.tutorial;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.tools.WorldRenderer;

public class Boundary extends AbstractGameObject {
	private String text;
	protected boolean finished;
	private Vector2 textPos;
	
	public Boundary(float x, float y, float width, float height, String text, float textX, float textY){
		super(x, y - height + 1, width, height);
		this.text = text;
		textPos = new Vector2(textX, textY);
		
	}
	@Override
	public void interact(AbstractGameObject couple) {
		//Only call this once
		if(!finished)
			WorldRenderer.renderer.displayToWorld(text, textPos);
		finished = true;
	
	}
	@Override
	public void render(SpriteBatch batch) {

	}
	

}
