package object;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.joml.Vector2f;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.TypeComponent;
import components.collision.PickupCollision;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.TypePickup;
import components.spawn.PointSpawn;
import display.Image;
import display.ImageProcessor;
import entity.DatableObject;
import entity.Entity;
import logging.Logger;
import main.ShootEmUp;

public abstract class InventoryItem<I extends InventoryItem<?>> implements DatableObject<I>, Serializable {

	private static final long serialVersionUID = 4785946601775436341L;

	protected static Random rand = new Random();

	protected String name;
	protected transient String type;
	protected transient TypePickup typePickup;

	public void destroy(Entity e) {
		Entity item = new Entity();
		AnimatedGraphics BG = null;
		PointSpawn BS;
		PickupCollision BC;

		BaseGraphics entityG = e.getComponent(TypeComponent.GRAPHICS);

		BG = new AnimatedGraphics(ImageProcessor.getImage(this.name), ImageProcessor.base, true, 1f);
		BS = new PointSpawn(BG, new Vector2f(entityG.getX() + BG.getWidth(), entityG.getY() + BG.getHeight()), item);
		item.addComponent(BG);
		BC = new PickupCollision(item, this.typePickup, this.name);
		item.addComponent(BS);
		item.addComponent(BC);
		ShootEmUp.getGame().getCurrentLevel().addEntity(item);
	}

	public Image getInventoryImage() {
		return ImageProcessor.getImage(this.name + "Button");
	}

	public String getName() {
		return this.name;
	}

	public TypePickup getTypePickup() {
		return this.typePickup;
	}

	@Override
	public JsonArray readJSON(String path, String fileName) {
		JsonReader in = null;
		try (FileInputStream fileInput = new FileInputStream(path)) {
			in = new JsonReader(new InputStreamReader(fileInput));

			return new JsonParser().parse(in).getAsJsonArray();
		} catch (IOException e) {
			Logger.error(e);
		}
		return null;
	}

	public void initSystem(Class<I> itemClass) {
		Map<String, JsonArray> jsonObjects = findFiles(getDirectoryPath());
		for (Entry<String, JsonArray> itemType : jsonObjects.entrySet()) {
			addToSystem(itemType.getKey(), itemType.getValue(), itemClass);
		}
	}

	protected void addToSystem(String typeName, JsonArray type, Class<I> itemClass) {
		Map<String, Map<String, I>> itemSystem = getSystem();
		for (JsonElement item : type) {
			String subType = typeName.substring(0, typeName.length() - 5);
			String itemName = item.getAsJsonObject().get("name").getAsString();
			if (!itemSystem.containsKey(subType)) {
				itemSystem.put(subType, new HashMap<String, I>());
			}
			I a = g.fromJson(item, itemClass);
			a.type = subType;
			itemSystem.get(subType).put(itemName, a);
		}
	}

	protected abstract String getDirectoryPath();
}
