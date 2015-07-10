package entities;

import java.util.HashMap;

import components.Component;
import components.ComponentType;
import components.Message;
import components.attack.BaseAttack;
import components.collision.BaseCollision;
import components.control.BaseControl;
import components.graphical.BaseGraphics;
import components.inventory.BaseInventory;
import components.movement.BaseMovement;

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
	
	//Attack Getters & Setters
	
	public BaseAttack getAttack(){
		return (BaseAttack) components.get(ComponentType.ATTACK);
	}
	
	//Collision Getters & Setters
	
	public BaseCollision getCollision(){
		return (BaseCollision) components.get(ComponentType.COLLISION);
	}
	
	//Control Getters & Setters
	
	public BaseControl getControl(){
		return (BaseControl) components.get(ComponentType.CONTROL);
	}
	
	//Graphics Getters & Setters
	
	public BaseGraphics getGraphics(){
		return (BaseGraphics) components.get(ComponentType.GRAPHICS);
	}
	
	//Inventory Getters & Setters
	
	public BaseInventory getInventory(){
		return (BaseInventory) components.get(ComponentType.INVENTORY);
	}
	
	//Movement Getters & Setters
	
	public BaseMovement getMovement(){
		return (BaseMovement) components.get(ComponentType.MOVEMENT);
	}
	
	
}
