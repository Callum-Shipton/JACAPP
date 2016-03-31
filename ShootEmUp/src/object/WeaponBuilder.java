package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import components.inventory.SubTypeWeapon;
import components.inventory.TypeWeapon;
import display.Art;

public abstract class WeaponBuilder {

	private static Random rand = new Random();

	private static ArrayList<SubTypeWeapon> daggers = new ArrayList<SubTypeWeapon>();

	static {
		daggers.add(SubTypeWeapon.IRON_DAGGAR);
		daggers.add(SubTypeWeapon.STEEL_DAGGAR);
	}

	private static ArrayList<SubTypeWeapon> oneHandedWeapons = new ArrayList<SubTypeWeapon>();

	static {
		oneHandedWeapons.add(SubTypeWeapon.SWORD);
		oneHandedWeapons.add(SubTypeWeapon.MACE);
		;
	}

	private static ArrayList<SubTypeWeapon> twoHandedWeapons = new ArrayList<SubTypeWeapon>();

	static {
		twoHandedWeapons.add(SubTypeWeapon.LONGSWORD);
		twoHandedWeapons.add(SubTypeWeapon.BATTLEAXE);
	}

	private static ArrayList<SubTypeWeapon> bows = new ArrayList<SubTypeWeapon>();

	static {
		bows.add(SubTypeWeapon.LONGBOW);
		bows.add(SubTypeWeapon.HORSEBOW);
	}

	private static ArrayList<SubTypeWeapon> crossbows = new ArrayList<SubTypeWeapon>();

	static {
		crossbows.add(SubTypeWeapon.CROSSBOW);
		crossbows.add(SubTypeWeapon.QUICK_CROSSBOW);
	}

	private static ArrayList<SubTypeWeapon> staffs = new ArrayList<SubTypeWeapon>();

	static {
		staffs.add(SubTypeWeapon.FIRE_STAFF);
		staffs.add(SubTypeWeapon.EARTH_STAFF);
		staffs.add(SubTypeWeapon.ICE_STAFF);
	}

	public static HashMap<TypeWeapon, ArrayList<SubTypeWeapon>> weaponSystem;

	static {
		weaponSystem = new HashMap<TypeWeapon, ArrayList<SubTypeWeapon>>();
		weaponSystem.put(TypeWeapon.DAGGAR, daggers);
		weaponSystem.put(TypeWeapon.ONE_HANDED, oneHandedWeapons);
		weaponSystem.put(TypeWeapon.TWO_HANDED, twoHandedWeapons);
		weaponSystem.put(TypeWeapon.BOW, bows);
		weaponSystem.put(TypeWeapon.CROSSBOW, crossbows);
		weaponSystem.put(TypeWeapon.STAFF, staffs);
	}

	public static Weapon buildWeapon(SubTypeWeapon type, int team) {
		switch (type) {
			case IRON_DAGGAR:
				return new Weapon(TypeWeapon.DAGGAR, type, 3, 1, 5, true, 1, null, team,
						Art.getImage("SwordProjectile"), Art.getImage("SwordButton"));
			case STEEL_DAGGAR:
				return new Weapon(TypeWeapon.DAGGAR, type, 3, 1, 5, true, 1, null, team,
						Art.getImage("SwordProjectile"), Art.getImage("SwordButton"));
			case SWORD:
				return new Weapon(TypeWeapon.ONE_HANDED, type, 3, 1, 5, true, 1, null, team,
						Art.getImage("SwordProjectile"), Art.getImage("SwordButton"));
			case MACE:
				return new Weapon(TypeWeapon.ONE_HANDED, type, 5, 1, 15, true, 3, null, team,
						Art.getImage("SwordProjectile"), Art.getImage("MaceButton"));
			case BATTLEAXE:
				return new Weapon(TypeWeapon.TWO_HANDED, type, 4, 1, 10, true, 2, null, team,
						Art.getImage("SwordProjectile"), Art.getImage("BattleaxeButton"));
			case LONGSWORD:
				return new Weapon(TypeWeapon.TWO_HANDED, type, 3, 1, 5, true, 1, null, team,
						Art.getImage("SwordProjectile"), Art.getImage("SwordButton"));
			case CROSSBOW:
				return new Weapon(TypeWeapon.CROSSBOW, type, 2, 5, 10, false, 2, null, team, Art.getImage("Arrow"),
						Art.getImage("CrossBowButton"));
			case QUICK_CROSSBOW:
				return new Weapon(TypeWeapon.CROSSBOW, type, 2, 5, 10, false, 2, null, team, Art.getImage("Arrow"),
						Art.getImage("CrossBowButton"));
			case LONGBOW:
				return new Weapon(TypeWeapon.BOW, type, 1, 5, 5, false, 1, null, team, Art.getImage("Arrow"),
						Art.getImage("BowButton"));
			case HORSEBOW:
				return new Weapon(TypeWeapon.BOW, type, 1, 5, 5, false, 1, null, team, Art.getImage("Arrow"),
						Art.getImage("BowButton"));
			case FIRE_STAFF:
				return new Weapon(TypeWeapon.STAFF, type, 1, 3, 10, false, 1, Element.FIRE, team,
						Art.getImage("FireMagic"), Art.getImage("FireStaffButton"));
			case ICE_STAFF:
				return new Weapon(TypeWeapon.STAFF, type, 1, 3, 10, false, 1, Element.FROST, team,
						Art.getImage("IceMagic"), Art.getImage("IceStaffButton"));
			case EARTH_STAFF:
				return new Weapon(TypeWeapon.STAFF, type, 2, 3, 10, false, 2, Element.EARTH, team,
						Art.getImage("EarthMagic"), Art.getImage("EarthStaffButton"));
		}
		System.out.println("no weapon");
		return null;
	}

	public static Weapon buildWeapon(TypeWeapon type, int team) {
		int temp = rand.nextInt(weaponSystem.get(type).size());
		return buildWeapon(weaponSystem.get(type).get(temp), team);
	}
}
