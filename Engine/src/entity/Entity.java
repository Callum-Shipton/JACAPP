package entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.Component;
import components.Message;
import components.TypeComponent;
import logging.Logger;

public class Entity implements DatableObject<Entity> {

	private static Map<String, HashMap<String, HashMap<String, Entity>>> entitySystem;
	private static Random rand = new Random();

	private String name;
	private transient UUID id;
	private transient Map<TypeComponent, Component> components;

	private transient boolean destroy;

	public Entity() {
		id = UUID.randomUUID();
		this.components = new HashMap<TypeComponent, Component>();
	}

	public Entity(String name) {
		this();
		if (entitySystem == null) {
			initSystem();
		}
		Entity e;
		if (entitySystem.get("Characters").containsKey(name)) {
			int temp = rand.nextInt(entitySystem.get("Characters").get(name).size());
			Entity[] typedEntities = new Entity[entitySystem.get("Characters").get(name).size()];
			typedEntities = entitySystem.get(name).values().toArray(typedEntities);
			e = typedEntities[temp];
		} else {
			HashMap<String, Entity> tempEntities = new HashMap<String, Entity>();
			for (HashMap<String, Entity> typedEntities : entitySystem.get("Characters").values()) {
				tempEntities.putAll(typedEntities);
			}
			e = tempEntities.get(name);
		}

		this.name = e.name;
	}

	// add components
	public void addComponent(Component c) {
		this.components.put(c.getType(), c);
	}

	public void destroy() {
		setDestroy(true);
		for (Component component : this.components.values()) {
			component.destroy(this);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(TypeComponent type) {
		return (T) this.components.get(type);
	}

	public Map<TypeComponent, Component> getComponents() {
		return this.components;
	}

	public void initSystem() {
		entitySystem = new HashMap<>();
		findFiles("res/Objects/Entities");
	}

	public boolean isDestroy() {
		return this.destroy;
	}

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
			for (JsonElement e : jsonObjects) {
				String name = e.getAsJsonObject().get("name").getAsString();
				Entity entity = g.fromJson(e, Entity.class);
				entitySystem.get(directory).get(type).put(name, entity);
			}

			return jsonObjects;
		} catch (IOException e) {
			Logger.error(e);
		}

		return null;
	}

	public void send(Message m) {
		for (Component c : components.values()) {
			c.receive(m, this);
		}
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public void update() {
		for (Component component : this.components.values()) {
			if (!isDestroy()) {
				component.update(this);
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
}
