package level;

import org.joml.Vector2f;

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

	public static Entity buildPlayer(TypeAttack type) {
		player = new Entity();
		chooseType(type);

		c = new RigidCollision();
		m = new GroundMovement(5);
		i = new BaseInventory(0);

		addComponents();
		return player;
	}

	public static Entity buildPlayer(TypeAttack type, CharacterSave save) {
		player = new Entity();
		chooseType(type);

		a = new PlayerAttack(save);
		c = new RigidCollision();
		m = new GroundMovement(5);
		i = new BaseInventory(save);

		addComponents();
		return player;
	}

	private static void chooseType(TypeAttack type) {
		switch (type) {
		case ARCHER:
			g = new PlayerGraphics("Archer");
			a = new PlayerAttack(4, 5, "Longbow", 0, null);
			break;
		case BATTLE_MAGE:
			g = new PlayerGraphics("BattleMage");
			a = new PlayerAttack(3, 5, "FireStaff", 0, null);
			break;
		case MAGE:
			g = new PlayerGraphics("Mage");
			a = new PlayerAttack(3, 5, "FireStaff", 0, null);
			break;
		case ROGUE:
			g = new PlayerGraphics("Rogue");
			a = new PlayerAttack(3, 5, "IronDagger", 0, null);
			break;
		case WARRIOR:
			g = new PlayerGraphics("Warrior");
			a = new PlayerAttack(5, 3, "Greatsword", 0, null);
			break;
		default:
			g = new PlayerGraphics("Warrior");
		}
		s = new PointSpawn(new Vector2f(480.0f, 480.0f));
	}
}
