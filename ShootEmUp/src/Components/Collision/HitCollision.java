package Components.Collision;

import Components.Message;
import Main.ShootEmUp;
import Object.Entity;

public class HitCollision extends BaseCollision{

	public HitCollision(){
		
	}
	
	@Override
	public void collision(Entity hitter, Entity hit) {
		hitter.destroy();
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
