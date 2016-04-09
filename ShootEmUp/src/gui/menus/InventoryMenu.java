package gui.menus;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.inventory.BaseInventory;
import display.Art;
import display.Image;
import gui.Inventory;
import gui.ItemSlot;
import main.ShootEmUp;

public class InventoryMenu extends PauseMenu {

	private PlayerAttack playerAttack;

	private ItemSlot helmet;
	private ItemSlot chest;
	private ItemSlot legs;
	private ItemSlot boots;
	private ItemSlot weapon;

	private Inventory inventory;

	public InventoryMenu(Image menuImage) {
		super(menuImage);

		this.playerAttack = ShootEmUp.getPlayer().getComponent(TypeComponent.ATTACK);

		buildHelmet();
		buildChest();
		buildLegs();
		buildBoots();
		buildWeapon();

		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		this.inventory = new Inventory(30, 30, BI.getInventory());
	}

	private void buildBoots() {
		if (this.playerAttack.getBoots() != null) {
			this.boots = new ItemSlot(560, 126, this.playerAttack.getBoots());
		}
	}

	private void buildChest() {
		if (this.playerAttack.getChest() != null) {
			this.chest = new ItemSlot(560, 62, this.playerAttack.getChest());
		}
	}

	private void buildHelmet() {
		if (this.playerAttack.getHelmet() != null) {
			this.helmet = new ItemSlot(560, 30, this.playerAttack.getHelmet());
		}
	}

	private void buildLegs() {
		if (this.playerAttack.getLegs() != null) {
			this.legs = new ItemSlot(560, 94, this.playerAttack.getLegs());
		}
	}

	private void buildWeapon() {
		if (this.playerAttack.getWeapon() != null) {
			this.weapon = new ItemSlot(560, 158, this.playerAttack.getWeapon());
		}
	}

	@Override
	public void render() {
		super.render();
		if (this.boots != null) {
			this.boots.render(Art.stat);
		}
		if (this.legs != null) {
			this.legs.render(Art.stat);
		}
		if (this.chest != null) {
			this.chest.render(Art.stat);
		}
		if (this.helmet != null) {
			this.helmet.render(Art.stat);
		}
		if (this.weapon != null) {
			this.weapon.render(Art.stat);
		}

		this.inventory.render(Art.stat);
	}

	@Override
	public void update() {
		super.update();

		if (this.boots != null) {
			this.boots.updateImage(this.playerAttack.getBoots().getInventoryImage());
			this.boots.updateStats(new int[] { this.playerAttack.getBoots().getDefence() });
		} else {
			buildBoots();
		}
		if (this.legs != null) {
			this.legs.updateImage(this.playerAttack.getLegs().getInventoryImage());
			this.legs.updateStats(new int[] { this.playerAttack.getLegs().getDefence() });
		} else {
			buildLegs();
		}
		if (this.chest != null) {
			this.chest.updateImage(this.playerAttack.getChest().getInventoryImage());
			this.chest.updateStats(new int[] { this.playerAttack.getChest().getDefence() });
		} else {
			buildChest();
		}
		if (this.helmet != null) {
			this.helmet.updateImage(this.playerAttack.getHelmet().getInventoryImage());
			this.helmet.updateStats(new int[] { this.playerAttack.getHelmet().getDefence() });
		} else {
			buildHelmet();
		}
		if (this.weapon != null) {
			this.weapon.updateImage(this.playerAttack.getWeapon().getInventoryImage());
			int[] stats = new int[] { this.playerAttack.getWeapon().getDamage(),
					this.playerAttack.getWeapon().getRange(), this.playerAttack.getWeapon().getFireRate(),
					this.playerAttack.getWeapon().getManaCost() };
			this.weapon.updateStats(stats);
		} else {
			buildWeapon();
		}
		this.inventory.update();
	}
}
