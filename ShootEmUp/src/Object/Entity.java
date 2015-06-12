package Object;

import java.util.HashMap;

import Components.Component;
import Components.ComponentType;
import Components.Message;

public class Entity{
	
	private HashMap<ComponentType,Component> components;

	// Constructors

	public Entity() {
		components = new HashMap<ComponentType, Component>();
	}

	public void update() {
		for(Component component : components.values()){
			component.update(this);
		}
	}
	
	public void destroy(){
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
}
