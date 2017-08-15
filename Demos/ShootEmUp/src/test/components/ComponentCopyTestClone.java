package test.components;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import components.Component;
import entity.Entity;

public class ComponentCopyTestClone {
	public int numObjects = 1000000000; // 1 billion
	
	@Test
	public void run() throws CloneNotSupportedException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		//INITIALISATION
		Entity e = new Entity();
		Component gm = new GroundMovementTest(numObjects);
		e.addComponent(gm);
		
		//CREATION PER OBJECT
		for(int i = 0; i< numObjects;i++) {	
			for (Component c : e.getComponents().values()) {
				Entity d = new Entity();
				Component component = c.clone();
				d.addComponent(component);
				assertEquals(gm,component);
			}
		}
	}
}
