package object;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import org.joml.Vector2f;

import components.MessageId;
import components.TypeComponent;
import components.audio.BaseAudio;
import components.audio.EventAudio;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.TypePickup;
import components.spawn.PointSpawn;
import display.ImageProcessor;
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
		Entity item = new Entity();
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;

		BaseGraphics EntityG = e.getComponent(TypeComponent.GRAPHICS);

		BG = new AnimatedGraphics(ImageProcessor.getImage(type), ImageProcessor.base, true, 1f);
		BS = new PointSpawn(new Vector2f(EntityG.getX() + BG.getWidth(), EntityG.getY()), item);
		item.addComponent(BG);
		BC = new PickupCollision(item, TypePickup.POTION, type);
		Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);
		sounds.put(MessageId.PICKUP, "Pickup2.ogg");
		BaseAudio audioComponent = new EventAudio(sounds);
		item.addComponent(BS);
		item.addComponent(BC);
		item.addComponent(audioComponent);
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
