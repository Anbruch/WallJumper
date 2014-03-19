package walljumper.game_objects.classes;

import walljumper.game_objects.classes.ManipulatableObject.COMBAT;
import walljumper.game_objects.classes.ManipulatableObject.VIEW_DIRECTION;
import walljumper.tools.Assets;
import walljumper.tools.LevelStage;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;

public class Rogue extends ManipulatableObject {
	
	public Rogue(){
		
	}
	public Rogue(float x, float y, float width, float  height, int scale){
		init(x, y, width, height, scale);
		
	}
	private void init(float x, float y, float width, float  height, int scale){
		this.scale = scale;
		aniRunning = Assets.instance.rogue.aniRunning;
		aniNormal = Assets.instance.rogue.aniNormal;
		aniJumping = Assets.instance.rogue.aniJumping;
		aniWalling = Assets.instance.rogue.aniWalling;
		
		position.set(x, y - .3f);
		acceleration.set(0, -25f);
		moveSpeed = new Vector2(10, 15);
		state = STATE.GROUNDED;
		setAnimation(aniNormal);
		terminalVelocity.set(10, 20);
		dimension.set(width * scale, height * scale);
		bounds.set(0, 0, dimension.x, dimension.y);
	}
	
	@Override
	protected void ensureCorrectCollisionBounds() {
		bounds.y = position.y;
		bounds.x = position.x;
		
	
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
		currentFrameDimension.set((image.getRegionWidth() / 100.0f) * scale,
				 (image.getRegionHeight()/100.0f) * scale);
		
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