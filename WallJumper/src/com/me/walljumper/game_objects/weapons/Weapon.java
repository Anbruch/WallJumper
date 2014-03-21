package com.me.walljumper.game_objects.weapons;

import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.abilities.GenericAbility;
import com.me.walljumper.game_objects.classes.ManipulatableObject;

import com.badlogic.gdx.utils.Array;

public abstract class Weapon extends AbstractGameObject {
	
	
	public ManipulatableObject owner;
	public WEAPON_STATE weaponState;
	public Array<GenericAbility> abilities;
	
	public enum WEAPON_STATE{
		ABILITY, NORMAL
	}
	
	public Weapon(){
		
	}
	
	public Weapon(ManipulatableObject owner){
		this.owner = owner;
		weaponState = WEAPON_STATE.NORMAL;
		
	}
	
	
	@Override
	public void update(float deltaTime){
		
	}
}
