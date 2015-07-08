package object;

import components.inventory.TypeWeapon;
import display.Art;

public abstract class WeaponBuilder {
	
	public static Weapon buildWeapon(TypeWeapon type, int team){
		switch(type){
		case SWORD:
			return new Weapon(type, 1, 3, 5, true, 1, team, Art.swordProjectile, Art.swordButton);
		case BATTLEAXE:
			return new Weapon(type, 2, 2, 10, true, 2, team, Art.swordProjectile, Art.battleaxeButton);
		case MACE:
			return new Weapon(type, 3, 2, 15, true, 3, team, Art.swordProjectile, Art.maceButton);
		case CROSSBOW:
			return new Weapon(type, 3, 3, 10, false, 1, team, Art.arrow, Art.crossbowButton);
		case BOW:
			return new Weapon(type, 2, 2, 5, false, 1, team, Art.arrow, Art.bowButton);
		case FIRE_STAFF:
			return new Weapon(type, 1, 3, 5, false, 1, team, Art.fireMagic, Art.fireStaffButton);
		case ICE_STAFF:
			return new Weapon(type, 1, 3, 5, false, 1, team, Art.iceMagic, Art.iceStaffButton);
		case EARTH_STAFF:
			return new Weapon(type, 2, 3, 10, false, 2, team, Art.earthMagic, Art.earthStaffButton);
		}
		System.out.println("no weapon");
		return null;
	}
}
