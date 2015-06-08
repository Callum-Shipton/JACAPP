package Components;

import Object.Entity;

public abstract class Component {
	
	public abstract ComponentType getType();
	
	public abstract void update(Entity e);
	
	public abstract void receive(Message m, Entity e);

}
