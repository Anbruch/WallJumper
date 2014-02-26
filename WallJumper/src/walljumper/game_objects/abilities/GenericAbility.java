package walljumper.game_objects.abilities;

import walljumper.game_objects.AbstractGameObject;
import walljumper.game_objects.weapons.Weapon;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public abstract class GenericAbility extends AbstractGameObject{
	
	//class variables for every Ability
	public AbstractGameObject caller;
	public Weapon weapon;
	public Array<Rectangle> collisionRects;
	public Array<Circle> circleCollision;
	
	public GenericAbility(){
		
	}
}
