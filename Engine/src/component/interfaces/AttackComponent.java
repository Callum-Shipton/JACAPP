package component.interfaces;

import entity.Entity;

public interface AttackComponent {

	boolean attack(Entity e, int dir);

	void update();

}
