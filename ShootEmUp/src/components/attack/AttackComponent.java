package components.attack;

import entity.Entity;

public interface AttackComponent {

	void attack(Entity e, int dir);

	void update(Entity e);

}
