package components.attack;

import entities.Entity;

public interface AttackComponent {
	
	void attack(Entity e,  int dir);
	void update(Entity e);
	
}
