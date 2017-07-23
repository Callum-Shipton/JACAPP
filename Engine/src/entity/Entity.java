package entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.Component;
import components.Message;
import components.TypeComponent;
import logging.Logger;

public class Entity implements DatableObject<Entity> {

	private static Map<String, HashMap<String, HashMap<String, Entity>>> entitySystem;
	private static HashMap<Class<? extends Component>, Constructor<? extends Component>> constructorMap;

	private static Random rand = new Random();

	private String name;
	private UUID id;
	private Map<TypeComponent, Component> components = new EnumMap<>(TypeComponent.class);

	private boolean destroy;

	public Entity() {
		id = UUID.randomUUID();
	}

	public Entity(String type, String subType, String name) {
		this();
		if (entitySystem == null) {
			initSystem();
		}
		Entity e;
		if (entitySystem.containsKey(type) && entitySystem.get(type).containsKey(subType)
				&& entitySystem.get(type).get(subType).containsKey(name)) {
			e = entitySystem.get(type).get(subType).get(name);
			for (Component c : e.components.values()) {
				try {
					addComponent(constructorMap.get(c.getClass()).newInstance(c));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					Logger.error(e1);
				}
			}
		} else {
			Logger.warn("No Entity found: " + type + ", " + subType + "," + name);
		}
		this.name = name;
	}

	// add components
	public void addComponent(Component c) {
		components.put(c.getType(), c);
		c.setEntity(this);
	}

	public void destroy() {
		setDestroy(true);
		for (Component component : components.values()) {
			component.destroy();
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(TypeComponent type) {
		return (T) this.components.get(type);
	}

	public Map<TypeComponent, Component> getComponents() {
		return components;
	}

	public void initSystem() {
		entitySystem = new HashMap<>();
		constructorMap = new HashMap<>();
		findFiles("res/Objects/Entities");
	}

	public boolean isDestroy() {
		return this.destroy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonArray readJSON(String path, String fileName) {
		String directory = path.substring(path.indexOf("Entities") + 9, path.length() - fileName.length() - 1);
		String type = fileName.substring(0, fileName.length() - 5);

		if (!entitySystem.containsKey(directory)) {
			entitySystem.put(directory, new HashMap<String, HashMap<String, Entity>>());
		}
		if (!entitySystem.get(directory).containsKey(type)) {
			entitySystem.get(directory).put(type, new HashMap<String, Entity>());
		}
		JsonReader in = null;
		try (FileInputStream fileInput = new FileInputStream(path)) {
			in = new JsonReader(new InputStreamReader(fileInput));

			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for (JsonElement entity : jsonObjects) {
				Entity e = new Entity();
				JsonObject charObj = entity.getAsJsonObject();
				String entityName = charObj.get("name").getAsString();
				JsonArray components = charObj.get("components").getAsJsonArray();
				for (JsonElement component : components) {
					JsonObject compObj = component.getAsJsonObject();
					String cClassString = "components." + compObj.get("class").getAsString();
					JsonObject comp = compObj.get("component").getAsJsonObject();
					Class<? extends Component> cClass = (Class<? extends Component>) Class.forName(cClassString);
					Component c = g.fromJson(comp, cClass);
					e.addComponent(c);
					if (!constructorMap.containsKey(cClass)) {
						Constructor<? extends Component> copyConst = cClass.getConstructor(cClass);
						constructorMap.put(c.getClass(), copyConst);
					}

				}
				entitySystem.get(directory).get(type).put(entityName, e);
			}

			return jsonObjects;
		} catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			Logger.error(e);
		}

		return null;
	}

	public void send(Message m) {
		for (Component c : components.values()) {
			c.receive(m);
		}
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public void update() {
		for (Component component : this.components.values()) {
			if (!isDestroy()) {
				component.update();
			}
		}
	}

	@Override
	public Map getSystem() {
		return entitySystem;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public boolean equals(Object e) {
		if (e instanceof Entity) {
			return id.compareTo(((Entity) e).getId()) == 0;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public void setID() {
		id = UUID.randomUUID();
	}
}
