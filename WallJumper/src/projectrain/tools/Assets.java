package projectrain.tools;

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
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		
	}

	@Override
	public void dispose() {
		
	}
	public class Platform{
		public final ArrayMap<String, AtlasRegion> platMap; 
		
		public Platform(TextureAtlas atlas){
			platMap = new ArrayMap<String, AtlasRegion>();
			platMap.put("grass_end", atlas.findRegion("grass_end"));
			platMap.put("grass_mid", atlas.findRegion("grass_mid"));
			platMap.put("grass_body", atlas.findRegion("grass_body"));
			platMap.put("grass_end2", atlas.findRegion("grass_end2"));

			platMap.put("grass_plat_long", atlas.findRegion("grass_plat_long"));
			platMap.put("grass_plat_block_long", atlas.findRegion("grass_plat_block_long"));
			platMap.put("grass_plat_tiny", atlas.findRegion("grass_plat_tiny"));
			platMap.put("grass_plat_short", atlas.findRegion("grass_plat_short"));

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
	
	public class Background{
		public final AtlasRegion nightSky;
		public Background(TextureAtlas atlas){
			nightSky = atlas.findRegion("NightSky");
		}
	}

}