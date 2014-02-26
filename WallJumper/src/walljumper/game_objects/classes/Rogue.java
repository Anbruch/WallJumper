package walljumper.game_objects.classes;

import walljumper.game_objects.classes.ManipulatableObject.COMBAT;
import walljumper.game_objects.classes.ManipulatableObject.VIEW_DIRECTION;
import walljumper.tools.Assets;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Rogue extends ManipulatableObject {
	
	public Rogue(){
		
	}
	public Rogue(float x, float y, float width, float  height){
		init(x, y, width, height);
		
	}
	private void init(float x, float y, float width, float  height){
		aniRunning = Assets.instance.rogue.aniRunning;
		aniNormal = Assets.instance.rogue.aniNormal;
		aniJumping = Assets.instance.rogue.aniJumping;
		aniWalling = Assets.instance.rogue.aniWalling;
		
		position.set(x, y);
		acceleration.set(0, -900);
		moveSpeed = new Vector2(300, 500);
		setAnimation(aniNormal);
		terminalVelocity.set(400, 600);
		dimension.set(width, height);
		bounds.set(0, 0, width - 47, height - 21);
		
		moveRight();
	}
	
	@Override
	protected void ensureCorrectCollisionBounds() {
		bounds.y = position.y;
		bounds.x = position.x + 7f;
		
	
	}
	@Override
	public void actOnInputKeyDown(int keycode){
		
		super.actOnInputKeyDown(keycode);
		if(keycode == Keys.Z && combatState != COMBAT.ATTACKING){
			combatState = COMBAT.ATTACKING;
			setAnimation(zAttack);
		}
		
	}
	
	@Override
	public void update(float deltaTime){
		
		super.update(deltaTime);
		
	
	}
	
	@Override
	public void render(SpriteBatch batch) {
		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set(image.getRegionWidth(),
				image.getRegionHeight());
		
		if(state == STATE.GROUNDED && animation.getPlayMode() == Animation.NORMAL){
			// Draw
			batch.draw(image.getTexture(), position.x, position.y, 0, 0,
					currentFrameDimension.x, currentFrameDimension.y, 1, 1,
					rotation, image.getRegionX(), image.getRegionY(),
					image.getRegionWidth(), image.getRegionHeight(),
					viewDirection == VIEW_DIRECTION.right, false);
			return;
		}
		
		// Draw
		batch.draw(image.getTexture(), position.x, position.y, 0, 0,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.left, false);

	}

}