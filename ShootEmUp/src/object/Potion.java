package object;

import java.io.Serializable;

import org.joml.Vector2f;

import components.TypeComponent;
import components.graphical.BaseGraphics;
import components.spawn.PointSpawn;
import entity.Entity;
import main.ShootEmUp;

public abstract class Potion implements Serializable {

	private static final long serialVersionUID = -3226423799063342754L;

	protected String type;

	protected int quantity = 1;

	protected boolean active = false;

	Potion(String type) {
		this.type = type;
	}

	public void addPotion() {
		quantity++;
	}

	public void destroy(Entity e) {
		Entity item = new Entity("Items", "Potions", type);

		BaseGraphics entityG = e.getComponent(TypeComponent.GRAPHICS);
		BaseGraphics potionGraphics = e.getComponent(TypeComponent.GRAPHICS);

		PointSpawn potionSpawn = item.getComponent(TypeComponent.SPAWN);
		potionSpawn.setSpawnLocation(new Vector2f(entityG.getX() + potionGraphics.getWidth(), entityG.getY()));
		potionSpawn.spawn();
		ShootEmUp.getGame().getCurrentLevel().addEntity(item);
	}

	public int getQuantity() {
		return quantity;
	}

	public abstract void update(Entity e);

	public void usePotion() {
		if (quantity > 0) {
			active = true;
			quantity--;
		}
	}
}
