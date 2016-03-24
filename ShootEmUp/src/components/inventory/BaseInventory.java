package components.inventory;

import java.util.HashSet;

import object.Entity;
import components.Component;
import components.TypeComponent;
import components.Message;

public abstract class BaseInventory extends Component implements InventoryComponent {

	protected TypeComponent type = TypeComponent.INVENTORY;
	
	protected int level;
	protected int exp;
	protected int coins;
	public HashSet<Entity> equipped;
	
	public void destroy(Entity e){
		
	}
	
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

	public void spendCoins(int coins) {
		this.coins -= coins;
	}
	
	public void setType(TypeComponent type) {
		this.type = type;
	}

	@Override
	public abstract void update(Entity e);

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
	}

	@Override
	public TypeComponent getType() {
		return type;
	}
}
