package object;

import java.io.Serializable;
import java.util.Random;

import components.TypeComponent;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.TypePickup;
import components.spawn.PointSpawn;
import display.Art;
import display.Image;
import main.ShootEmUp;
import math.Vector2;

public abstract class InventoryItem implements DatableObject, Serializable {

	private static final long serialVersionUID = 4785946601775436341L;
	
	protected static Random rand = new Random();
	
	protected String name;
	protected transient TypePickup typePickup;
	
	public void destroy(Entity e){
		Entity item = new Entity();
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;
		
		BaseGraphics entityG = e.getComponent(TypeComponent.GRAPHICS);
		
		BG = new AnimatedGraphics(Art.getImage(name), Art.base, true, 1f);
		BS = new PointSpawn(BG, new Vector2(entityG.getX() - BG.getWidth(), entityG.getY() - BG.getHeight()), item);
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
