package Components.Attack;

import Components.Inventory.PlayerInventory;

public abstract class PlayerAttack extends BaseAttack {
	PlayerInventory PI;
	
	protected int mana;
	protected int manaRegen;
	protected int maxMana;
	protected int maxManaRegen;
	
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getManaRegen() {
		return manaRegen;
	}

	public void setManaRegen(int manaRegen) {
		this.manaRegen = manaRegen;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getMaxManaRegen() {
		return maxManaRegen;
	}

	public void setMaxManaRegen(int maxManaRegen) {
		this.maxManaRegen = maxManaRegen;
	}
}
