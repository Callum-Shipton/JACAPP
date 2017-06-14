package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import components.inventory.TypePickup;
import main.Logger;

public final class Armour extends InventoryItem<Armour> {

	private static final long serialVersionUID = 6126061373353659997L;

	private static Map<String, Map<String, Armour>> armourSystem = new HashMap<>();

	protected static String directoryPath = "res/Objects/Items/Armour";

	private int defence;

	public Armour(String typeName) {
		if (armourSystem.isEmpty()) {
			initSystem(Armour.class);
		}
		Armour a = null;
		if (armourSystem.containsKey(typeName)) {
			int temp = rand.nextInt(armourSystem.get(typeName).size());
			Armour[] typedArmours = new Armour[armourSystem.get(typeName).size()];
			typedArmours = armourSystem.get(typeName).values().toArray(typedArmours);
			a = typedArmours[temp];
		} else {
			for (Entry<String, Map<String, Armour>> typedWeapons : armourSystem.entrySet()) {
				if (typedWeapons.getValue().containsKey(typeName)){
					a = typedWeapons.getValue().get(typeName);
					this.type = typedWeapons.getKey();
				}
			}
		}

		if(a == null){
			// Pick a random armour
			Logger.warn("An armour was created with unknown name/type: " + typeName);
		}
		this.name = a.name;
		this.defence = a.defence;
		this.typePickup = TypePickup.ARMOUR;
	}

	public int getDefence() {
		return this.defence;
	}

	public String getType() {
		return this.type;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	@Override
	public Map<String, Map<String, Armour>> getSystem() {
		return armourSystem;
	}

	@Override
	protected String getDirectoryPath() {
		return directoryPath;
	}

}
