package components.inventory;

import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.spawn.PointSpawn;
import display.Art;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public abstract class PickupBuilder {
	
	public static void buildPickup(TypePickup type, SubType subtype, SubSubType subsubtype, float x, float y){
		
		Entity item = new Entity();
		
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;
		
		switch(type){
		case ARMOUR:
			switch((TypeArmour)subtype){
			case HELMET:
				switch((SubTypeArmour)subsubtype){
				case IRON:
					BG = new AnimatedGraphics(Art.ironHelmet, Art.base, true);
					break;
				case LEATHER:
					BG = new AnimatedGraphics(Art.leatherHelmet, Art.base, true);
					break;
				}
				break;
			case CHESTPLATE:
				switch((SubTypeArmour)subsubtype){
				case IRON:
					BG = new AnimatedGraphics(Art.ironChest, Art.base, true);
					break;
				case LEATHER:
					BG = new AnimatedGraphics(Art.leatherChest, Art.base, true);
					break;
				}
				break;
			case LEGS:
				switch((SubTypeArmour)subsubtype){
				case IRON:
					BG = new AnimatedGraphics(Art.ironLegs, Art.base, true);
					break;
				case LEATHER:
					BG = new AnimatedGraphics(Art.leatherLegs, Art.base, true);
					break;
				}
				break;
			case BOOTS:
				switch((SubTypeArmour)subsubtype){
				case IRON:
					BG = new AnimatedGraphics(Art.ironBoots, Art.base, true);
					break;
				case LEATHER:
					BG = new AnimatedGraphics(Art.leatherBoots, Art.base, true);
					break;
				}
				break;
			}
			break;
		case COIN:
			switch((TypeCoin)subtype){
			case ONE:
				BG = new AnimatedGraphics(Art.coin, Art.base, true);
				break;
			case FIVE:
				BG = new AnimatedGraphics(Art.coin, Art.base, true);
				break;
			case TEN:
				BG = new AnimatedGraphics(Art.coin, Art.base, true);
				break;
			}
			break;
		case POTION:
			switch((TypePotion)subtype){
			case HEALTH:
				BG = new AnimatedGraphics(Art.healthPotion, Art.base, true);
				break;
			case KNOCKBACK:
				BG = new AnimatedGraphics(Art.knockbackPotion, Art.base, true);
				break;
			case MANA:
				BG = new AnimatedGraphics(Art.manaPotion, Art.base, true);
				break;
			case SPEED:
				BG = new AnimatedGraphics(Art.speedPotion, Art.base, true);
				break;
			}
			break;
		case WEAPON:
			switch((SubTypeWeapon)subsubtype){
			case BATTLEAXE:
				BG = new AnimatedGraphics(Art.battleaxe, Art.base, true);
				break;
			case LONGBOW:
				BG = new AnimatedGraphics(Art.bow, Art.base, true);
				break;
			case CROSSBOW:
				BG = new AnimatedGraphics(Art.crossbow, Art.base, true);
				break;
			case EARTH_STAFF:
				BG = new AnimatedGraphics(Art.earthStaff, Art.base, true);
				break;
			case FIRE_STAFF:
				BG = new AnimatedGraphics(Art.fireStaff, Art.base, true);
				break;
			case ICE_STAFF:
				BG = new AnimatedGraphics(Art.iceStaff, Art.base, true);
				break;
			case MACE:
				BG = new AnimatedGraphics(Art.mace, Art.base, true);
				break;
			case SWORD:
				BG = new AnimatedGraphics(Art.sword, Art.base, true);
				break;
			case HORSEBOW:
				BG = new AnimatedGraphics(Art.bow, Art.base, true);
				break;
			case IRON_DAGGAR:
				BG = new AnimatedGraphics(Art.sword, Art.base, true);
				break;
			case LONGSWORD:
				BG = new AnimatedGraphics(Art.sword, Art.base, true);
				break;
			case QUICK_CROSSBOW:
				BG = new AnimatedGraphics(Art.crossbow, Art.base, true);
				break;
			case STEEL_DAGGAR:
				BG = new AnimatedGraphics(Art.sword, Art.base, true);
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
