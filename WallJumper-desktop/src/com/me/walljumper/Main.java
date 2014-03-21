package com.me.walljumper;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "WallJumper";
		cfg.width = 1024;
		cfg.height = 720;
		cfg.fullscreen = false;
		new LwjglApplication(new WallJumper(), cfg);
	}
}
