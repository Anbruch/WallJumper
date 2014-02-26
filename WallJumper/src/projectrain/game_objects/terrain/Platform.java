package projectrain.game_objects.terrain;

import projectrain.game_objects.AbstractGameObject;
import projectrain.tools.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Platform extends AbstractGameObject{
	private TextureRegion endImage, midImage, bodyImage;
	private int lengthX, lengthY;
	private Vector2 endDimension, midDimension;
	
	public Platform(){
	}
	
	public Platform(float x, float y, int width, int height){
		image = Assets.instance.platform.platMap.getValueAt((int)(Math.random() * 4));

		init(null, x, y, width, height);
	}
	
	public Platform(String platType, float x, float y, int width, int height){
		String endFileName = platType.concat("_end");
		String midFileName = platType.concat("_mid");
		String bodyFileName = platType.concat("_body");
		
		endImage = Assets.instance.platform.platMap.get(endFileName);
		midImage = Assets.instance.platform.platMap.get(midFileName);
		bodyImage = Assets.instance.platform.platMap.get(bodyFileName);

		init(platType, x, y, width, height);
		
	}
	
	private void init(String platType, float x, float y, int width, int height){
		String endFileName;
		endDimension = new Vector2(endImage.getRegionWidth(), endImage.getRegionHeight());
		midDimension = new Vector2(midImage.getRegionWidth(), midImage.getRegionHeight());
		
		lengthX = (int) (Math.ceil((width - 2 * midDimension.x) / midDimension.x));
		
		lengthX = (lengthX > 0) ? lengthX : 0;
		lengthY = (int) (height / midDimension.y);
		
		
		//Pick between end images of the platform
		if(lengthY > 1){
			endFileName = platType.concat("_end2");
			//endFileName = platType.concat("_end");

		}else{
			endFileName = platType.concat("_end");

		}
		
		String midFileName = platType.concat("_mid");
		String bodyFileName = platType.concat("_body");
		
		endImage = Assets.instance.platform.platMap.get(endFileName);
		midImage = Assets.instance.platform.platMap.get(midFileName);
		bodyImage = Assets.instance.platform.platMap.get(bodyFileName);
		
		
		//set basic vectors of position, dimension and bounds for collision
		position.set(x, y - height);
		dimension.set(width, height);
		bounds.set(position.x, position.y, endDimension.x * 2 + midDimension.x * lengthX,
				midDimension.y * lengthY - 5);
		
	}
	
	/*public void setLength(int lengthX, int lengthY){
		bounds.setSize(lengthX * dimension.x, lengthY * dimension.y - 3);
	}*/
	
	@Override
	public void render(SpriteBatch batch) {
		float relX = 0;
		batch.draw(endImage.getTexture(), position.x, position.y + lengthY * midDimension.y - midDimension.y, endDimension.x, 
				endDimension.y, endImage.getRegionX(), endImage.getRegionY(),
				endImage.getRegionWidth(), endImage.getRegionHeight(), false, false);
		relX += endDimension.x;
		for(int i = 0; i < lengthX; i++){
			batch.draw(midImage.getTexture(), position.x + i * midDimension.x + endDimension.x,
					position.y + lengthY * midDimension.y - midDimension.y, midDimension.x, 
					midDimension.y,  midImage.getRegionX(), midImage.getRegionY(),
					midImage.getRegionWidth(), midImage.getRegionHeight(), false, false);
			relX += midDimension.x;
		}
		
		batch.draw(endImage.getTexture(), position.x + relX, position.y + lengthY * midDimension.y - midDimension.y, endDimension.x, 
				endDimension.y, endImage.getRegionX(), endImage.getRegionY(),
				endImage.getRegionWidth(), endImage.getRegionHeight(), true, false);
		
		
		for(int j = 0; j < lengthY - 1; j++){
			for(int i = 0; i < lengthX + 2; i ++){
			
				batch.draw(bodyImage.getTexture(), position.x + i * midDimension.x, position.y + midDimension.y * j, endDimension.x, 
						endDimension.y, bodyImage.getRegionX(), bodyImage.getRegionY(),
						bodyImage.getRegionWidth(), bodyImage.getRegionHeight(), false, false);
			}
		}
		
		
	}

}
