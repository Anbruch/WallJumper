package walljumper.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener{
	public static Assets instance = new Assets();
	private AssetManager assetManager;
	public ScytheMan scytheMan;
	public Platform platform;
	public Background nightSky;
	public Rogue rogue;
	
	
	private Assets(){
		
	}
	public void init(){
		
	}
	public void init(AssetManager assetManager){
		//Load Spritesheet to be cut from texture packer2
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load("images/WallJumper.pack", TextureAtlas.class);
		assetManager.finishLoading();
		
		TextureAtlas atlas = assetManager.get("images/WallJumper.pack");
		platform = new Platform(atlas);
		scytheMan = new ScytheMan(atlas);
		nightSky = new Background(atlas);
		rogue = new Rogue(atlas);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		
	}

	@Override
	public void dispose() {
		
	}
	public class Platform{
		public final ArrayMap<String, AtlasRegion> platMap; 
		//			
		public Platform(TextureAtlas atlas){
			platMap = new ArrayMap<String, AtlasRegion>();
			platMap.put("grass_end", atlas.findRegion("grass_end"));
			platMap.put("grass_mid", atlas.findRegion("grass_mid"));
			for(int i = 1; i < 7; i++){
				platMap.put("grass_body" + "" + i, atlas.findRegion("grass_body" + "" + i));
			}
			
			for(int i = 1; i < 6; i++){
				platMap.put("grass_endbody" + "" + i, atlas.findRegion("grass_endbody" + "" + i));
				
				platMap.put("grass_endbottombody" + "" + i, atlas.findRegion("grass_endbottombody" + "" + i));

				
			}
		
			platMap.put("grass_topcorner", atlas.findRegion("grass_topcorner"));
			platMap.put("grass_bottomcorner", atlas.findRegion("grass_bottomcorner"));
			
		}
	}
	
	public class ScytheMan{
		public final Array<AtlasRegion> scytheNormal;
		public final Array<AtlasRegion> scytheRunning;
		public final Array<AtlasRegion >scytheJumping;
		public final Array<AtlasRegion >scytheWalling;
		public final Array<AtlasRegion> scytheAttackZ;

		public final Animation aniRunning;
		public final Animation aniNormal;
		public final Animation aniJumping;
		public final Animation aniWalling;
		
		public final Animation zAttack;
		
		public ScytheMan(TextureAtlas atlas){
			
			scytheAttackZ = atlas.findRegions("scythe_zattack");
			
			scytheRunning = atlas.findRegions("Scythe_running");
			scytheJumping = atlas.findRegions("Scythe_jumping");
			
			scytheWalling = atlas.findRegions("scythe_walling");
			scytheWalling.add(atlas.findRegion("scythe_walling"));
			
			scytheNormal = atlas.findRegions("Scythe_normal");
			
			zAttack = new Animation(1 / 10.0f, scytheAttackZ, Animation.NORMAL);
			aniRunning = new Animation(1 / 10.0f, scytheRunning, Animation.LOOP_PINGPONG);
			aniNormal = new Animation(1 / 10.0f, scytheNormal, Animation.NORMAL);
			aniJumping = new Animation(1 / 10.0f, scytheJumping, Animation.NORMAL);
			aniWalling = new Animation(1/10.0f, scytheWalling, Animation.NORMAL);
		}
	}
	public class Rogue{
		public final Array<AtlasRegion> rogueNormal;
		public final Array<AtlasRegion> rogueRunning;
		public final Array<AtlasRegion >rogueJumping;
		public final Array<AtlasRegion >rogueWalling;

		public final Animation aniRunning;
		public final Animation aniNormal;
		public final Animation aniJumping;
		public final Animation aniWalling;
		
		
		public Rogue(TextureAtlas atlas){
			
			
			rogueRunning = atlas.findRegions("rogue_running");
			rogueJumping = atlas.findRegions("rogue_jumping");
			
			rogueWalling = atlas.findRegions("rogue_walling");
			
			rogueNormal = atlas.findRegions("rogue_normal");
			
			aniRunning = new Animation(1 / 10.0f, rogueRunning, Animation.LOOP_PINGPONG);
			aniNormal = new Animation(1 / 10.0f, rogueNormal, Animation.NORMAL);
			aniJumping = new Animation(1 / 10.0f, rogueJumping, Animation.LOOP);
			aniWalling = new Animation(1/10.0f, rogueWalling, Animation.NORMAL);
		}
	}
	public class Background{
		public final AtlasRegion nightSky;
		public Background(TextureAtlas atlas){
			nightSky = atlas.findRegion("NightSky");
		}
	}

}