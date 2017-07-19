package components;

import entity.Entity;

public abstract class Component {

	protected Entity entity;

	public Component(Entity entity) {
		this.entity = entity;
	}

	public abstract void destroy(Entity e);

	public abstract TypeComponent getType();

	public abstract void receive(Message m, Entity e);

	public abstract void update(Entity e);

}
