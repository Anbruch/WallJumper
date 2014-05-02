package com.me.walljumper.game_objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class Rain extends AbstractGameObject{
	public boolean active;

	public Rain(String imageFile, float x, float y) {
		position = new Vector2(x, y);
		dimension = new Vector2(.15f, .3f);
		velocity.y = -5.46f;
		active = true;
		image = Assets.instance.getRainImage(imageFile);
	}
	@Override
	public void update(float deltaTime){
		position.y += velocity.y * deltaTime;
		if( World.controller.cameraHelper.hasTarget() &&
				position.y < World.controller.cameraHelper.getTarget().position.y - 20)
			active = false;
	}
	@Override
	public void render(SpriteBatch batch){
		batch.draw(image.getTexture(), position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				flipX, flipY);
	}

}
