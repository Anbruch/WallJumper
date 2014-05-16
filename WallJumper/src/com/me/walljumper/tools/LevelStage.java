package com.me.walljumper.tools;

import com.me.walljumper.WallJumper;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.game_objects.terrain.Platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.screens.World;

public class LevelStage {
	
	private Platform platform;
	public static Array<ManipulatableObject> playerControlledObjects = new Array<ManipulatableObject>();
	public static Array<AbstractGameObject> platforms = new Array<AbstractGameObject>();
	public static Array<AbstractGameObject> backPlatforms = new Array<AbstractGameObject>();
	public static Array<ManipulatableObject> enemyControlledObjects = new Array<ManipulatableObject>();

	public static Array<AbstractGameObject> uncollidableObjects = new Array<AbstractGameObject>();
	public static ManipulatableObject player;
	//For objects like the portal
	public static Array<AbstractGameObject> interactables = new Array<AbstractGameObject>();
	
	private LevelLoader levelLoader;
	
	
	public LevelStage(){
		levelLoader = new LevelLoader("levels/" + ("World" + WallJumper.WorldNum) + "/l" + WallJumper.level + ".png");
	}
	public static void setPlayer(ManipulatableObject player){
		LevelStage.player = player;
	}
	//This gets nearest platform in the y-axis
	public static AbstractGameObject getNearestPlatformY(Vector2 position){
		AbstractGameObject currentClosest = null;
		float curLength = 100;
		
		//Goes through back platforms
		for(AbstractGameObject platform: LevelStage.backPlatforms){
			float yDifference = Math.abs(platform.position.y + platform.bounds.height - position.y);
			if(yDifference < curLength){
				curLength = yDifference;
				currentClosest = platform;
			}
		}
		
		//Goes through front platforms
		for(AbstractGameObject platform: LevelStage.platforms){
			float yDifference = Math.abs(platform.position.y + platform.bounds.height - position.y);
			if(yDifference < curLength){
				curLength = yDifference;
				currentClosest = platform;
			}
		}
		return currentClosest;
	}
	public void render(SpriteBatch batch){
		//render portal
		for(AbstractGameObject interactableObject: interactables){
			interactableObject.render(batch);
		}
		//Render all of the manipulatable objects
		for(ManipulatableObject object: LevelStage.playerControlledObjects){
			object.render(batch);
		}
		
		//Render all of the enemy controlled objects
		for(ManipulatableObject object: LevelStage.enemyControlledObjects){
			object.render(batch);
		}
		for(int i = LevelStage.backPlatforms.size - 1; i >= 0; i--){
			backPlatforms.get(i).render(batch);
		}
		//render all of the terrain
		for(AbstractGameObject platform: LevelStage.platforms){
			platform.render(batch);
		}
		
		for(AbstractGameObject uncollidable: LevelStage.uncollidableObjects){
			uncollidable.render(batch);
		}
	}
	public void destroy(){
		//Clear all the arrays
		playerControlledObjects.clear();
		enemyControlledObjects.clear();
		interactables.clear();
		platforms.clear();
		backPlatforms.clear();
		uncollidableObjects.clear();
		InputManager.inputManager.controllableObjects.clear();
		player = null;
		//set current level to null
		levelLoader.destroy();
	}
	public void update(float deltaTime){
		
		//Iterate and update all enemies, players, controllable objects
		for(ManipulatableObject object: LevelStage.playerControlledObjects){
			object.update(deltaTime);
			object.fallingToDie(deltaTime);
		}
		//Render all of the enemy controlled objects
		for(ManipulatableObject object: LevelStage.enemyControlledObjects){
			object.update(deltaTime);
		}
		for(int i = LevelStage.backPlatforms.size - 1; i >= 0; i--){
			backPlatforms.get(i).update(deltaTime);
		}
		//render all of the terrain
		for(AbstractGameObject platform: LevelStage.platforms){
			platform.update(deltaTime);
		}
		for(AbstractGameObject interactableObject: interactables){
			interactableObject.update(deltaTime);
		}
		for(AbstractGameObject uncollidable: LevelStage.uncollidableObjects){
			uncollidable.update(deltaTime);
		}
		
		
	}
	
}
