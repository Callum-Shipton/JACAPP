package Components.Attack;

import Object.Entity;

public interface AttackComponent {
	
	void attack(Entity e,  int dir);
	void update(Entity e);
	
}
