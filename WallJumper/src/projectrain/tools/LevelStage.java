package projectrain.tools;

import projectrain.game_objects.AbstractGameObject;
import projectrain.game_objects.classes.ManipulatableObject;
import projectrain.game_objects.terrain.Platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class LevelStage {
	
	private Platform platform;
	public static Array<ManipulatableObject> playerControlledObjects = new Array<ManipulatableObject>();
	public static Array<AbstractGameObject> platforms = new Array<AbstractGameObject>();
	public static Array<ManipulatableObject> enemyControlledObjects = new Array<ManipulatableObject>();
	public static ManipulatableObject player;
	private LevelLoader levelLoader;
	
	
	public LevelStage(){
		levelLoader = new LevelLoader("levels/testLevel.png");
		
	}
	public static void setPlayer(ManipulatableObject player){
		LevelStage.player = player;
	}
	public void render(SpriteBatch batch){
		
		
		
		//Render all of the manipulatable objects
		for(ManipulatableObject object: LevelStage.playerControlledObjects){
			object.render(batch);
		}
		//Render all of the enemy controlled objects
		for(ManipulatableObject object: LevelStage.enemyControlledObjects){
			object.render(batch);
		}
		//render all of the terrain
		for(AbstractGameObject platform: LevelStage.platforms){
			platform.render(batch);
		}
		
		
	}
	public void update(float deltaTime){
		
		//Iterate and update all enemies, players, controllable objects
		for(ManipulatableObject object: LevelStage.playerControlledObjects){
			object.update(deltaTime);
		}
		//Render all of the enemy controlled objects
		for(ManipulatableObject object: LevelStage.enemyControlledObjects){
			object.update(deltaTime);
		}
		
		
	}
	
}
