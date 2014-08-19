package com.me.walljumper.game_objects.classes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.WallJumper;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.LandDust;
import com.me.walljumper.game_objects.particles.WallJumpParticle;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.LevelStage;
import com.me.walljumper.tools.WorldRenderer;

public class ManipulatableObject extends AbstractGameObject {

	protected Vector2 moveSpeed;
	public Vector2 deltaPosition, bhOriginPos;
	protected VIEW_DIRECTION viewDirection;
	protected STATE state;
	protected AbstractGameObject target, collidingPlatformX,
			collidingPlatformY;
	protected boolean xCollision;
	private boolean right, left;
	private float deltax, deltay, rotSpeed;
	public float moveToSomethingOverTime;
	protected Animation zAttack;
	protected Animation aniLanding;

	public Animation aniJumping, aniWalling;
	private MeleeEnemyAI AI;
	public COMBAT combatState;
	private boolean wallJumped;
	private float startFallTime;
	public float time;
	public float bhRadius;

	public enum VIEW_DIRECTION {
		left, right
	}

	public enum STATE {
		JUMPING, GROUNDED, WALLING;
	}

	public enum COMBAT {
		ATTACKING, DEFENDING, NEUTRAL;
	}

	public ManipulatableObject() {
		// Set the Default States
		viewDirection = VIEW_DIRECTION.right;
		state = STATE.JUMPING;
		combatState = COMBAT.NEUTRAL;
		startFallTime = 0;
		origin.set(dimension.x / 2, dimension.y / 2);
		rotSpeed = 220;
		bhOriginPos = new Vector2();

		wallJumped = false;
		currentFrameDimension = new Vector2();

	}

	public void actOnInputKeyDown(int keycode) {
		// Movement, same among all characters
		if (World.controller.blackHoled) {
			return;
		}
		switch (keycode) {

		case Keys.SPACE:

			jump();
			break;
		}
	}// END OF METHOD

	public boolean actOnInputTouch(int screenX, int screenY, int pointer,
			int button) {
		if (World.controller.blackHoled)
			return false;

		jump();
		return false;
	}

	public void jump() {

		// Jumping off ground
		if (state == STATE.GROUNDED) {
			velocity.y = moveSpeed.y;
			state = STATE.JUMPING;
			wallJumped = false;
			setAnimation(aniJumping);
			return;
		}

		// change direction on touch
		if (left) {
			stopMoveLeft();
			moveRight();
		} else {
			stopMoveRight();
			moveLeft();
		}

		// WALL JUMPING, CAN BE REMOVED
		if (state == STATE.WALLING && !wallJumped && collidingPlatformX != null) {
			velocity.y = moveSpeed.y;
			wallJumped = true;

			// Draws when he's jumping from the wall
			LevelStage.uncollidableObjects
					.add(new WallJumpParticle(
							viewDirection == VIEW_DIRECTION.right ? position.x
									: position.x + .1f, position.y
									- (dimension.y / 2), 1, 1,
							viewDirection == VIEW_DIRECTION.right ? false
									: true) {

						@Override
						public void animationComplete() {
							LevelStage.uncollidableObjects.removeValue(this,
									true);
						}
					});

		}

	}

	// Called when you hit a platform in the x-axis
	public void positionOnSidePlatform() {
		if (left) {
			position.x = collidingPlatformX.position.x
					+ collidingPlatformX.bounds.width;
		} else if (right) {
			position.x = collidingPlatformX.position.x - bounds.width;
		}
	}

	public void actOnInputKeyUp(int keycode) {

	}// End of actOnInput methods

	public void moveRight() {

		right = true;
		if (left) {
			if (state != STATE.JUMPING)
				setAnimation(aniNormal);
			velocity.x = 0;
			return;
		}
		// Animates the run if grounded
		if (state == STATE.GROUNDED) {
			setAnimation(aniRunning);
		}
		viewDirection = VIEW_DIRECTION.right;
		velocity.x = moveSpeed.x;

	}

	public void moveLeft() {
		// Set left to true so if we were holding right previous to this,
		// when we let go of right, it will move us left
		left = true;
		if (right) {
			velocity.x = 0;
			if (state != STATE.JUMPING)
				setAnimation(aniNormal);

			return;
		}

		// Sets up running animation if on ground
		if (state == STATE.GROUNDED) {
			setAnimation(aniRunning);
		}
		viewDirection = VIEW_DIRECTION.left;
		velocity.x = -moveSpeed.x;

	}

