package projectrain.game_objects.classes;

import projectrain.game_objects.AbstractGameObject;
import projectrain.game_objects.weapons.Weapon;
import projectrain.tools.LevelStage;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ManipulatableObject extends AbstractGameObject {

	protected Vector2 moveSpeed;
	protected VIEW_DIRECTION viewDirection;
	protected STATE state;
	protected AbstractGameObject target, collidingPlatformX, collidingPlatformY;
	protected boolean xCollision;
	private boolean right, left;
	private float deltax, deltay;
	protected Animation zAttack;

	public Weapon weapon;
	protected Animation aniJumping, aniWalling;
	private MeleeEnemyAI AI;
	public COMBAT combatState;
	private boolean wallJumped;
	
	public enum VIEW_DIRECTION {
		left, right
	}

	public enum STATE {
		JUMPING, GROUNDED, WALLING;
	}
	public enum COMBAT{
		ATTACKING, DEFENDING, NEUTRAL;
	}

	public ManipulatableObject() {
		
		//Set the Default States
		viewDirection = VIEW_DIRECTION.right;
		state = STATE.JUMPING;
		combatState = COMBAT.NEUTRAL;
		
		
		wallJumped = false;
		currentFrameDimension = new Vector2();
		

	}

	public void actOnInputKeyDown(int keycode) {
		// Movement, same among all characters
		switch (keycode) {
		
		case Keys.SPACE:

			jump();
			break;
		}
	}// END OF METHOD
	public boolean actOnInputTouch(int screenX, int screenY, int pointer, int button){
		
		jump();
		return false;
	}
	public void jump() {
		
		//change direction on touch
		if(left){
			stopMoveLeft();
			moveRight();
		}else{
			stopMoveRight();
			moveLeft();
		}
		
		//WALL JUMPING, CAN BE REMOVED 
		if(state == STATE.WALLING && !wallJumped && collidingPlatformX != null){
			velocity.y = moveSpeed.y;
			wallJumped = true;
		}
		
		//Jumping off ground
		if (state == STATE.GROUNDED) {
			velocity.y = moveSpeed.y;
			state = STATE.JUMPING;
			wallJumped = false;
			setAnimation(aniJumping);
		}
		
		
	}
	
	public void positionOnSidePlatform(){
		if(left){
			position.x = collidingPlatformX.position.x + collidingPlatformX.bounds.width;
		}else if(right){
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

	public void moveX(float deltaTime){
		// Used to check if grounded or not
		Vector2 curPosition = new Vector2(position);
		
		//change in X axis this frame
		deltax = velocity.x * deltaTime;
		
		//overriden in subclass of ManipulatableObject
		ensureCorrectCollisionBounds();
		
		if(AI != null){
			wallJumped = false;
		}
		//If you wall jump and come off the ledge, you can walljump again
		if(wallJumped && deltax != 0 && collidingPlatformX == null){
			wallJumped = false;
		}
		
		// Collision Check this object once for x, once for y
		if (!collision(deltax, 0)) {
			position.x += deltax;
			if(deltax != 0)
				collidingPlatformX = null;
			if(deltay != 0){
				if(state != STATE.JUMPING)
				setAnimation(aniJumping);

				state = STATE.JUMPING;
			}
			xCollision = false;
			
		//Hit a wall while walking
		//Everything below hit a wall in the x-axis
		}else if(deltay == 0){
			state = STATE.GROUNDED;
			setAnimation(aniNormal);
			xCollision = true;
			
		//If you're on the wall sliding down
		}else if(state != STATE.WALLING){
		
			
			state = STATE.WALLING;
			positionOnSidePlatform();

			setAnimation(aniWalling);
			xCollision = true;
			
		}else{
			xCollision = true;
		}
		

		
		// so you run when you land from jump
		if (animation != aniRunning && state == STATE.GROUNDED
				&& position.x != curPosition.x)
			setAnimation(aniRunning);
		
		//or if you just land, you don't want to be in jumping animation
		else if (state == STATE.GROUNDED && position.x == curPosition.x
				&& animation == aniRunning) {
			setAnimation(aniNormal);
		}
	}
	public void moveY(float deltaTime){
		//change in y this frame
		deltay = velocity.y * deltaTime;
		
		//overriden by whatever subclass of manipulatable object
		ensureCorrectCollisionBounds();

		//If you didn't collide in y axis,
		//add deltaY to the position.y
		if (!collision(0, deltay)) {
			position.y += deltay;
			
		//if you did collide with something,
		//in the Y AXIS ONLY, set velocity to 0
		}else{
			velocity.y = 0;	
			deltay = 0;
		}
		
		//If you're in the air, set state to jumping
		if(deltay != 0){
			collidingPlatformY = null;
			
		//else you've either hit the top of the top of the platform
		//or one of the other 3 sides.
		} else {
			//If you hit the top of the platform,
			//set state to grounded and velocity to 0, 
			//and position to the top of the platform
			if (position.y > collidingPlatformY.position.y
					+ collidingPlatformY.bounds.height) {
				setAnimation(aniNormal);
				state = STATE.GROUNDED;
				velocity.y = 0;
				position.y = collidingPlatformY.position.y
						+ collidingPlatformY.bounds.height;
			}

		}

	}
	@Override
	public void update(float deltaTime) {
		if(AI != null)
			AI.update();
		
		checkCombatState();
		super.update(deltaTime);
		moveX(deltaTime);
		moveY(deltaTime);
		

		// Just to clarify where the rectangle ended
		bounds.setPosition(position);
		

	}

	//This method takes the MO (manipulatable object) and 
	//Sets it's animation back to what it should be after an attack
	//animation is finished
	protected void checkCombatState() {
		if(combatState == COMBAT.ATTACKING || combatState == COMBAT.DEFENDING){
			velocity.x = 0;
			
			//if MO attack animation is finished
			
			if(animation.isAnimationFinished(stateTime)){
				combatState = COMBAT.NEUTRAL;
				ensureMoving();
			}
			
		}
	}

	private void ensureMoving() {
		//Make him move left or right
		//seemlessly from an attack
		if(left){
			moveLeft();
		}else if(right){
			moveRight();
		}
		
		//Go from attack animation to jumping
		if(state ==  STATE.JUMPING){
			setAnimation(aniJumping);
		}
	}

	//To be overridden in subclasses
	protected void ensureCorrectCollisionBounds() {

	}

	private boolean collision(float deltaX, float deltaY) {

		// Set bounds to where this object will be after adding
		// the velocity of this frame to check and see if we are
		// going to collide with anything
		bounds.setPosition(position.x + deltaX, position.y + deltaY);

		// Iterate through platforms
		for (AbstractGameObject platform : LevelStage.platforms) {

			// If collision
			if (bounds.overlaps(platform.bounds)) {
				if(deltaX != 0){
					collidingPlatformX = platform;
					deltax = 0;
				}
				if(deltaY != 0)
					collidingPlatformY = platform;
				
				return true;
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
		batch.draw(image.getTexture(), position.x, position.y, 0, 0,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.left, false);

	}

}