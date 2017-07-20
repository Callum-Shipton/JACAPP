package components;

import entity.Entity;

public abstract class Component {

	protected Entity entity;

	public abstract void destroy();

	public abstract TypeComponent getType();

	public abstract void receive(Message m);

	public abstract void update();

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
