package components.collision;

import object.Entity;
import object.Weapon;
import main.ShootEmUp;
import components.ComponentType;
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
		
		BaseAttack hitAttack = (BaseAttack) hit.getComponent(ComponentType.ATTACK);
		if(hitAttack != null){
			if(hitAttack.getWeapon().getTeam() == weapon.getTeam()){
				return;
			}
		}
		if(hit.getComponent(ComponentType.COLLISION) instanceof RigidCollision){
			e.destroy();
			if(hitAttack != null) hitAttack.damage(weapon.getDamage(), hit); //needs updating to take weapon damage
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
