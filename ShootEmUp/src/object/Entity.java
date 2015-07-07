package object;

import java.util.HashMap;

import components.Component;
import components.ComponentType;
import components.Message;

public class Entity{
	
	private HashMap<ComponentType,Component> components;

	private boolean destroy;
	// Constructors

	public Entity() {
		components = new HashMap<ComponentType, Component>();
	}

	public void update() {
		for(Component component : components.values()){
			if(!isDestroy())component.update(this);
		}
	}
	
	public void destroy(){
		setDestroy(true);
		for(Component component : components.values()){
			component.destroy(this);
		}
	}

	//add components
	public void addComponent(Component c){
		components.put(c.getType(), c);
	}
	
	public Component getComponent(ComponentType type){
		return components.get(type);
	}
	
	public HashMap<ComponentType, Component> getComponents(){
		return components;
	}
	
	public void send(Message m){
		for(Component c : components.values()){
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