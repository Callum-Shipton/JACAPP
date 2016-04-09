package object;

import java.io.Serializable;

import components.TypeComponent;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
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

	public void destroy(Entity e) {
		Entity item = new Entity();
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;
		
		BaseGraphics EntityG = e.getComponent(TypeComponent.GRAPHICS);
		
		BG = new AnimatedGraphics(Art.getImage(type), Art.base, true, 1f);
		BS = new PointSpawn(BG, new Vector2(EntityG.getX() - BG.getWidth(), EntityG.getY() - BG.getHeight()), item);
		item.addComponent(BG);
		BC = new PickupCollision(item, TypePickup.POTION, type);
		item.addComponent(BS);
		item.addComponent(BC);
		ShootEmUp.getCurrentLevel().newEntities.add(item);
	}
}
