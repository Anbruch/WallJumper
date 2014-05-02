package com.me.walljumper.game_objects.terrain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.Constants;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.particles.Rain;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.WorldRenderer;

public class Weather {
	
	private Array<Rain> drops;
	private static float curTime = 0, nextTime, spacing = .03f;
	public Weather(){
		drops = new Array<Rain>();
		nextTime = spacing;
	}
	
	public void update(float deltaTime){
		if(!WorldRenderer.renderer.weatherBool)
			return;
		trySpawn(deltaTime);
		
		//Update and possibly remove drops
		for(int i = 0; i < drops.size; i++){
			Rain drop = drops.get(i);
			drop.update(deltaTime);
			if(!drop.active){
				drops.removeIndex(i);
				drops.shrink();
			}
		}
		
		
		
	}

	private void trySpawn(float deltaTime) {
		curTime += deltaTime;
		while(curTime > nextTime){
			try{
				AbstractGameObject player = World.controller.cameraHelper.getTarget();
					float x = (float) (player.position.x - Constants.viewportWidth * 2 + Math.random() * 3f * Constants.viewportWidth) 
							,y = (float) (player.position.y + Constants.viewportHeight / 2);
					drops.add(new Rain("snow", x, y));
					nextTime += spacing;
			}catch(NullPointerException e){
				return;
			}
		}
	}
	public void destroy(){
		curTime = 0;
		nextTime = 0;
		while(drops.size > 0){
			drops.removeIndex(0);
		}
		drops = null;
	}
	public void render(SpriteBatch batch){
		for(Rain drop : drops){
			drop.render(batch);
		}
	}
}
