package com.me.walljumper.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.classes.Rogue;
import com.me.walljumper.game_objects.terrain.Platform;
import com.me.walljumper.game_objects.terrain.Portal;
import com.me.walljumper.game_objects.terrain.traps.SpikeTrap;
import com.me.walljumper.game_objects.terrain.traps.SpikeTrap.SIDE;
import com.me.walljumper.screens.World;

public class LevelLoader {

	private Pixmap pixmap;

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), PLAYER_SPAWNPOINT(255, 255, 255), ENEMY_SPAWNPOINT(255,
				0, 0), GOAL(255, 255, 0), PLATFORM_RIGHT_DOWN(0, 0, 255),
				PLATFORM_START_RIGHT_DOWN(0, 0, 100), SPIKE(125, 0, 0),
				PLATFORM(0, 255, 0), PLATFORM_START_DOWN_RIGHT(0, 100,0), 
				TUTORIAL_PHASE_ONE(32, 74, 56);
		private int color;

		private BLOCK_TYPE(int r, int g, int b) {

			color = r << 24 | g << 16 | b << 8 | 0xff;

		}

		public boolean sameColor(int color) {
			return this.color == color;
		}

		public int getColor() {
			return color;
		}
	}

	public LevelLoader(String fileName) {
		init(fileName);
	}
	public void startLevel(String fileName){
		init(fileName);
	}
	private void init(String fileName) {

		// Load image file that represents level data
		try{
		pixmap = new Pixmap(Gdx.files.internal(fileName));
		}catch(Exception e){
			World.controller.failedLoad = true;
			World.controller.gameScreen.backToLevelMenu();
			return;
		}
		// scan pixels from top-left to bottom right
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {

			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {

				// height grows bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;

				// Get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				if(BLOCK_TYPE.EMPTY.sameColor(currentPixel)){
					continue;
				}
				if (BLOCK_TYPE.PLATFORM.sameColor(currentPixel) || BLOCK_TYPE.PLATFORM_START_DOWN_RIGHT.sameColor(currentPixel)) {
					
					
					//S
					if (isStartOfNewObject(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color, BLOCK_TYPE.PLATFORM_START_RIGHT_DOWN.color) || BLOCK_TYPE.PLATFORM_START_DOWN_RIGHT.sameColor(currentPixel)) {
						boolean putInFront = nextIsSameColor(pixelX + 1, pixelY, BLOCK_TYPE.PLATFORM.color);
						
						Vector2 newPixelXY = extendPlatformDownRight(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color);
						int lengthX = (int) (newPixelXY.x - pixelX) + 1;
						int lengthY = (int)(newPixelXY.y - pixelY) + 1;
						
						
						if(!putInFront){
							LevelStage.backPlatforms.add(new Platform(
									"grass", pixelX * 1, baseHeight * 1,
								 lengthX, lengthY));
						}else{
							LevelStage.platforms.add(new Platform(
									"grass", pixelX * 1, baseHeight * 1,
								 lengthX, lengthY));
						}
						pixmap.drawPixel(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color);

					}


				}else if (BLOCK_TYPE.PLATFORM_START_RIGHT_DOWN.sameColor(currentPixel)) {
					boolean putInFront = nextIsSameColor(pixelX, pixelY + 1, BLOCK_TYPE.PLATFORM.color);
					
					Vector2 newPixelXY = extendPlatformRightDown(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color);
					int lengthX = (int) (newPixelXY.x - pixelX) + 1;
					int lengthY = (int)(newPixelXY.y - pixelY) + 1;
					
					
					
					if(!putInFront){
						LevelStage.backPlatforms.add(new Platform(
								"grass", pixelX * 1, baseHeight * 1,
							 lengthX, lengthY));
						pixmap.drawPixel(pixelX, pixelY + 1, BLOCK_TYPE.PLATFORM.color);
					}else{
						LevelStage.platforms.add(new Platform(
								"grass", pixelX * 1, baseHeight * 1,
							 lengthX, lengthY));
					}
					pixmap.drawPixel(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color);
					// IF PLAYER SPAWNPOINT
				} else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
					if (isStartOfNewObject(pixelX, pixelY, currentPixel)) {
/*
						// Spawn player
						ScytheMan scytheMan = new ScytheMan(pixelX * 1,
								baseHeight * 1, 63, 48);*/
						Rogue rogue = new Rogue(pixelX * 1,
								baseHeight, .19f, .31f, Constants.ROGUE_SCALE);
						
						// Track him in these arrays
						LevelStage.playerControlledObjects.add(rogue);
						LevelStage.setPlayer(rogue);
						InputManager.inputManager.addObject(rogue);
					}
				} else if(BLOCK_TYPE.GOAL.sameColor(currentPixel)){
					LevelStage.interactables.add(new Portal(pixelX, baseHeight, false));
					
				//TRAP
				}else if(BLOCK_TYPE.SPIKE.sameColor(currentPixel) && isStartOfNewObject(pixelX, pixelY, currentPixel)){
					
					Vector2 newPixelXY = extendPlatformDownRight(pixelX, pixelY, currentPixel);
					int lengthX = (int) (newPixelXY.x - pixelX) + 1;
					int lengthY = (int)(newPixelXY.y - pixelY) + 1;
					
					//If Platform is above this spike, flip it pointing downwards
					if(nextIsSameColor(pixelX, pixelY - 1,BLOCK_TYPE.PLATFORM.color)){
						
						LevelStage.interactables.add(new SpikeTrap((float)(pixelX), (float)(baseHeight), lengthX, lengthY, 1f, 1f, SIDE.BOT));
					
					//If platform is below this spike, flip it pointing up
					}else if(nextIsSameColor(pixelX, pixelY + 1, BLOCK_TYPE.PLATFORM.color)){
						LevelStage.interactables.add(new SpikeTrap((float)(pixelX), (float)(baseHeight), lengthX, lengthY, 1f, 1f, SIDE.TOP));
					//LEFT
					}else if(nextIsSameColor(pixelX + 1, pixelY, BLOCK_TYPE.PLATFORM.color)){
						LevelStage.interactables.add(new SpikeTrap((float)(pixelX), (float)(baseHeight), lengthX, lengthY, 1f, 1f, SIDE.LEFT));
					//RIGHT
					}else if(nextIsSameColor(pixelX - 1, pixelY, BLOCK_TYPE.PLATFORM.color)){
						LevelStage.interactables.add(new SpikeTrap((float)(pixelX), (float)(baseHeight), lengthX, lengthY, 1f, 1f, SIDE.RIGHT));

					}else{
						LevelStage.interactables.add(new Portal(pixelX, baseHeight, true));
					}
				}
					/*else if (BLOCK_TYPE.ENEMY_SPAWNPOINT.sameColor(currentPixel)) {
				

					
					if (isStartOfNewObject(pixelX, pixelY, currentPixel)) {
						for(int i = 0; i < 500; i++){
							ScytheMan scytheMan = new ScytheMan(
									pixelX + i, baseHeight, 63, 48);
							scytheMan.setMovementSpeed(new Vector2((float)(Math.random()) * 200, 500));
							MeleeEnemyAI ai = new MeleeEnemyAI(scytheMan, LevelStage.player);
							scytheMan.addAI(ai);
						
						// Track him in these arrays
						LevelStage.enemyControlledObjects.add(scytheMan);
						}
					}
				}// end else if
				*/
			}// inner for loop
		}// outer for loop
	}// end of method

	private Vector2 extendPlatformDownRight(int i, int j, int color) {
		while(nextIsSameColor(i, j + 1, color)){
			j++;
			
		}
		while(nextIsSameColor(i + 1, j, color)){
			i++;
		}
		
		return new Vector2(i, j);
	}
	private Vector2 extendPlatformRightDown(int i, int j, int color){
		while(nextIsSameColor(i + 1, j, color)){
			i++;
		}
		while(nextIsSameColor(i, j + 1, color)){
			j++;
		}
		
		return new Vector2(i, j);
	}
	private boolean nextIsSameColor(int i, int j, int color){
		if(pixmap.getPixel(i, j) == color){
			return true;
		}
		return  false;
	}
	private boolean isStartOfNewObject(int i, int j, int color) {
		int lastPixelX = pixmap.getPixel(i - 1, j);
		int lastPixelY = pixmap.getPixel(i, j - 1);

		if (lastPixelX == color)
			return false;
		if (lastPixelY == color)
			return false;

		return true;
	}
	private boolean isStartOfNewObject(int i, int j, int color, int color2){
		int lastPixelX = pixmap.getPixel(i - 1, j);
		int lastPixelY = pixmap.getPixel(i, j - 1);

		if (lastPixelX == color || lastPixelX == color2)
			return false;
		if (lastPixelY == color || lastPixelY == color2)
			return false;

		return true;
	}

	public void destroy() {
		
	}
}