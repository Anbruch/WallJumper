package projectrain.game_objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	
	//Physics
	public Vector2 acceleration;
	public Vector2 velocity;
	public Vector2 terminalVelocity; //Objects max speed magnitude
	public Rectangle bounds; // objects bounding box used for collision
	protected TextureRegion image;
	public float stateTime;
	
	public boolean looping;
	public Vector2 currentFrameDimension;
	public Animation aniRunning;
	public Animation aniNormal;
	public float rotation;
	public Animation animation;
	public boolean animationBool;
	
	public AbstractGameObject(){
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		
		acceleration = new Vector2();
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		bounds = new Rectangle();
	}
	public void update(float deltaTime){
		
		stateTime += deltaTime;
		
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		
		
		
		
		
		
	}
	public void setAnimation(Animation animation){
		if(animation.getPlayMode() == animation.NORMAL){
			looping = false;
		}else
			looping = true;
		this.animation = animation;
		stateTime = 0;
		
	}
	public void setImage(TextureRegion image){
		this.image = image;
	}
	protected void updateMotionX(float deltaTime){
		
		//Apply acceleration
		velocity.x += acceleration.x * deltaTime;
		//Make sure the object's velocity does not exceed the terminal velocity
		velocity.x = MathUtils.clamp(velocity.x, - terminalVelocity.x,  terminalVelocity.x);
		
		
		
		
	}
	protected void updateMotionY(float deltaTime){
		
		velocity.y += acceleration.y * deltaTime;
		velocity.y = MathUtils.clamp(velocity.y, - terminalVelocity.y,  terminalVelocity.y);
		
	}
	public void render(SpriteBatch batch){
		batch.draw(image.getTexture(), position.x, position.y, 0, 0, dimension.x, dimension.y, 1, 1, rotation, image.getRegionX(), image.getRegionY(), image.getRegionWidth(), image.getRegionHeight(),
				false, false);
	}
}
