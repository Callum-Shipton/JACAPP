package level;

import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.collision.RigidCollision;
import components.control.PlayerControl;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.movement.BasicMovement;
import components.spawn.PointSpawn;
import display.Art;
import entity.Entity;
import math.Vector2;
import object.Weapon;
import save.CharacterSave;

public abstract class PlayerBuilder {

	private static PlayerGraphics g;
	private static PointSpawn s;
	private static PlayerAttack a;
	private static RigidCollision c;
	private static BasicMovement m;
	private static BaseInventory i;
	private static Entity player = new Entity();

	private static void addComponents() {
		player.addComponent(g);
		player.addComponent(c);
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(i);
		player.addComponent(new PlayerControl(g, a, m, i));
	}

	public static Entity buildPlayer(TypeAttack type) {
		chooseType(type);

		c = new RigidCollision();
		m = new BasicMovement(c, g, 5);
		i = new BaseInventory(g, a, 0);

		addComponents();
		return player;
	}

	public static Entity buildPlayer(TypeAttack type, CharacterSave save) {
		chooseType(type);

		a = new PlayerAttack(type, save);
		c = new RigidCollision();
		m = new BasicMovement(c, g, 5);
		i = new BaseInventory(g, a, save);

		addComponents();
		return player;
	}

	private static void chooseType(TypeAttack type) {
		switch (type) {
		case ARCHER:
			g = new PlayerGraphics(Art.getImage("Archer"), Art.base, 1f);
			a = new PlayerAttack(type, 4, 5, new Weapon("Longbow", 0));
			break;
		case BATTLE_MAGE:
			g = new PlayerGraphics(Art.getImage("BattleMage"), Art.base, 1f);
			a = new PlayerAttack(type, 3, 5, new Weapon("FireStaff", 0));
			break;
		case MAGE:
			g = new PlayerGraphics(Art.getImage("Mage"), Art.base, 1f);
			a = new PlayerAttack(type, 3, 5, new Weapon("FireStaff", 0));
			break;
		case ROGUE:
			g = new PlayerGraphics(Art.getImage("Rogue"), Art.base, 1f);
			a = new PlayerAttack(type, 3, 5, new Weapon("IronDagger", 0));
			break;
		case WARRIOR:
			g = new PlayerGraphics(Art.getImage("Warrior"), Art.base, 1f);
			a = new PlayerAttack(type, 5, 3, new Weapon("Greatsword", 0));
			break;
		default:
			g = new PlayerGraphics(Art.getImage("Warrior"), Art.base, 1f);
		}
		s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
	}
}
