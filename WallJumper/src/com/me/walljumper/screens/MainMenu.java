package com.me.walljumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.me.walljumper.ActorAccessor;
import com.me.walljumper.Constants;
import com.me.walljumper.WallJumper;
import com.me.walljumper.tools.Assets;

public class MainMenu extends ScreenHelper{
	private Stage stage;
	private Skin skin;// done
	//private TweenManager tweenManager;
	private Image imgBackground, startScreen, title;
	
	private TweenManager twnManager;

	
	@Override
	public void render(float delta) {
		
		//Have to do this
		Gdx.gl.glClearColor(255, 255, 255, 0); // Default background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		shouldChangeScreen();
		
		
		twnManager.update(delta);
	}
	//Sets screen to the WorldController
	private void shouldChangeScreen() {
		if(Gdx.input.isTouched()){
			super.changeScreen(new LevelMenu());
		}		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(Constants.bgViewportWidth, Constants.bgViewportHeight, false);
		
 
        
	}
	private void rebuildStage(){
		skin = new Skin(Gdx.files.internal(Constants.SKIN_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

		//Each table of actors
		Table layerBackground = buildBackgroundLayer();
		Table layerObjects = buildObjectsLayer();
		Table layerLogos = buildLogosLayer();
		Table layerControls = buildControlsLayer();
		Table layerOptionsWindow = buildOptionsWindowLayer();
		
		//Assemble stage for menu screen
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		
		stack.setSize(Constants.bgViewportWidth, Constants.bgViewportHeight);
		stack.add(layerBackground);
		stack.add(layerLogos);

		stack.add(layerObjects);
		stack.add(layerControls);
		stack.addActor(layerOptionsWindow);
		buildTween();

		
		
	}
	private void buildTween() {
		twnManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		Timeline.createSequence()
		.beginParallel()
			.push(Tween.from(title, ActorAccessor.SCALE, 2).target(0, 0))
			.push(Tween.to(title, ActorAccessor.XY, 2)
					.target(Constants.bgViewportWidth / 2 - title.getWidth() / 2, Constants.bgViewportHeight / 3))
		.end().start(twnManager);
		

		

	}

	private Table buildControlsLayer() {
		Table layer = new Table();
		
		title = new Image(Assets.instance.title.title);
		title.setPosition(Constants.bgViewportWidth / 2, Constants.bgViewportHeight);
		
		
		layer.addActor(title);
		return layer;
	}

	private Table buildOptionsWindowLayer() {
		Table layer = new Table();
		
		return layer;
	}

	
	

	private Table buildLogosLayer() {
		Table layer = new Table();
		startScreen = new Image(skin, "startscreen");
		layer.add(startScreen);
		return layer;
	}

	

	private Table buildObjectsLayer() {
		Table layer = new Table();
		
 /*
        // button "start game"
        TextButton startGameButton = new TextButton( "Play", skin );
        startGameButton.scaleBy(2);
        startGameButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				((Game) Gdx.app.getApplicationListener()).setScreen(World.controller);

				
			}
			
				
		});

        layer.add(startGameButton);
        
*/        
        // button "options"
       /* TextButton optionsButton = new TextButton( "Options", skin );
       
        layer.add( optionsButton );*/
		return layer;
	}

	private Table buildBackgroundLayer() {
		Table layer = new Table();
		imgBackground = new Image(skin, "background");
		layer.add(imgBackground);
		
		
		return layer;
	}

	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		WallJumper.currentScreen = this;
		rebuildStage();
		
		/*
			stage = new Stage();
			Gdx.input.setInputProcessor(stage);

			atlas = new TextureAtlas("ui/atlas.pack");
			skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);
			
			table = new Table(skin);
			table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			// creating heading
			Label heading = new Label("WallJumper", skin);
			heading.setFontScale(3);

			// creating buttons
			TextButton exitButton = new TextButton("Exit", skin);
			// Listener for Clicks, exits game
			exitButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Gdx.app.exit();
				}
			});
			exitButton.pad(10);

			
			
			 TextButton buttonSettings = new TextButton("SETTINGS", skin);
			buttonSettings.addListener(new ClickListener() {

				@Override
				public void clicked(InputEvent event, float x, float y) {
					stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

						@Override
						public void run() {
							((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
						}
					})));
				}
			});
			buttonSettings.pad(15);
			
			TextButton buttonPlay = new TextButton("Play", skin);
			buttonPlay.addListener(new ClickListener() {
				
				@Override
				public void clicked(InputEvent event, float x, float y) {
					stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {
		
						@Override
						public void run() {
							((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
						}
					})));
				}
					
			});
			buttonPlay.pad(10);

			// putting stuff together
			table.add(heading);
			table.getCell(heading).spaceBottom(40);
			table.row();
			table.add(buttonPlay);
			table.getCell(buttonPlay).spaceBottom(10);
			table.row();
			//table.add(buttonSettings);
			//table.getCell(buttonSettings).spaceBottom(10);
			//table.row();
			table.add(exitButton);
			stage.addActor(table);
			
			// creating animations
			tweenManager = new TweenManager();
			Tween.registerAccessor(Actor.class, new ActorAccessor());

			// Heading color animation
			Timeline.createSequence()
					.beginSequence()
					.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
					.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
					.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
					.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
					.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
					.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
					.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
					.end().repeat(Tween.INFINITY, 0).start(tweenManager);

			// Heading and buttons fade-in

			Timeline.createSequence().beginSequence()
					.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
					.push(Tween.set(exitButton, ActorAccessor.ALPHA).target(0))
					.push(Tween.from(heading, ActorAccessor.ALPHA, .5f).target(0))
					.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .5f).target(1))
					.push(Tween.to(exitButton, ActorAccessor.ALPHA, .5f).target(1))
					.end().start(tweenManager);

			// Table fade-in
			Tween.from(table, ActorAccessor.ALPHA, .75f).target(0).start(tweenManager);
			Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

			tweenManager.update(Gdx.graphics.getDeltaTime());

		*/
	}

	@Override
	public void hide() {
		stage.dispose();
		skin.dispose();
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
		skin.dispose();
		
	}
	
	public boolean handleTouchInput(int screenX, int screenY, int pointer, int button){
		((Game) Gdx.app.getApplicationListener()).setScreen(World.controller);

		return false;
	}

}
