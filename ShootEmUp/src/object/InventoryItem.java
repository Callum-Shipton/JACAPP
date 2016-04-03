package object;

import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.inventory.TypePickup;
import components.spawn.PointSpawn;
import display.Art;
import display.Image;
import main.ShootEmUp;
import math.Vector2;

public abstract class InventoryItem implements DatableObject{

	protected String name;
	protected transient TypePickup typePickup;
	
	public void drop(float x, float y){
		Entity item = new Entity();
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;
		
		BG = new AnimatedGraphics(Art.getImage(name), Art.base, true);
		
		BS = new PointSpawn(BG, new Vector2(x - BG.getWidth(), y - BG.getHeight()), item);
		item.addComponent(BG);
		BC = new PickupCollision(item, typePickup, name);
		item.addComponent(BS);
		item.addComponent(BC);
		ShootEmUp.currentLevel.newEntities.add(item);
	}
	
	public Image getInventoryImage() {
		return Art.getImage(name);
	}

	public TypePickup getTypePickup() {
		return typePickup;
	}
	
	public String getName(){
		return name;
	}
}
