package object;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.inventory.TypePickup;

public class Armour extends InventoryItem {

	private static HashMap<String, HashMap<String,Armour>> armourSystem;
	private static Random rand = new Random();
	
	private transient String type;
	private int defence;

	public Armour(String type) {
		if (armourSystem == null) {
			initSystem();
		}
		Armour a;
		if(armourSystem.containsKey(type)){
			int temp = rand.nextInt(armourSystem.get(type).size());
			Armour[] typedArmours = new Armour[armourSystem.get(type).size()];
			typedArmours = armourSystem.get(type).values().toArray(typedArmours);
			a = typedArmours[temp];
		}else{
			HashMap<String, Armour> tempArmours = new HashMap<String,Armour>();
			for(HashMap<String,Armour> typedArmours : armourSystem.values()){
				tempArmours.putAll(typedArmours);
			}
			a = tempArmours.get(type);
		}
		this.type = type;
		this.name = a.name;
		this.defence = a.defence;
		typePickup = TypePickup.ARMOUR;
	}

	public void initSystem() {
		armourSystem = new HashMap<String, HashMap<String,Armour>>();
		findFiles("res\\Objects\\Items\\Armour");
	}

	@Override
	public void readJSON(String path, String fileName) {
		JsonReader in = null;
		try {
			in = new JsonReader(new InputStreamReader(new FileInputStream(path)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (in != null) {
			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for(JsonElement e : jsonObjects){
				String type = fileName.substring(0, fileName.length()-5);
				String name = e.getAsJsonObject().get("name").getAsString();
				if(!armourSystem.containsKey(type)){
					armourSystem.put(type, new HashMap<String,Armour>());
				}
				Armour a = g.fromJson(e, Armour.class);
				a.type = type;
				armourSystem.get(type).put(name,a);
			}
		}
	}
	
	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public String getType() {
		return type;
	}
}
