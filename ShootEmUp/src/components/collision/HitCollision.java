package components.collision;

import object.Element;
import object.Entity;
import object.Weapon;
import main.ShootEmUp;
import components.TypeComponent;

import java.util.Random;

import components.Message;
import components.attack.BaseAttack;

public class HitCollision extends BaseCollision{
	
	Weapon weapon;

	public HitCollision(Entity e, Weapon weapon){
		this.weapon = weapon;
		moveBack = false;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}
	
	@Override
	public void collision(Entity e, Entity hit) {
		
		BaseAttack hitAttack = (BaseAttack) hit.getComponent(TypeComponent.ATTACK);
		if(hitAttack != null){
			if(hitAttack.getWeapon().getTeam() == weapon.getTeam()){
				return;
			}
		}
		if(hit.getComponent(TypeComponent.COLLISION) instanceof RigidCollision){
			e.destroy();
			if(hitAttack != null){
				hitAttack.damage(weapon.getDamage(), hit);
				if(weapon.getElement() == Element.FIRE){
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if(prob == 0){
						hitAttack.setFire(true);
					}
				}
				if(weapon.getElement() == Element.FROST){
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if(prob == 0){
						
					}
				}
				if(weapon.getElement() == Element.EARTH){
					Random rand = new Random();
					int prob = rand.nextInt(3);
					if(prob == 0){
						hitAttack.setPoison(true);
					}
				}
			}	
		}
		/*
		if (hit != null && getTeam() != hit.getTeam()) {
			hit.damage(weapon.getDamage());
		}
		*/
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
