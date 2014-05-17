package com.me.walljumper.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.me.walljumper.WallJumper;

public class Assets implements Disposable, AssetErrorListener {
	public static Assets instance = new Assets();
	private AssetManager assetManager;
	public ScytheMan scytheMan;
	public Platform platform;
	public Background nightSky;
	public Rogue rogue;
	public Portal portal;
	public WallJumpParticle wallJumpParticle;
	public Pause pause;
	public Title title;
	public Trap trap;
	public Rain rain;
	public AssetMusic music;

	private Assets() {

	}

	public void init() {

	}

	public void init(AssetManager assetManager) {
		// Load Spritesheet to be cut from texture packer2
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load("images/WallJumper.pack", TextureAtlas.class);
		assetManager.load("music/World0.mp3", Music.class);
		assetManager.finishLoading();

		TextureAtlas atlas = assetManager.get("images/WallJumper.pack");
		platform = new Platform(atlas);
		scytheMan = new ScytheMan(atlas);
		nightSky = new Background(atlas);
		rogue = new Rogue(atlas);
		portal = new Portal(atlas);
		pause = new Pause(atlas);
		title = new Title(atlas);
		trap = new Trap(atlas);
		wallJumpParticle = new WallJumpParticle(atlas);
		rain = new Rain(atlas);
		music = new AssetMusic(assetManager);
	}

	public void init(AssetManager assetManager, Array<String> path, boolean ui) {
		// Load Spritesheet to be cut from texture packer2
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		
		
		
		// Load general textures for all worlds
		assetManager.load("images/WallJumper.pack", TextureAtlas.class);
		// Load world specific textures
		for (String paths : path) {
			assetManager.load(paths, TextureAtlas.class);
		}
		assetManager.load("music/World0.mp3", Music.class);
		assetManager.finishLoading();

		// Create texture atlases
		Array<TextureAtlas> atlasMap = new Array<TextureAtlas>();
		atlasMap.add((TextureAtlas) (assetManager.get("images/WallJumper.pack")));
		for (int i = 0; i < path.size; i++) {
			atlasMap.add((TextureAtlas) (assetManager.get(path.get(i))));
		}

		platform = new Platform(atlasMap.get(1));
		nightSky = new Background(atlasMap.get(1));
		rogue = new Rogue(atlasMap.get(0));
		portal = new Portal(atlasMap.get(0));
		pause = new Pause(atlasMap.get(0));
		title = new Title(atlasMap.get(0));
		trap = new Trap(atlasMap.get(0));
		wallJumpParticle = new WallJumpParticle(atlasMap.get(0));
		rain = new Rain(atlasMap.get(0));
		music = new AssetMusic(assetManager);
		
	}
	public void loadMusic(String file){
		assetManager.load(file, Music.class);
		assetManager.finishLoading();
		music = new AssetMusic(assetManager);
	}

	

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {

	}

	@Override
	public void dispose() {
		assetManager.dispose();
		if(instance.scytheMan != null)
			instance.scytheMan = null;
		if(instance.music != null)
			instance.music = null;
		if(instance.nightSky != null)
			instance.nightSky = null;
		if(instance.pause != null)
			instance.pause = null;
		if(instance.platform != null)
			instance.platform = null;
		if(instance.portal != null)
			instance.portal = null;
		if(instance.rain != null)
			instance.rain = null;
		if(instance.rogue != null)
			instance.rogue = null;
		if(instance.title != null)
			instance.title = null;
		if(instance.trap != null)
			instance.trap = null;
		
	}

	public class Trap {
		public final AtlasRegion spike;

		public Trap(TextureAtlas atlas) {
			spike = atlas.findRegion("spikeTrap");
		}
	}


	public class Platform {
		public final ArrayMap<String, AtlasRegion> platMap;

		//
		public Platform(TextureAtlas atlas) {
			platMap = new ArrayMap<String, AtlasRegion>();
			platMap.put("end", atlas.findRegion("end"));
			platMap.put("mid", atlas.findRegion("mid"));
			for (int i = 1; i < 7; i++) {
				platMap.put("body" + "" + i, atlas.findRegion("body" + "" + i));
			}

			for (int i = 1; i < 6; i++) {
				platMap.put("endbody" + "" + i,
						atlas.findRegion("endbody" + "" + i));

				platMap.put("endbottombody" + "" + i,
						atlas.findRegion("endbottombody" + "" + i));

			}
			platMap.put("topcorner", atlas.findRegion("topcorner"));
			platMap.put("bottomcorner", atlas.findRegion("bottomcorner"));

		}
	}

	public class Portal {
		public final Array<AtlasRegion> portal;
		public final Animation aniPortal;
		public final Array<AtlasRegion> deathPortal;
		public final Animation aniDeathPortal;

