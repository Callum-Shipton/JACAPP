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

public abstract class Entity{
	private float posX;
	private float posY;
	private float width;
	private float height;
	public boolean destroy = false;
	
	public HashMap<ComponentType,Component> components;

	// Constructors

	public Entity() {
		components = new HashMap<ComponentType, Component>();
	}

	public void update() {
		for(Component component : components.values()){
			component.update();
		}
	}

	// Methods

	public void render(DPDTRenderer r) {
		r.draw(image, new Vector2(getPosX(), getPosY()), new Vector2(getWidth(), getHeight()),
				0.0f, new Vector2((float) Math.floor(animID / animTime),
						(float) getDirection()), new Vector2(image.getFWidth(),
						image.getFHeight()));
	}

	//add components
	public void addComponent(Component c){
		components.put(c.getType(), c);
	}
	
	public Component getComponent(ComponentType type){
		return components.get(type);
	}
	
	// Setters and getters

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public void send(Message m){
		for(Component c : components.values()){
			c.receive(m, this);
		}
	}
}
