package components.attack;

import entity.Entity;

public interface AttackComponent {

	boolean attack(Entity e, int dir);

	void update(Entity e);

}
