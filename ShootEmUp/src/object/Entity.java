package object;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.Component;
import components.Message;
import components.TypeComponent;

public class Entity implements DatableObject{

	private HashMap<String, HashMap<String, HashMap<String, Entity>>> entitySystem;
	private HashMap<TypeComponent, Component> components;

	private boolean destroy;
	// Constructors

	public Entity() {
		/*
		if(entitySystem == null){
			initSystem();
		}
		*/
		components = new HashMap<TypeComponent, Component>();
	}

	@Override
	public void initSystem() {
		entitySystem = new HashMap<String, HashMap<String, HashMap<String, Entity>>>();
		findFiles("res\\Objects\\Entities");
	}

	@Override
	public void readJSON(String path, String fileName) {
		/*JsonReader in = null;
		try {
			in = new JsonReader(new InputStreamReader(new FileInputStream(path)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (in != null) {
			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for(JsonElement e : jsonObjects){
				String type = fileName.substring(0, fileName.length()-5);
				String name = e.getAsJsonObject().get("name").getAsString();
				if(!entitySystem.containsKey(type)){
					entitySystem.put(type, new HashMap<String,Weapon>());
				}
				Entity entity = g.fromJson(e, Entity.class);
				entitySystem.get(type).put(name,entity);
			}
		}*/
	}
	
	public void update() {
		for (Component component : components.values()) {
			if (!isDestroy()) {
				component.update(this);
			}
		}
	}

	public void destroy() {
		setDestroy(true);
		for (Component component : components.values()) {
			component.destroy(this);
		}
	}

	// add components
	public void addComponent(Component c) {
		components.put(c.getType(), c);
	}

	public Component getComponent(TypeComponent type) {
		return components.get(type);
	}

	public HashMap<TypeComponent, Component> getComponents() {
		return components;
	}

	public void send(Message m) {
		for (Component c : components.values()) {
			c.receive(m, this);
		}
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}
}
