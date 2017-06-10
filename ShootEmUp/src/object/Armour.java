package object;

import java.util.HashMap;
import java.util.Map;

import components.inventory.TypePickup;

public final class Armour extends InventoryItem<Armour> {

	private static final long serialVersionUID = 6126061373353659997L;

	private static Map<String, Map<String, Armour>> armourSystem = new HashMap<>();

	protected static String directoryPath = "res/Objects/Items/Armour";

	private int defence;

	public Armour(String type) {
		if (armourSystem.isEmpty()) {
			initSystem(Armour.class);
		}
		Armour a;
		if (armourSystem.containsKey(type)) {
			int temp = rand.nextInt(armourSystem.get(type).size());
			Armour[] typedArmours = new Armour[armourSystem.get(type).size()];
			typedArmours = armourSystem.get(type).values().toArray(typedArmours);
			a = typedArmours[temp];
		} else {
			Map<String, Armour> tempArmours = new HashMap<>();
			for (Map<String, Armour> typedArmours : armourSystem.values()) {
				tempArmours.putAll(typedArmours);
			}
			a = tempArmours.get(type);
		}
		this.type = type;
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
