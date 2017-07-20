package components;

import entity.Entity;

public abstract class Component {

	protected Entity entity;

	public abstract void destroy(Entity e);

	public abstract TypeComponent getType();

	public abstract void receive(Message m, Entity e);

	public abstract void update(Entity e);
	
	public Component clone() throws CloneNotSupportedException {
		return (Component) super.clone();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	};

}