	public void addAI(MeleeEnemyAI abstractEnemyAI) {
		this.AI = abstractEnemyAI;
	}

	public void stopMoveRight() {

		// Set velocity to 0, check if left might be pressed
		velocity.x = 0;
		right = false;

		// Animates back to neutral
		if (state != STATE.JUMPING)
			setAnimation(aniNormal);

		if (left) {
			moveLeft();
		}
	}

	public void stopMoveLeft() {

		// Set velocity.x to 0, check if right might be pressed
		velocity.x = 0;
		left = false;
		// Sets animation back to neutral
		if (state != STATE.JUMPING)
			setAnimation(aniNormal);

		// Bug fix for if both buttons are down,
		// Left is released, then character should move right
		if (right) {
			moveRight();
		}
	}

	public void stopMove() {

		// Set velocity.x to 0, check if right might be pressed
		velocity.x = 0;
		left = false;
		right = false;
		// Sets animation back to neutral
		setAnimation(aniNormal);

	}

	private void moveUp() {

	}

	private void moveDown() {

	}

	private void stopMoveUp() {

	}

	private void stopMoveDown() {

	}

	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}

	public void moveX(float deltaTime) {
		// Used to check if grounded or not
		Vector2 curPosition = new Vector2(position);

		// change in X axis this frame
		deltax = velocity.x * deltaTime;

		// overriden in subclass of ManipulatableObject
		ensureCorrectCollisionBounds();

		if (AI != null) {
			wallJumped = false;
		}
		// If you wall jump and come off the ledge, you can walljump again
		if (wallJumped && deltax != 0 && collidingPlatformX == null) {
			wallJumped = false;
		}

		// Collision Check this object once for x only
		if (!collision(deltax, 0)) {
			position.x += deltax;
			if (deltax != 0)
				collidingPlatformX = null;

			// Jumping
			if (deltay != 0) {
				if (state != STATE.JUMPING)
					setAnimation(aniJumping);
				state = STATE.JUMPING;
			}
			xCollision = false;

			// Hit a wall while walking
			// Everything below hit a wall in the x-axis
		} else if (deltay == 0) {
			state = STATE.GROUNDED;
			setAnimation(aniNormal);
			xCollision = true;

			// If you're on the wall sliding down
		} else if (state != STATE.WALLING) {

			// Walling
			state = STATE.WALLING;
			positionOnSidePlatform();
			setAnimation(aniWalling);
			xCollision = true;

		} else {
			xCollision = true;
		}

		// so you run when you land from jump
		if (animation != aniRunning && state == STATE.GROUNDED
				&& position.x != curPosition.x)
			setAnimation(aniRunning);

		// or if you just land, you don't want to be in jumping animation
		else if (state == STATE.GROUNDED && position.x == curPosition.x
				&& animation == aniRunning) {
			setAnimation(aniNormal);
		}
	}

	public void moveY(float deltaTime) {
		// change in y this frame
		deltay = velocity.y * deltaTime;
		acceleration.y = Math.abs(velocity.y) < 1f ? Constants.gravity / 6
				: Constants.gravity;

		// overriden by whatever subclass of manipulatable object
		ensureCorrectCollisionBounds();

		// If you didn't collide in y axis,
		// add deltaY to the position.y
		if (!collision(0, deltay)) {
			position.y += deltay;

			// if you did collide with something,
			// in the Y AXIS ONLY, set velocity to 0
		} else {
			velocity.y = 0;
			if (position.y > collidingPlatformY.position.y)
				deltay = 0;
		}

		// If you're in the air, set state to jumping
		if (deltay != 0) {
			collidingPlatformY = null;

			// else you've either hit the top of the top of the platform
			// or one of the other 3 sides.
		} else {
			// If you hit the top of the platform,
			// set state to grounded and velocity to 0,
			// and position to the top of the platform
			if (collidingPlatformY != null
					&& position.y > collidingPlatformY.position.y
							+ collidingPlatformY.bounds.height) {
				if ((left || right) && xCollision == false)
					setAnimation(aniRunning);
				else
					setAnimation(aniNormal);
				state = STATE.GROUNDED;
				LevelStage.uncollidableObjects.add(new LandDust(position.x,
						collidingPlatformY.position.y
								+ collidingPlatformY.bounds.height + .1f, 0, 0,
						false, false) {
					@Override
					public void animationComplete() {
						LevelStage.uncollidableObjects.removeValue(this, true);
					}
				});
				velocity.y = 0;
				position.y = collidingPlatformY.position.y
						+ collidingPlatformY.bounds.height;
			}

		}

	}

	@Override
	public void update(float deltaTime) {

		if (World.controller.blackHoled) {
			return;
		}
		super.update(deltaTime);
		moveX(deltaTime);
		moveY(deltaTime);

		// Just to clarify where the rectangle ended
		bounds.setPosition(position);

	}

	// Return false jump to World.contrlr.
	public boolean continueToHole(float deltaTime) {
		if (moveToSomethingOverTime > 0) {

			rotation += deltaTime * rotSpeed;
			position.x = bhOriginPos.x + deltaPosition.x
					* ((time - moveToSomethingOverTime) / time);
			position.y = bhOriginPos.y + deltaPosition.y
					* ((time - moveToSomethingOverTime) / time);
			moveToSomethingOverTime -= deltaTime;
			scale /= 1.03f;
			super.update(deltaTime);
			return true;
		}

		return false;

	}

	// This method takes the MO (manipulatable object) and
	// Sets it's animation back to what it should be after an attack
	// animation is finished
	protected void checkCombatState() {
		if (combatState == COMBAT.ATTACKING || combatState == COMBAT.DEFENDING) {
			velocity.x = 0;

			// if MO attack animation is finished

			if (animation.isAnimationFinished(stateTime)) {
				combatState = COMBAT.NEUTRAL;
				ensureMoving();
			}

		}
	}

	private void ensureMoving() {
		// Make him move left or right
		// seemlessly from an attack
		if (left) {
			moveLeft();
		} else if (right) {
			moveRight();
		}

		// Go from attack animation to jumping
		if (state == STATE.JUMPING) {
			setAnimation(aniJumping);
		}
	}

	// To be overridden in subclasses
	protected void ensureCorrectCollisionBounds() {

	}

	private boolean collision(float deltaX, float deltaY) {

		// Set bounds to where this object will be after adding
		// the velocity of this frame to check and see if we are
		// going to collide with anything
		bounds.setPosition(position.x + deltaX, position.y + deltaY);

		// Iterate through platforms
		for (AbstractGameObject platform : LevelStage.backPlatforms) {

			// If collision
			if (bounds.overlaps(platform.bounds)) {
				if (deltaX != 0) {
					collidingPlatformX = platform;
					
					deltax = 0;
				}
				if (deltaY != 0) {
					collidingPlatformY = platform;
				}
				return true;
			}
		}
		// Iterate through platforms
		for (AbstractGameObject platform : LevelStage.platforms) {

			// If collision
			if (bounds.overlaps(platform.bounds)) {
				if (deltaX != 0) {
					collidingPlatformX = platform;
					deltax = 0;
				}
				if (deltaY != 0) {
					collidingPlatformY = platform;
				}
				return true;
			}
		}

		// Collide with (spikes, portals)
		for (AbstractGameObject interactable : LevelStage.interactables) {
			if (bounds.overlaps(interactable.bounds)) {
				interactable.interact(this);
			}
		}
		return false;

	}

	@Override
	public void render(SpriteBatch batch) {

		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set(image.getRegionWidth(),
				image.getRegionHeight());

		// Draw
		batch.draw(image.getTexture(), position.x, position.y, origin.x,
				origin.y, currentFrameDimension.x, currentFrameDimension.y, 1,
				1, rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.left, false);

	}

	// WALLJUMPER ONLY KINDA IDK
	public void fallingToDie(float deltaTime) {
		if (state == STATE.JUMPING && velocity.y < 0) {
			startFallTime += deltaTime;

			// This makes GameScreen call restartLevel()
			if (startFallTime > 1.5f && !World.controller.blackHoled) {
				World.restart = true;
				return;
			}

			// Makes the camera stop following Aeternus
			if (startFallTime > 1f && World.controller.cameraHelper.hasTarget()) {
				World.controller.cameraHelper.setTarget(null);
				return;
			}
		} else {
			startFallTime = 0;
			World.controller.cameraHelper.setTarget(this);
		}

	}// End method
}
