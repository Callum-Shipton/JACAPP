package components.inventory;

import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.spawn.PointSpawn;
import display.Art;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public abstract class PickupBuilder {

	public static void buildPickup(TypePickup type, SubType subtype, SubSubType subsubtype, float x, float y) {

		Entity item = new Entity();

		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;

		switch (type) {
			case ARMOUR:
				switch ((TypeArmour) subtype) {
					case HELMET:
						switch ((SubTypeArmour) subsubtype) {
							case IRON:
								BG = new AnimatedGraphics(Art.getImage("IronHelmet"), Art.base, true);
								break;
							case LEATHER:
								BG = new AnimatedGraphics(Art.getImage("LeatherHelmet"), Art.base, true);
								break;
						}
						break;
					case CHESTPLATE:
						switch ((SubTypeArmour) subsubtype) {
							case IRON:
								BG = new AnimatedGraphics(Art.getImage("IronChest"), Art.base, true);
								break;
							case LEATHER:
								BG = new AnimatedGraphics(Art.getImage("LeatherChest"), Art.base, true);
								break;
						}
						break;
					case LEGS:
						switch ((SubTypeArmour) subsubtype) {
							case IRON:
								BG = new AnimatedGraphics(Art.getImage("IronLegs"), Art.base, true);
								break;
							case LEATHER:
								BG = new AnimatedGraphics(Art.getImage("LeatherLegs"), Art.base, true);
								break;
						}
						break;
					case BOOTS:
						switch ((SubTypeArmour) subsubtype) {
							case IRON:
								BG = new AnimatedGraphics(Art.getImage("IronBoots"), Art.base, true);
								break;
							case LEATHER:
								BG = new AnimatedGraphics(Art.getImage("LeatherBoots"), Art.base, true);
								break;
						}
						break;
				}
				break;
			case COIN:
				switch ((TypeCoin) subtype) {
					case ONE:
						BG = new AnimatedGraphics(Art.getImage("Coin"), Art.base, true);
						break;
					case FIVE:
						BG = new AnimatedGraphics(Art.getImage("Coin"), Art.base, true);
						break;
					case TEN:
						BG = new AnimatedGraphics(Art.getImage("Coin"), Art.base, true);
						break;
				}
				break;
			case POTION:
				switch ((TypePotion) subtype) {
					case HEALTH:
						BG = new AnimatedGraphics(Art.getImage("HealthPotion"), Art.base, true);
						break;
					case KNOCKBACK:
						BG = new AnimatedGraphics(Art.getImage("KnockbackPotion"), Art.base, true);
						break;
					case MANA:
						BG = new AnimatedGraphics(Art.getImage("ManaPotion"), Art.base, true);
						break;
					case SPEED:
						BG = new AnimatedGraphics(Art.getImage("SpeedPotion"), Art.base, true);
						break;
				}
				break;
			case WEAPON:
				switch ((SubTypeWeapon) subsubtype) {
					case BATTLEAXE:
						BG = new AnimatedGraphics(Art.getImage("Battleaxe"), Art.base, true);
						break;
					case LONGBOW:
						BG = new AnimatedGraphics(Art.getImage("Bow"), Art.base, true);
						break;
					case CROSSBOW:
						BG = new AnimatedGraphics(Art.getImage("Crossbow"), Art.base, true);
						break;
					case EARTH_STAFF:
						BG = new AnimatedGraphics(Art.getImage("EarthStaff"), Art.base, true);
						break;
					case FIRE_STAFF:
						BG = new AnimatedGraphics(Art.getImage("FireStaff"), Art.base, true);
						break;
					case ICE_STAFF:
						BG = new AnimatedGraphics(Art.getImage("IceStaff"), Art.base, true);
						break;
					case MACE:
						BG = new AnimatedGraphics(Art.getImage("Mace"), Art.base, true);
						break;
					case SWORD:
						BG = new AnimatedGraphics(Art.getImage("Sword"), Art.base, true);
						break;
					case HORSEBOW:
						BG = new AnimatedGraphics(Art.getImage("Bow"), Art.base, true);
						break;
					case IRON_DAGGAR:
						BG = new AnimatedGraphics(Art.getImage("Sword"), Art.base, true);
						break;
					case LONGSWORD:
						BG = new AnimatedGraphics(Art.getImage("Sword"), Art.base, true);
						break;
					case QUICK_CROSSBOW:
						BG = new AnimatedGraphics(Art.getImage("Crossbow"), Art.base, true);
						break;
					case STEEL_DAGGAR:
						BG = new AnimatedGraphics(Art.getImage("Sword"), Art.base, true);
						break;
				}
		}

		BS = new PointSpawn(BG, new Vector2(x - BG.getWidth(), y - BG.getHeight()), item);
		item.addComponent(BG);
		BC = new PickupCollision(item, type, subtype, subsubtype);
		item.addComponent(BS);
		item.addComponent(BC);
		ShootEmUp.currentLevel.newEntities.add(item);
	}
}
