package object;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.Component;
import components.Message;
import components.TypeComponent;

public class Entity implements DatableObject {

	private static HashMap<String, HashMap<String, HashMap<String, Entity>>> entitySystem;
	private static Random rand = new Random();

	private String name;
	private transient HashMap<TypeComponent, Component> components;

	private transient boolean destroy;

	public Entity() {
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

	public HashMap<TypeComponent, Component> getComponents() {
		return this.components;
	}

	@Override
	public void initSystem() {
		entitySystem = new HashMap<String, HashMap<String, HashMap<String, Entity>>>();
		findFiles("res/Objects/Entities");
	}

	public boolean isDestroy() {
		return this.destroy;
	}

	@Override
	public void readJSON(String path, String fileName) {
		String directory = path.substring(path.indexOf("Entities") + 9, path.length() - fileName.length() - 1);
		String type = fileName.substring(0, fileName.length() - 5);

		if (!entitySystem.containsKey(directory)) {
			entitySystem.put(directory, new HashMap<String, HashMap<String, Entity>>());
		}
		if (!entitySystem.get(directory).containsKey(type)) {
			entitySystem.get(directory).put(type, new HashMap<String, Entity>());
		}
		JsonReader in = null;
		try {
			in = new JsonReader(new InputStreamReader(new FileInputStream(path)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (in != null) {
			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for (JsonElement e : jsonObjects) {
				String name = e.getAsJsonObject().get("name").getAsString();
				Entity entity = g.fromJson(e, Entity.class);
				entitySystem.get(directory).get(type).put(name, entity);
			}
		}
	}

	public void send(Message m) {
		for (Component c : this.components.values()) {
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
}
