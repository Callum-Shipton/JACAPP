package test.components;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import components.Component;
import components.TypeComponent;
import entity.Entity;

public class ComponentCopyConstructorTest {

	public int numObjects = 1000000000; // 1 billion
	
	@Test
	public void run() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		//INITIALISATION
		Entity e = new Entity();
		Component gm = new GroundMovementTest(null, numObjects);
		e.addComponent(gm);
		
		HashMap<TypeComponent, Constructor<? extends Component>> constructorMap = new HashMap<>();
		for (Component c : e.getComponents().values()) {
			Class<? extends Component> cl = c.getClass();
			Constructor<? extends Component> cons = cl.getConstructor(cl);
			constructorMap.put(c.getType(), cons);
		}
		
		//CREATION PER OBJECT
		for(int i = 0; i< numObjects;i++) {	
			for (Component c : e.getComponents().values()) {
				Component component = constructorMap.get(c.getType()).newInstance(c);
				assertEquals(gm,component);
			}
		}
	}
}
