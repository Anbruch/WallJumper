package walljumper.game_objects.terrain;

import walljumper.game_objects.AbstractGameObject;
import walljumper.tools.Assets;
import walljumper.tools.World;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Portal extends AbstractGameObject{
	
	public Portal(){
		
	}
	public Portal(float x, float y){
		init(x, y);
	}
	public void init(float x, float y){
		currentFrameDimension = new Vector2();
		aniNormal = Assets.instance.portal.aniPortal;
		setAnimation(aniNormal);
		image = animation.getKeyFrame(stateTime);
		scale = 5;
		dimension.set(image.getRegionWidth()/100.f * scale, image.getRegionHeight()/100.f * scale);
		position.set(x, y-1);
		bounds.set(x, y, dimension.x, dimension.y);
	}
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		if(bounds.overlaps(World.controller.getPlayerRect())){
			World.controller.destroy();
			World.controller.init();
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {

		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set((image.getRegionWidth() / 100.0f) * scale,
				(image.getRegionHeight() / 100.0f) * scale);
		// Draw
		batch.draw(image.getTexture(), position.x, position.y, 0, 0,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				false, false);

	}

}