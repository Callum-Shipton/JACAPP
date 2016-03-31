package object;

import java.util.HashMap;

import components.Component;
import components.Message;
import components.TypeComponent;

public class Entity {

	private HashMap<TypeComponent, Component> components;

	private boolean destroy;
	// Constructors

	public Entity() {
		components = new HashMap<TypeComponent, Component>();
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
