package components;

import object.Entity;

public abstract class Component {
	
	public abstract TypeComponent getType();
	
	public abstract void update(Entity e);
	
	public abstract void destroy(Entity e);
	
	public abstract void receive(Message m, Entity e);

}
