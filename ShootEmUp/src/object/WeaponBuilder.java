package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import components.inventory.SubTypeWeapon;
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
	}

	private static ArrayList<SubTypeWeapon> twoHandedWeapons = new ArrayList<SubTypeWeapon>();

	static {
		twoHandedWeapons.add(SubTypeWeapon.LONGSWORD);
		twoHandedWeapons.add(SubTypeWeapon.BATTLEAXE);
	}

	private static ArrayList<SubTypeWeapon> bows = new ArrayList<SubTypeWeapon>();

	static {
		bows.add(SubTypeWeapon.LONGBOW);
		bows.add(SubTypeWeapon.SHORTBOW);
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

}
