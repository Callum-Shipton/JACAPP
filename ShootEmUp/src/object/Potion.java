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
		AnimatedGraphics potionGraphics;
		PointSpawn potionSpawn;
		PickupCollision potionCollision;

		BaseGraphics entityG = e.getComponent(TypeComponent.GRAPHICS);

		potionGraphics = new AnimatedGraphics(ImageProcessor.getImage(type), ImageProcessor.base, true, 1f, item);
		potionSpawn = new PointSpawn(new Vector2f(entityG.getX() + potionGraphics.getWidth(), entityG.getY()), item);
		item.addComponent(potionGraphics);
		potionCollision = new PickupCollision(TypePickup.POTION, type, item);
		Map<MessageId, String> sounds = new EnumMap<>(MessageId.class);
		sounds.put(MessageId.PICKUP, "Pickup2.ogg");
		BaseAudio audioComponent = new EventAudio(sounds, item);
		item.addComponent(potionSpawn);
		item.addComponent(potionCollision);
		item.addComponent(audioComponent);
		potionSpawn.spawn(item);
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
