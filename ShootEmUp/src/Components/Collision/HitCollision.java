package Components.Collision;

import Components.ComponentType;
import Components.Message;
import Components.Attack.BaseAttack;
import Main.ShootEmUp;
import Object.Entity;

public class HitCollision extends BaseCollision{

	public HitCollision(){
		
	}
	
	@Override
	public void collision(Entity hitter, Entity hit) {
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, hitter);
		ShootEmUp.currentLevel.oldEntities.add(hitter);
		
		if(hit.getComponent(ComponentType.ATTACK) != null){
			System.out.println("hit");
			((BaseAttack)hit.getComponent(ComponentType.ATTACK)).damage(2); //needs updating to take weapon damage
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
