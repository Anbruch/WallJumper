package com.me.walljumper.screens;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.me.walljumper.Constants;
import com.me.walljumper.WallJumper;

public class LevelMenu extends ScreenHelper {
	private Stage stage;
	private Skin skin;
	private Image imgBackground;
	private TweenManager twnManager;
	private Table container;

	
	@Override
	public void render(float delta) {
		//Have to do this
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		//twnManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(Constants.bgViewportWidth, Constants.bgViewportHeight, false);

	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		WallJumper.currentScreen = this;
		rebuildStage();
	}

	private void rebuildStage() {
		
		skin = new Skin(Gdx.files.internal(Constants.SKIN_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		
		//Each table of actors
		container = new Table();
		container.setFillParent(true);
		
		buildBackgroundLayer();
		buildObjectsLayer();
		Label label = new Label("World " + WallJumper.World, skin);
		label.setPosition(Constants.bgViewportWidth / 2 - label.getWidth() / 2, Constants.bgViewportHeight - label.getHeight() - 20);
		  
		ScrollPane scroll = new ScrollPane(container);
		scroll.setFillParent(true);
		scroll.setFlickScroll(true);
		
		scroll.setFlingTime(0.1f);
		 
		 
		 
         
        final Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);
		stage.addActor(label);
		
        table.add(scroll).fill().expand();
        

		
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
		
	private void buildBackgroundLayer() {
		imgBackground = new Image(skin, "background");
		container.addActor(imgBackground);
		
	}

	private void buildObjectsLayer() {
		int maxRow = WallJumper.getNumSetsOfLevels(), rowsMaxCol;
		
		for(int i = 0; i < maxRow; i++){
			rowsMaxCol = WallJumper.getNumLevelsForSet(i);
			for(int j = 0; j < rowsMaxCol; j++){
				
		       final TextButton levelButton = new TextButton("" + j, skin);
		        levelButton.setPosition(Constants.levelOffsetX + j *
		        		(Constants.buttonSpacingX + 25 + levelButton.getWidth()), 
		        		Constants.bgViewportHeight - levelButton.getHeight() - 70 - Constants.levelOffsetY
		        		- i * (Constants.buttonSpacingY + levelButton.getHeight()));
		        levelButton.setName("s" + i + "l" + j);
		        System.out.println(levelButton);
		        levelButton.addListener(new ChangeListener() {
					
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						
						int s = actor.getName().lastIndexOf('s') + 1;
						WallJumper.set = actor.getName().charAt(s) - 48;
						
						int l = actor.getName().lastIndexOf('l') + 1;
						WallJumper.level = actor.getName().charAt(l) - 48;

						changeScreen(World.controller);
						
					}
					
						
				});
		        container.addActor(levelButton);
			}
		}
		
	
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
	
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button){
		return false;
	}

	public boolean handleKeyInput(int keycode) {
		return false;
	}


}
