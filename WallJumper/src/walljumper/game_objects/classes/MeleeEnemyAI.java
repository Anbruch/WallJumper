package walljumper.game_objects.classes;

import walljumper.game_objects.classes.ManipulatableObject.STATE;
import walljumper.game_objects.classes.ManipulatableObject.VIEW_DIRECTION;

public class MeleeEnemyAI {

	public ManipulatableObject target;
	public ManipulatableObject self;
	private AISTATE aiState;
	
	public enum AISTATE{
		MOVING, ATTACKING, WAITING;
	}
	
	public MeleeEnemyAI(ManipulatableObject self, ManipulatableObject target){
		this.self = self;
		this.target = target;
		aiState = AISTATE.WAITING;
	}
	
	public void setTarget(ManipulatableObject target){
		this.target = target;
	}
	
	public void update(){
		strategize();
	}
	//default implementation of this will move towards target
	//and attack
	private void strategize() {
		if(aiState == AISTATE.WAITING)
			lookForTarget();
		else if(aiState == AISTATE.MOVING){
			moveToTarget();
		}else if(aiState == AISTATE.ATTACKING){
			attackTarget();
		}
	}

	private void moveToTarget() {
		if(target.position.y - self.position.y > self.bounds.height / 2 || (self.xCollision && self.state != STATE.JUMPING)){
			self.jump();
			
		}
		//target is to the right
		if(target.position.x - self.position.x >  self.bounds.width / 2){
			if(self.viewDirection == VIEW_DIRECTION.left || aiState != AISTATE.MOVING){
				self.stopMove();
				self.moveRight();
			}
			
		//target is to the left
		}else if(target.position.x - self.position.x < -self.bounds.width / 2){
			if(self.viewDirection == VIEW_DIRECTION.right || aiState != AISTATE.MOVING){
				self.stopMove();
				self.moveLeft();
			}

		}
		
		
		
	}

	private void attackTarget() {
		
	}

	private void lookForTarget() {
		if(Math.pow(Math.abs(target.position.x - self.position.x), 2) + 
				Math.pow(Math.abs(target.position.y - self.position.y), 2) < 400 * 400){
			aiState = AISTATE.MOVING;
		}
	}
}
