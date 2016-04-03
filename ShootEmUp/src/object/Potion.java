package object;

import java.io.Serializable;

import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.inventory.TypePickup;
import components.spawn.PointSpawn;
import display.Art;
import main.ShootEmUp;
import math.Vector2;

public abstract class Potion implements Serializable {

	private static final long serialVersionUID = -3226423799063342754L;

	protected String type;

	public int quantity = 1;

	protected boolean active = false;

	Potion(String type) {
		this.type = type;
	}

	public abstract void update(Entity e);

	public void usePotion() {
		if (quantity > 0) {
			active = true;
			quantity--;
		}
	}

	public void addPotion() {
		quantity++;
	}

	public void drop(float x, float y) {
		Entity item = new Entity();
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;
		
		BG = new AnimatedGraphics(Art.getImage(type), Art.base, true);
		
		BS = new PointSpawn(BG, new Vector2(x - BG.getWidth(), y - BG.getHeight()), item);
		item.addComponent(BG);
		BC = new PickupCollision(item, TypePickup.POTION, type);
		item.addComponent(BS);
		item.addComponent(BC);
		ShootEmUp.currentLevel.newEntities.add(item);
	}
}
