package level;

import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.collision.RigidCollision;
import components.control.PlayerControl;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.inventory.SubTypeWeapon;
import components.movement.BasicMovement;
import components.spawn.PointSpawn;
import display.Art;
import gui.Hud;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;
import object.Weapon;
import object.WeaponBuilder;
import save.CharacterSave;

public abstract class PlayerBuilder {

	private static PlayerGraphics g;
	private static PointSpawn s;
	private static PlayerAttack a;
	private static RigidCollision c;
	private static BasicMovement m;
	private static BaseInventory i;
	private static Entity player = new Entity();

	public static void buildPlayer(TypeAttack type) {
		chooseType(type);

		player.addComponent(g);
		c = new RigidCollision(player);
		player.addComponent(c);
		m = new BasicMovement(player, c, g, 5);
		i = new BaseInventory(g, a, 0);

		addComponents();
	}

	public static void buildPlayer(TypeAttack type, CharacterSave save) {
		chooseType(type);

		a = new PlayerAttack(type, save);

		player.addComponent(g);
		c = new RigidCollision(player);
		player.addComponent(c);
		m = new BasicMovement(player, c, g, 5);
		i = new BaseInventory(g, a, save);

		addComponents();
	}

	private static void chooseType(TypeAttack type) {
		switch (type) {
			case ARCHER:
				g = new PlayerGraphics(player, Art.getImage("Archer"), Art.base);
				a = new PlayerAttack(type, 4, 5, new Weapon("LONGBOW",0));
				break;
			case BATTLE_MAGE:
				g = new PlayerGraphics(player, Art.getImage("BattleMage"), Art.base);
				a = new PlayerAttack(type, 3, 5 , new Weapon("EARTH_STAFF", 0));
				break;
			case MAGE:
				g = new PlayerGraphics(player, Art.getImage("Mage"), Art.base);
				a = new PlayerAttack(type, 3, 5 , new Weapon("FIRE_STAFF", 0));
				break;
			case ROGUE:
				g = new PlayerGraphics(player, Art.getImage("Rogue"), Art.base);
				a = new PlayerAttack(type, 3, 5 ,new Weapon("CROSSBOW", 0));
				break;
			case WARRIOR:
				g = new PlayerGraphics(player, Art.getImage("Warrior"), Art.base);
				a = new PlayerAttack(type, 5, 3 , new Weapon("SWORD", 0));
				break;
			default:
				g = new PlayerGraphics(player, Art.getImage("Warrior"), Art.base);
		}
		s = new PointSpawn(g, new Vector2(480.0f, 480.0f), player);
	}

	private static void addComponents() {
		player.addComponent(s);
		player.addComponent(a);
		player.addComponent(m);
		player.addComponent(i);
		player.addComponent(new PlayerControl(player, g, a, m, i));

		ShootEmUp.currentLevel.entities.add(player);
		ShootEmUp.currentLevel.hud = new Hud(player, 0, 0);

		ShootEmUp.currentLevel.setPlayer(player);
	}
}
