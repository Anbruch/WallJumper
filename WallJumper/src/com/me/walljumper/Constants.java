package com.me.walljumper;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.tools.Assets;

public class Constants {

	public static final float defaultZoom = .58f;
	public static final float viewportWidth = 25.0f, viewportHeight = 25.0f,
			bgViewportWidth = 1280f, bgViewportHeight = 720f;
	public static final String SKIN_UI = "ui/menuSkin.json";
	public static final String TEXTURE_ATLAS_UI = "ui/MenuSkin.pack";
	public static final int ROGUE_SCALE = 5;
	public static final float levelOffsetX = 85, levelOffsetY = -90;
	public static final float bhForce = 40;
	public static final int buttonSpacingX = 25;
	public static final float buttonSpacingY = 20;
	public static final int worldsOffsetX = 30;
	public static final float camConstantX = -bgViewportWidth / 2;
	public static final float sceneCamX = bgViewportWidth / 2;
	public static final float sceneCamY = bgViewportHeight / 2;
	public static final float cameraPanVal = 200;
	public static final float gravity = -33;
	public static final int maxRiftFragments = 18;
	public static int lastSeenLevelInMenu = -1;

	public static Array<String> givenStory;
	public static Array<String> extraStory;

	// Unprojects coordinates from screen and converts to virtual coordinates
	// that are based off the camera's units and resize
	public static Vector2 screenToCoords(OrthographicCamera camera, Vector3 pos) {
		camera.unproject(pos);
		return new Vector2(pos.x, pos.y);
	}

	public static void initializeStoryArrays() {
		// Hold the story between each level
		givenStory = new Array<String>();
		extraStory = new Array<String>();

		// Level 1
		givenStory
				.add("Aeternus was one of the founding guardians of the Multiverse.");
		extraStory
				.add("He served for many a universal generation which made him hundreds of trillion years old");

		// Level2
		givenStory
				.add("His home was a true alien land. The multiverse we know was a very small aspect of his realm. A project.");
		extraStory
				.add("The founder of the multiverse was a 9th dimensional being that transformed his brain into a host and mathematic engine for the multiverse to exist. The reality that we know...");
	}

}
