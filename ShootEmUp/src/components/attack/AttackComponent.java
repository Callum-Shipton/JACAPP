package components.attack;

import object.Entity;

public interface AttackComponent {

	void attack(Entity e, int dir);

	void update(Entity e);

}
