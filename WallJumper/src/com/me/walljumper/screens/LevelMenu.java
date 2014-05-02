package com.me.walljumper.screens;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.Constants;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.WallJumper;
import com.me.walljumper.gui.Button;
import com.me.walljumper.gui.Image;
import com.me.walljumper.gui.Scene;
import com.me.walljumper.gui.SceneAssets;
import com.me.walljumper.screens.screentransitions.ScreenTransition;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;

public class LevelMenu extends ScreenHelper {
	
	private Image bg;
	private Scene scene;
	private TweenManager twnManager;

	
	public LevelMenu(DirectedGame game) {
		super(game);
	}





	@Override
	public void render(float delta) {
		//Have to do this
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		scene.update(delta);
		scene.render();
		
		//twnManager.update(delta);
	}
	

	


	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}

	@Override
	public void show() {
		//LOAD ASSETS FOR UI
		Array<String> paths = new Array<String>();
		paths.add("ui/MenuSkin.pack");
		SceneAssets.instance.dispose();
		SceneAssets.instance = new SceneAssets(new AssetManager(), paths);
		
		scene = new Scene(this, game);
		WallJumper.currentScreen = this;
		rebuildStage();
	}

	private void rebuildStage() {
		bg = new Image(false, "bg" + WallJumper.World, 0, 0, Constants.bgViewportWidth, Constants.bgViewportHeight);
		bg.setToWrite("World " + WallJumper.World, Constants.bgViewportWidth / 2 - 10, Constants.bgViewportHeight - 50);
		scene.add(bg);
		
		//Make each level button
		for(int i = 0; i < WallJumper.numButtonsPerPage; i++){
			float buttonWidth = 94 * 1.7f, buttonHeight = 104 * 1.7f;
			Button button = new Button(true, "levelbutton.down", "levelbutton.up", 
					Constants.levelOffsetX + (i % 6) * (buttonWidth+ Constants.buttonSpacingX), 
					Constants.bgViewportHeight + Constants.levelOffsetY - (i / 6 + 1) * (buttonHeight + Constants.buttonSpacingY),
					buttonWidth, buttonHeight){
				@Override
				public boolean clickRelease() {
					
					//Set level to the button's number
					WallJumper.level = this.getNum();
					SceneAssets.instance.dispose();
					ScreenTransitionFade transition = ScreenTransitionFade.init(.75f);
					game.setScreen(new GameScreen(game), transition);
					return false;
				}
			};
			button.setNum(i);
			float textOffsetX = button.getNum()	< 10 ? 8 : 15;
			button.setToWrite("" + button.getNum(), button.dimension.x / 2 - textOffsetX, button.dimension.y / 2 + 10);
			scene.add(button);
		}
		
		
		
		
		
        

		
	}
	public void getLevelButton(int level) {
		  /*  Button button = new Button(skin);
		     
		    // Create the label to show the level number
		    Label label = new Label(Integer.toString(level), skin);
		    label.setFontScale(2f);
		    label.setAlignment(Align.center);      
		     
		    // Stack the image and the label at the top of our button
		    button.stack(new Image(skin.getDrawable("top")), label).expand().fill();
		 
		    // Randomize the number of stars earned for demonstration purposes
		    //int stars = MathUtils.random(-1, +3);
		    Table starTable = new Table();
		    starTable.defaults().pad(5);
		    if (stars >= 0) {
		        for (int star = 0; star < 3; star++) {
		            if (stars > star) {
		                starTable.add(new Image(skin.getDrawable("star-filled"))).width(20).height(20);
		            } else {
		                starTable.add(new Image(skin.getDrawable("star-unfilled"))).width(20).height(20);
		            }
		        }          
		    }
		     
		    button.row();
		    button.add(starTable).height(30);
		     
		    button.setName("Level" + Integer.toString(level));
		    button.addListener(levelClickListener);    
		    return button;
*/
	}
		
	

	@Override
	public void hide() {
		scene.destroy();
		twnManager = null;
		scene = null;
	}

	@Override
	public void pause() {
		
	}
	@Override
	public InputProcessor getInputProcessor(){
		return scene;
	}
	@Override
	public void resume() {
		
	}


	
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button){

		return false;
	}

	public boolean handleKeyInput(int keycode) {
		return false;
	}


}
