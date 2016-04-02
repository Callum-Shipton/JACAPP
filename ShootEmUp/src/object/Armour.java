package object;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import components.inventory.TypePickup;

public class Armour extends InventoryItem {

	private static HashMap<String, HashMap<String,Armour>> armourSystem;
	private static Random rand = new Random();
	
	private String type;
	private String subType;
	private int defence;

	public Armour(String type) {
		if (armourSystem == null) {
			initArmour();
		}
		Armour a;
		if(armourSystem.containsKey(type)){
			int temp = rand.nextInt(armourSystem.get(type).size());
			Armour[] typedArmours = new Armour[temp];
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
		this.subType = a.subType;
		this.defence = a.defence;
		this.inventoryImage = a.inventoryImage;
		typePickup = TypePickup.ARMOUR;
	}

	private void initArmour() {
		if(g == null)g = (new GsonBuilder()).setPrettyPrinting().create();
		armourSystem = new HashMap<String, HashMap<String,Armour>>();
		JsonReader in = null;
		in = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/Items/Armour.JSON")));
		if (in != null) {
			JsonArray jsonObjects = new JsonParser().parse(in).getAsJsonArray();
			for(JsonElement e : jsonObjects){
				String type = e.getAsJsonObject().get("type").getAsString();
				String subType = e.getAsJsonObject().get("subType").getAsString();
				if(!armourSystem.containsKey(type)){
					armourSystem.put(type, new HashMap<String,Armour>());
				}
				armourSystem.get(type).put(subType,g.fromJson(e, Armour.class));
			}
		}
	}

	public int getDefence() {
		return defence;
	}

	public String getType() {
		return type;
	}
	
	public String getSubType() {
		return subType;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

}