		public Portal(TextureAtlas atlas) {
			portal = atlas.findRegions("portal");
			aniPortal = new Animation(1 / 10f, portal, Animation.LOOP);

			deathPortal = atlas.findRegions("deathPortal");
			aniDeathPortal = new Animation(1 / 10f, deathPortal, Animation.LOOP);
		}
	}

	public class Pause {
		public final AtlasRegion pause;
		public final AtlasRegion goalBackground;
		public final AtlasRegion play;
		public final AtlasRegion pauseLayer;
		public final AtlasRegion buttonUp, buttonDown;

		public Pause(TextureAtlas atlas) {
			goalBackground = atlas.findRegion("button.down");
			buttonUp = atlas.findRegion("button.up");
			buttonDown = atlas.findRegion("button.down");
			pause = atlas.findRegion("PauseButton");
			play = atlas.findRegion("PlayButton");
			pauseLayer = atlas.findRegion("pauseLayer");
		}
	}

	public class WallJumpParticle {
		public final Array<AtlasRegion> wallJump;
		public final Animation wallJumpParticle;

		public WallJumpParticle(TextureAtlas atlas) {
			wallJump = atlas.findRegions("wallJump");
			wallJumpParticle = new Animation(1 / 15f, wallJump,
					Animation.NORMAL);
		}
	}

	public class Title {
		public final AtlasRegion title;

		public Title(TextureAtlas atlas) {
			title = atlas.findRegion("Title");
		}
	}

	public class ScytheMan {
		public final Array<AtlasRegion> scytheNormal;
		public final Array<AtlasRegion> scytheRunning;
		public final Array<AtlasRegion> scytheJumping;
		public final Array<AtlasRegion> scytheWalling;
		public final Array<AtlasRegion> scytheAttackZ;

		public final Animation aniRunning;
		public final Animation aniNormal;
		public final Animation aniJumping;
		public final Animation aniWalling;

		public final Animation zAttack;

		public ScytheMan(TextureAtlas atlas) {

			scytheAttackZ = atlas.findRegions("scythe_zattack");

			scytheRunning = atlas.findRegions("Scythe_running");
			scytheJumping = atlas.findRegions("Scythe_jumping");

			scytheWalling = atlas.findRegions("scythe_walling");
			scytheWalling.add(atlas.findRegion("scythe_walling"));

			scytheNormal = atlas.findRegions("Scythe_normal");

			zAttack = new Animation(1 / 10.0f, scytheAttackZ, Animation.NORMAL);
			aniRunning = new Animation(1 / 10.0f, scytheRunning,
					Animation.LOOP_PINGPONG);
			aniNormal = new Animation(1 / 10.0f, scytheNormal, Animation.NORMAL);
			aniJumping = new Animation(1 / 10.0f, scytheJumping,
					Animation.NORMAL);
			aniWalling = new Animation(1 / 10.0f, scytheWalling,
					Animation.NORMAL);
		}
	}

	public class Rogue {
		public final Array<AtlasRegion> rogueNormal;
		public final Array<AtlasRegion> rogueRunning;
		public final Array<AtlasRegion> rogueJumping;
		public final Array<AtlasRegion> rogueWalling;

		public final Animation aniRunning;
		public final Animation aniNormal;
		public final Animation aniJumping;
		public final Animation aniWalling;

		public Rogue(TextureAtlas atlas) {

			rogueRunning = atlas.findRegions("rogue_running");
			rogueJumping = atlas.findRegions("rogue_jumping");

			rogueWalling = atlas.findRegions("rogue_walling");

			rogueNormal = atlas.findRegions("rogue_normal");

			aniRunning = new Animation(1 / 10.0f, rogueRunning, Animation.LOOP);
			aniNormal = new Animation(1 / 10.0f, rogueNormal, Animation.NORMAL);
			aniJumping = new Animation(1 / 10.0f, rogueJumping, Animation.LOOP);
			aniWalling = new Animation(1 / 10.0f, rogueWalling,
					Animation.NORMAL);
		}
	}

	public class Background {
		public final AtlasRegion nightSky;

		public Background(TextureAtlas atlas) {
			nightSky = atlas.findRegion("bg");
		}
	}

	public class AssetMusic {
		public final Music world0;

		public AssetMusic(AssetManager am) {
			world0 = am.get("music/World0.mp3", Music.class);
		}
	}

	public class Rain {

		public final ArrayMap<String, AtlasRegion> images;

		public Rain(TextureAtlas atlas) {
			images = new ArrayMap<String, AtlasRegion>();
			images.put("snow", atlas.findRegion("snow"));
		}
	}

	public TextureRegion getRainImage(String image) {

		return rain.images.get(image);
	}

}