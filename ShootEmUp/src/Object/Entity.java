package Object;

import java.util.HashMap;
import java.util.HashSet;

import Components.Component;
import Components.ComponentType;
import Components.Message;
import Display.DPDTRenderer;
import Display.Image;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;

public class Entity{

	private boolean destroy = false;
	
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
	
	public boolean getDestroy(){
		return destroy;
	}
}
