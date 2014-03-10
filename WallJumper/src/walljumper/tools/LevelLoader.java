package walljumper.tools;

import walljumper.game_objects.AbstractGameObject;
import walljumper.game_objects.classes.Rogue;
import walljumper.game_objects.terrain.Platform;
import walljumper.game_objects.terrain.Portal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

public class LevelLoader {

	private int color, behindTarget;
	private Pixmap pixmap;

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), PLAYER_SPAWNPOINT(255, 255, 255), ENEMY_SPAWNPOINT(255,
				0, 0), GOAL(255, 255, 0), PLATFORM(0, 255, 0), PLATFORM_START(0, 100,0);
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

		behindTarget = 0;
		// Load image file that represents level data
		pixmap = new Pixmap(Gdx.files.internal(fileName));
		// scan pixels from top-left to bottom right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {

			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {

				AbstractGameObject obj = null;
				float offsetHeight = 0;
				// height grows bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				float heightIncreaseFactor = .25f;

				// Get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
/*
				if (BLOCK_TYPE.GRASS_PLAT_BLOCK_LONG.sameColor(currentPixel)) {
					if (isStartOfNewObject(pixelX, pixelY, currentPixel)) {
						Vector2 newPixelXY = extendPlatform(pixelX, pixelY, currentPixel);
						int lengthX = (int) (newPixelXY.x - pixelX);
						int lengthY = (int)(newPixelXY.y - pixelY);
						System.out.println(lengthX + " , " + lengthY);
						
						LevelStage.platforms.add(new Platform(
								"grass_plat_block_long", pixelX * 1,
								baseHeight, lengthX, lengthY));
						pixelX += lengthX;
						pixelY += lengthY;
						
					}else
						continue;

					// IF GRASS_PLAT_LONG
				
				} else */
				if (BLOCK_TYPE.PLATFORM.sameColor(currentPixel) || BLOCK_TYPE.PLATFORM_START.sameColor(currentPixel)) {
					if (isStartOfNewObject(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color) || BLOCK_TYPE.PLATFORM_START.sameColor(currentPixel)) {
						boolean putBehind = nextIsSameColor(pixelX + 1, pixelY, BLOCK_TYPE.PLATFORM.color);
						
						Vector2 newPixelXY = extendPlatform(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color);
						int lengthX = (int) (newPixelXY.x - pixelX) + 1;
						int lengthY = (int)(newPixelXY.y - pixelY) + 1;
						
						
						LevelStage.platforms. add(new Platform(
								"grass", pixelX * 1, baseHeight * 1,
							 lengthX, lengthY));
						if(!putBehind){
							LevelStage.platforms.swap(behindTarget, LevelStage.platforms.size - 1);
							behindTarget++;
						}
						pixmap.drawPixel(pixelX, pixelY, BLOCK_TYPE.PLATFORM.color);

					}else
						continue;
				/*
					// IF GRASS_PLAT_SHORT
				} else if (BLOCK_TYPE.GRASS_PLAT_SHORT.sameColor(currentPixel)) {
					if (isStartOfNewObject(pixelX, pixelY, currentPixel)) {
						Vector2 newPixelXY = extendPlatform(pixelX, pixelY, currentPixel);
						int lengthX = (int) (newPixelXY.x - pixelX);
						int lengthY = (int)(newPixelXY.y - pixelY);
						System.out.println(lengthX + " , " + lengthY);
						
						LevelStage.platforms.add(new Platform(
								"grass_plat_short", pixelX * 1, baseHeight * 1,
								90, 24, lengthX, lengthY));
						pixelX += lengthX;
					}else
						continue;
					// IF GRASS_PLAT_TINY
				} else if (BLOCK_TYPE.GRASS_PLAT_TINY.sameColor(currentPixel)) {
					if (isStartOfNewObject(pixelX, pixelY, currentPixel)) {
						Vector2 newPixelXY = extendPlatform(pixelX, pixelY, currentPixel);
						int lengthX = (int) (newPixelXY.x - pixelX);
						int lengthY = (int)(newPixelXY.y - pixelY);
						System.out.println(lengthX + " , " + lengthY);
						LevelStage.platforms.add(new Platform(
								"grass_plat_tiny", pixelX * 1, baseHeight * 1,
								54, 24, lengthX, lengthY));
						pixelX += lengthX;
					}else
						continue;*/
					// IF PLAYER SPAWNPOINT
				} else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
					if (isStartOfNewObject(pixelX, pixelY, currentPixel)) {
/*
						// Spawn player
						ScytheMan scytheMan = new ScytheMan(pixelX * 1,
								baseHeight * 1, 63, 48);*/
						Rogue rogue = new Rogue(pixelX * 1,
								baseHeight * 1, .15f, .31f, 5);
						
						// Track him in these arrays
						LevelStage.playerControlledObjects.add(rogue);
						LevelStage.player = rogue;
						InputManager.inputManager.addObject(rogue);
					}
				} else if(BLOCK_TYPE.GOAL.sameColor(currentPixel)){
					LevelStage.interactables.add(new Portal(pixelX, baseHeight));
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

	private Vector2 extendPlatform(int i, int j, int color) {
		while(nextIsSameColor(i, j + 1, color)){
			j++;
			
		}
		while(nextIsSameColor(i + 1, j, color)){
			i++;
		}
		
		System.out.println(j);
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

	public void destroy() {
		
	}
}