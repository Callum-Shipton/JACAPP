package test.components;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.Component;
import components.TypeComponent;
import entity.Entity;
import logging.Logger;

public class ComponentCopyConstructorTest {

	static Gson g = (new GsonBuilder()).setPrettyPrinting().create();
	
	public int numObjects = 1000000000; // 1 billion
	public String path = "res/Objects/Entities/Characters/Players.JSON";
	
	@SuppressWarnings("unchecked")
	@Test
	public void run() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException{
		
		Map<String, HashMap<String, HashMap<String, Entity>>> entitySystem = new HashMap<>();
		
		HashMap<TypeComponent, Constructor<? extends Component>> constructorMap = new HashMap<>();
		
		entitySystem.put("Characters", new HashMap<>());
		entitySystem.get("Characters").put("Players", new HashMap<>());
		
		JsonReader in = null;
		try (FileInputStream fileInput = new FileInputStream(path)) {
			in = new JsonReader(new InputStreamReader(fileInput));

			JsonArray a = new JsonParser().parse(in).getAsJsonArray();
			for (JsonElement character : a) {
				Entity e = new Entity();
				JsonObject charObj = character.getAsJsonObject();
				String charName = charObj.get("name").getAsString();
				JsonArray components = charObj.get("components").getAsJsonArray();
				for(JsonElement component: components) {
					JsonObject compObj = component.getAsJsonObject();
					String cClassString = "test.components." + compObj.get("class").getAsString();
					JsonObject comp = compObj.get("component").getAsJsonObject();
					Class<? extends Component> cClass = (Class<? extends Component>) Class.forName(cClassString);
					Component c =  g.fromJson(comp, cClass);
					Constructor<? extends Component> copyConst = cClass.getConstructor(cClass);
					constructorMap.put(c.getType(), copyConst);
					e.addComponent(c);
				}
				entitySystem.get("Characters").get("Players").put(charName, e);
			}
		} catch (IOException e) {
			Logger.error(e);
		}
		
		//CREATION PER OBJECT
		Entity j = entitySystem.get("Characters").get("Players").get("Jack");
		Entity c = entitySystem.get("Characters").get("Players").get("Callum");
		
		for(int i = 0; i< numObjects;i++) {
			
			for (Component c1 : j.getComponents().values()) {
				Component component = constructorMap.get(c1.getType()).newInstance(c1);
				assertEquals(c1,component);
				assertNotEquals(component,c.getComponent(c1.getType()));
			}
			for (Component c1 : c.getComponents().values()) {
				Component component = constructorMap.get(c1.getType()).newInstance(c1);
				assertEquals(c1,component);
				assertNotEquals(component,j.getComponent(c1.getType()));
			}
		}
	}
}
