package level;

import org.joml.Vector2f;

import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.collision.RigidCollision;
import components.control.PlayerControl;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.movement.BasicMovement;
import components.spawn.PointSpawn;
import display.ImageProcessor;
import entity.Entity;
import object.Weapon;
import save.CharacterSave;

public abstract class PlayerBuilder {

	private static PlayerGraphics g;
	private static PointSpawn s;
	private static PlayerAttack a;
	private static RigidCollision c;
	private static BasicMovement m;
	private static BaseInventory i;
	private static Entity player;

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
		player = new Entity();
		chooseType(type);

		c = new RigidCollision();
		m = new BasicMovement(c, g, 5);
		i = new BaseInventory(g, a, 0);

		addComponents();
		return player;
	}

	public static Entity buildPlayer(TypeAttack type, CharacterSave save) {
		player = new Entity();
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
			g = new PlayerGraphics(ImageProcessor.getImage("Archer"), ImageProcessor.base, 1f);
			a = new PlayerAttack(type, 4, 5, new Weapon("Longbow", 0));
			break;
		case BATTLE_MAGE:
			g = new PlayerGraphics(ImageProcessor.getImage("BattleMage"), ImageProcessor.base, 1f);
			a = new PlayerAttack(type, 3, 5, new Weapon("FireStaff", 0));
			break;
		case MAGE:
			g = new PlayerGraphics(ImageProcessor.getImage("Mage"), ImageProcessor.base, 1f);
			a = new PlayerAttack(type, 3, 5, new Weapon("FireStaff", 0));
			break;
		case ROGUE:
			g = new PlayerGraphics(ImageProcessor.getImage("Rogue"), ImageProcessor.base, 1f);
			a = new PlayerAttack(type, 3, 5, new Weapon("IronDagger", 0));
			break;
		case WARRIOR:
			g = new PlayerGraphics(ImageProcessor.getImage("Warrior"), ImageProcessor.base, 1f);
			a = new PlayerAttack(type, 5, 3, new Weapon("Greatsword", 0));
			break;
		default:
			g = new PlayerGraphics(ImageProcessor.getImage("Warrior"), ImageProcessor.base, 1f);
		}
		s = new PointSpawn(g, new Vector2f(480.0f, 480.0f), player);
	}
}
