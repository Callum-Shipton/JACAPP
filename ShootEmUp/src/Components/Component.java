package Components;

import Object.Entity;

public abstract class Component {
	
	private static final ComponentType type = ComponentType.COMPONENT;
	
	public ComponentType getType() {
		return type;
	}
	
	public abstract void receive(Message m, Entity e);

}
