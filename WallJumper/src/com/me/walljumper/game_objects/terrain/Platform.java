package com.me.walljumper.game_objects.terrain;

import com.me.walljumper.WallJumper;
import com.me.walljumper.game_objects.AbstractGameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class Platform extends AbstractGameObject{
	private static int count = 0;
	private static final int numBodyImages = 6;
	private static final double numEndBodyImages = 5;
	private static final double numEndBottomBodyImages = 5;
	private TextureRegion topCornerImage, endImage, midImage, bodyImage, bottomCornerImage;
	private Array<TextureRegion> bodyImages, endBodyImages, endBottomBodyImages;
	private String bodyFileName, midFileName,topCornerFileName, endBodyFileName, endBottomBodyFileName, bottomCornerFileName;
	private int lengthX, lengthY;
	private Vector2 endDimension, midDimension;
	
	public Platform(){
		
	}
	
	public Platform(float x, float y, int width, int height){
		image = Assets.instance.platform.platMap.getValueAt((int)(Math.random() * 4));
		init(null, x, y, width, height);
	}
	
	public Platform(String platType, float x, float y, int width, int height){
		bodyImages = new Array<TextureRegion>();
		endBodyImages = new Array<TextureRegion>();
		endBottomBodyImages = new Array<TextureRegion>();
		
		topCornerFileName = "topcorner";
		midFileName = "mid";
		bodyFileName = "body";
		endBodyFileName ="endbody";
		bottomCornerFileName = "bottomcorner";
		endBottomBodyFileName = "endbottombody";
		
		
		
		

		init(platType, x, y, width, height);
		
	}
	
	private void init(String platType, float x, float y, int width, int height){
		String endFileName;
		dimension.set(1, 1);
		topCornerImage = Assets.instance.platform.platMap.get(topCornerFileName);
		bottomCornerImage = Assets.instance.platform.platMap.get(bottomCornerFileName);
		midImage = Assets.instance.platform.platMap.get(midFileName);
		
		
		endDimension = new Vector2(topCornerImage.getRegionWidth(), topCornerImage.getRegionHeight());
		midDimension = new Vector2(midImage.getRegionWidth(), midImage.getRegionHeight());
		
		lengthX = width;
		lengthY = height;
		
		
		
		//Pick between end images of the platform
		if(lengthY > 1){
			endFileName = "topcorner";

		}else{
			endFileName = "end";
			endImage = Assets.instance.platform.platMap.get(endFileName);

		}
		
				
		//Body's
		int index = 0;
		String specificBody;
		for(int j = 0; j < lengthY - 2; j++){
			for(int i = 0; i < lengthX; i++){
				specificBody = bodyFileName.concat("" + (int)(Math.random() * numBodyImages + 1));
				bodyImages.add(Assets.instance.platform.platMap.get(specificBody)); 
				index++;
			}
		}
		//EndBody's
		String specificEndBody;
		for(int k = 0; k < (lengthY - 2) * 2; k++){
			specificEndBody = endBodyFileName.concat("" + (int)(Math.random() * numEndBodyImages + 1));
			endBodyImages.add(Assets.instance.platform.platMap.get(specificEndBody));
		}
		
		//EndBottomBody's
		String specificEndBottomBody;
		for(int l = 0; l < lengthX - 2; l++){
			specificEndBottomBody = endBodyFileName.concat("" + (int)(Math.random() * numEndBottomBodyImages + 1));
			endBottomBodyImages.add(Assets.instance.platform.platMap.get(specificEndBottomBody));
		}
		
		//set basic vectors of position, dimension and bounds for collision
		position.set(x, y - dimension.y * lengthY + 1.3f);

		bounds.set(position.x, position.y, dimension.x * lengthX ,
				dimension.y * lengthY - .3f);
		
	}
	
	/*public void setLength(int lengthX, int lengthY){
		bounds.setSize(lengthX * dimension.x, lengthY * dimension.y - 3);
	}*/
	
	
	@Override
	public void render(SpriteBatch batch) {
		if(onScreen || World.controller.renderAll){
			if(lengthY < 2){
				renderSingleRowPlat(batch);
				return;
			}
			
			float relX = 0;
			//TopCorner Left
			batch.draw(topCornerImage.getTexture(), position.x, position.y + lengthY * dimension.y - dimension.y, dimension.x, 
					dimension.y, topCornerImage.getRegionX(), topCornerImage.getRegionY(),
					topCornerImage.getRegionWidth(), topCornerImage.getRegionHeight(), false, false);
			relX += dimension.x;
			//Top Row
			for(int i = 0; i < lengthX - 2; i++){
				batch.draw(midImage.getTexture(), position.x + i * dimension.x + dimension.x,
						position.y + lengthY * dimension.y - dimension.y, dimension.x, 
						dimension.y,  midImage.getRegionX(), midImage.getRegionY(),
						midImage.getRegionWidth(), midImage.getRegionHeight(), false, false);
				relX += dimension.x;
			}
			
			//TopCorner Right
			batch.draw(topCornerImage.getTexture(), position.x + relX, position.y + lengthY * dimension.y - dimension.y, dimension.x, 
					dimension.y, topCornerImage.getRegionX(), topCornerImage.getRegionY(),
					topCornerImage.getRegionWidth(), topCornerImage.getRegionHeight(), true, false);
			
			//Left and right Column
			TextureRegion endBodyImage;
			for(int p = 0; p < lengthY - 2; p++){
				endBodyImage = endBodyImages.get(p);
				batch.draw(endBodyImage.getTexture(), position.x, position.y + p * dimension.y + dimension.y, dimension.x, 
						dimension.y, endBodyImage.getRegionX(), endBodyImage.getRegionY(),
						endBodyImage.getRegionWidth(), endBodyImage.getRegionHeight(), false, false);
				
				endBodyImage = endBodyImages.get(p + endBodyImages.size/2 - 1);
				batch.draw(endBodyImage.getTexture(), position.x + relX, position.y + p * dimension.y + dimension.y, dimension.x, 
						dimension.y, endBodyImage.getRegionX(), endBodyImage.getRegionY(),
						endBodyImage.getRegionWidth(), endBodyImage.getRegionHeight(), true, false);
			}
			
			//Bottom Left Corner
			batch.draw(bottomCornerImage.getTexture(), position.x, position.y, dimension.x, 
					dimension.y, bottomCornerImage.getRegionX(), bottomCornerImage.getRegionY(),
					bottomCornerImage.getRegionWidth(), bottomCornerImage.getRegionHeight(), false, false);
			
			//Bottom Row
			TextureRegion endBottomBodyImage;
			for(int q = 0; q < endBottomBodyImages.size; q++){
				
				endBottomBodyImage = endBottomBodyImages.get(q);
				
				batch.draw(endBottomBodyImage, position.x + q * dimension.x + dimension.x * 2,
						position.y, 0, 0, dimension.y, 
						dimension.x, 1, 1, 90);
			}
			//Bottom Right Corner
					batch.draw(bottomCornerImage.getTexture(), position.x + relX, position.y, dimension.x, 
							dimension.y, bottomCornerImage.getRegionX(), bottomCornerImage.getRegionY(),
							bottomCornerImage.getRegionWidth(), bottomCornerImage.getRegionHeight(), true, false);
			
			
			//Body
			int index = 0;
			for(int j = 0; j < lengthY - 2; j++){
				for(int i = 0; i < lengthX - 2; i ++){
					
					bodyImage = bodyImages.get(index);
					batch.draw(bodyImage.getTexture(), position.x + i * dimension.x + dimension.x, position.y + dimension.y * j + dimension.y, dimension.x, 
							dimension.y, bodyImage.getRegionX(), bodyImage.getRegionY(),
							bodyImage.getRegionWidth(), bodyImage.getRegionHeight(), false, false);
					index++;
				}
			}
		
		}
	}

	private void renderSingleRowPlat(SpriteBatch batch) {
		float relX = 0;
		//TopCorner Left
		batch.draw(endImage.getTexture(), position.x, position.y + lengthY * dimension.y - dimension.y, dimension.x, 
				dimension.y, endImage.getRegionX(), endImage.getRegionY(),
				endImage.getRegionWidth(), endImage.getRegionHeight(), false, false);
		relX += dimension.x;
		//Top Row
		for(int i = 0; i < lengthX - 2; i++){
			batch.draw(midImage.getTexture(), position.x + i * dimension.x + dimension.x,
					position.y + lengthY * dimension.y - dimension.y, dimension.x, 
					dimension.y,  midImage.getRegionX(), midImage.getRegionY(),
					midImage.getRegionWidth(), midImage.getRegionHeight(), false, false);
			relX += dimension.x;
		}
		
		//TopCorner Right
		batch.draw(endImage.getTexture(), position.x + relX, position.y + lengthY * dimension.y - dimension.y, dimension.x, 
				dimension.y, endImage.getRegionX(), endImage.getRegionY(),
				endImage.getRegionWidth(), endImage.getRegionHeight(), true, false);
		
	}

}