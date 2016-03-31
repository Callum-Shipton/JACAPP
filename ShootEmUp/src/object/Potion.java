package object;

import java.io.Serializable;

import components.inventory.TypePotion;

public abstract class Potion implements Serializable {

	private static final long serialVersionUID = -3226423799063342754L;

	protected TypePotion type;

	public int quantity = 1;

	protected boolean active = false;

	Potion(TypePotion type) {
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
}
