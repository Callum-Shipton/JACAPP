package object;

import components.inventory.SubTypeWeapon;
import components.inventory.TypeWeapon;
import display.Art;

public abstract class WeaponBuilder {
	
	public static Weapon buildWeapon(SubTypeWeapon type, int team){
		switch(type){
		case SWORD:
			return new Weapon(TypeWeapon.ONE_HANDED, type, 3, 1, 5, true, 1, null, team, Art.swordProjectile, Art.swordButton);
		case BATTLEAXE:
			return new Weapon(TypeWeapon.TWO_HANDED, type, 4, 1, 10, true, 2, null, team, Art.swordProjectile, Art.battleaxeButton);
		case MACE:
			return new Weapon(TypeWeapon.ONE_HANDED, type, 5, 1, 15, true, 3, null, team, Art.swordProjectile, Art.maceButton);
		case CROSSBOW:
			return new Weapon(TypeWeapon.CROSSBOW, type, 2, 5, 10, false, 2, null, team, Art.arrow, Art.crossbowButton);
		case BOW:
			return new Weapon(TypeWeapon.BOW, type, 1, 5, 5, false, 1, null, team, Art.arrow, Art.bowButton);
		case FIRE_STAFF:
			return new Weapon(TypeWeapon.STAFF, type, 1, 3, 10, false, 1, Element.FIRE, team, Art.fireMagic, Art.fireStaffButton);
		case ICE_STAFF:
			return new Weapon(TypeWeapon.STAFF, type, 1, 3, 10, false, 1, Element.FROST, team, Art.iceMagic, Art.iceStaffButton);
		case EARTH_STAFF:
			return new Weapon(TypeWeapon.STAFF ,type, 2, 3, 10, false, 2, Element.EARTH, team, Art.earthMagic, Art.earthStaffButton);
		}
		System.out.println("no weapon");
		return null;
	}
}
