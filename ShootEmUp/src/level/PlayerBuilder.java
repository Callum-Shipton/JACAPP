package level;

import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.collision.RigidCollision;
import components.control.PlayerControl;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.movement.GroundMovement;
import components.spawn.PointSpawn;
import entity.Entity;
import save.CharacterSave;

public abstract class PlayerBuilder {

	private static PlayerGraphics g;
	private static PointSpawn s;
	private static PlayerAttack a;
	private static RigidCollision c;
	private static GroundMovement m;
	private static BaseInventory i;
	private static Entity player;

	private static void addComponents() {
		player.addComponent(g);
		player.addComponent(c);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(i);
		player.addComponent(new PlayerControl());
		s.spawn();
	}

	public static Entity buildPlayer(TypeAttack type, CharacterSave save) {
		player = new Entity();

		a = new PlayerAttack(save);
		c = new RigidCollision();
		m = new GroundMovement(5);
		i = new BaseInventory(save);

		addComponents();
		return player;
	}
}
