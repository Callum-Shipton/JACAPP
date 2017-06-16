package object;

import java.io.Serializable;

import components.TypeComponent;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.TypePickup;
import components.spawn.PointSpawn;
import display.ImageProcessor;
import entity.Entity;
import main.ShootEmUp;
import math.Vector2;

public abstract class Potion implements Serializable {

	private static final long serialVersionUID = -3226423799063342754L;

	protected String type;

	protected int quantity = 1;

	protected boolean active = false;

	Potion(String type) {
		this.type = type;
	}

	public void addPotion() {
		this.quantity++;
	}

	public void destroy(Entity e) {
		Entity item = new Entity();
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;

		BaseGraphics EntityG = e.getComponent(TypeComponent.GRAPHICS);

		BG = new AnimatedGraphics(ImageProcessor.getImage(this.type), ImageProcessor.base, true, 1f);
		BS = new PointSpawn(BG, new Vector2(EntityG.getX() + BG.getWidth(), EntityG.getY()), item);
		item.addComponent(BG);
		BC = new PickupCollision(item, TypePickup.POTION, this.type);
		item.addComponent(BS);
		item.addComponent(BC);
		ShootEmUp.getCurrentLevel().addEntity(item);
	}

	public int getQuantity() {
		return this.quantity;
	}

	public abstract void update(Entity e);

	public void usePotion() {
		if (this.quantity > 0) {
			this.active = true;
			this.quantity--;
		}
	}
}
