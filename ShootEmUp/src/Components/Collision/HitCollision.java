package Components.Collision;

import Components.ComponentType;
import Components.Message;
import Components.Attack.BaseAttack;
import Main.ShootEmUp;
import Object.Entity;
import Object.Weapon;

public class HitCollision extends BaseCollision{
	
	Weapon weapon;

	public HitCollision(Entity e, Weapon weapon){
		this.weapon = weapon;
		moveBack = false;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}
	
	@Override
	public void collision(Entity hitter, Entity hit) {
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, hitter);
		ShootEmUp.currentLevel.oldEntities.add(hitter);
		
		BaseAttack hitAttack = (BaseAttack) hit.getComponent(ComponentType.ATTACK);
		
		if(hitAttack != null){
			System.out.println("hit");
			hitAttack.damage(weapon.getDamage()); //needs updating to take weapon damage
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
