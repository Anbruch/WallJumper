  package com.me.walljumper;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	
	private static boolean rebuildAtlas = false;
	public static void main(String[] args) {
		if(rebuildAtlas){
			Settings settings = new Settings();
			TexturePacker2.process(settings, "assets-raw/images", "../WallJumper-desktop/assets/images", "WallJumper.pack");
		}
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "WallJumper";
		cfg.width = 1280;
		cfg.height = 720;
		cfg.fullscreen = false;
		
		
		new LwjglApplication(new WallJumper(), cfg);
	}
}


 
