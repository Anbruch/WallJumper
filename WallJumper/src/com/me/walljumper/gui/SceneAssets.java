package com.me.walljumper.gui;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.me.walljumper.WallJumper;

public class SceneAssets implements Disposable, AssetErrorListener {
	private AssetManager assetManager;
	public UI ui;
	private boolean disposed;
	public static SceneAssets instance;
	public SceneAssets(AssetManager assetManager, Array<String> paths) {
		this.assetManager = assetManager;
		init(paths);
	}
	private void init(Array<String> paths){
		disposed = false;
		//Load all the pack files
		int i;
		for(i = 0; i < paths.size; i++){
			assetManager.load(paths.get(i), TextureAtlas.class);
		}
		assetManager.finishLoading();
		
		//Get the texture atlases for each pack
		Array<TextureAtlas> atlasMap = new Array<TextureAtlas>();
		for(i = 0; i < paths.size; i++){
			atlasMap.add((TextureAtlas) (assetManager.get(paths.get(i))));
		}
		
		//Send the packs to the UI to cut out each string
		ui = new UI(atlasMap.get(0));
	}
	public class UI {
		public final ArrayMap<String, AtlasRegion> uiMap;

		public UI(TextureAtlas atlas){
			
			//Which images are loaded from this
			Array<String> worldScreenAssetFiles = new Array<String>();
			for(int i = 0; i < WallJumper.numWorlds; i++){
				worldScreenAssetFiles.add("bg" + i);
			}
			
			
			//Adds individual images
			//It will look in the folder specified,
			//these exact strings for images.
			//These strings must be the same as name in folder on drive!!!!
			worldScreenAssetFiles.add("button.up");
			worldScreenAssetFiles.add("button.down");
			worldScreenAssetFiles.add("startscreen");
			worldScreenAssetFiles.add("levelbutton.down");
			worldScreenAssetFiles.add("levelbutton.up");
			worldScreenAssetFiles.add("title");
			worldScreenAssetFiles.add("homeButton");
			
			//builds the ArrayMap of ui objects 
			uiMap = new ArrayMap<String, AtlasRegion>();
			try{
				for(String uiObj: worldScreenAssetFiles){
					//note: key = value
					uiMap.put(uiObj, atlas.findRegion(uiObj));
				}
			
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
		}//End METHOD
	}//END CLASS
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		if(!disposed){
			assetManager.dispose();
			ui = null;
			disposed = true;
		}
	}
}
