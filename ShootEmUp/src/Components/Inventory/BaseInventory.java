package Components.Inventory;

import java.util.HashSet;

import Components.Component;
import Components.ComponentType;
import Components.Message;
import Object.Entity;

public abstract class BaseInventory extends Component implements InventoryComponent {

	protected ComponentType type = ComponentType.INVENTORY;
	
	protected int level;
	protected int exp;
	protected int coins;
	public HashSet<Entity> equiped;
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setType(ComponentType type) {
		this.type = type;
	}

	@Override
	public abstract void update(Entity e);

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
	}

	@Override
	public ComponentType getType() {
		return type;
	}
}
