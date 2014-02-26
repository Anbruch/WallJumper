package walljumper.game_objects.terrain;

import walljumper.game_objects.AbstractGameObject;
import walljumper.tools.Assets;

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
		position.set(x, y);
		
		
		aniNormal = Assets.instance.portal.aniPortal;
		setAnimation(aniNormal);
		
		image = animation.getKeyFrame(stateTime);
		dimension.set(image.getRegionWidth(), image.getRegionHeight());
	}
	
	@Override
	public void render(SpriteBatch batch) {

		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set(image.getRegionWidth(),
				image.getRegionHeight());
		// Draw
		batch.draw(image.getTexture(), position.x, position.y, 0, 0,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				false, false);

	}

}
