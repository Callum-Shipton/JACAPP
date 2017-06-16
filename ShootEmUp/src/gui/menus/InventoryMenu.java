package gui.menus;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.inventory.BaseInventory;
import display.ImageProcessor;
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
		if (playerAttack.getBoots() != null) {
			boots = new ItemSlot(560, 126, playerAttack.getBoots());
		}
	}

	private void buildChest() {
		if (playerAttack.getChest() != null) {
			chest = new ItemSlot(560, 62, playerAttack.getChest());
		}
	}

	private void buildHelmet() {
		if (playerAttack.getHelmet() != null) {
			helmet = new ItemSlot(560, 30, playerAttack.getHelmet());
		}
	}

	private void buildLegs() {
		if (playerAttack.getLegs() != null) {
			legs = new ItemSlot(560, 94, playerAttack.getLegs());
		}
	}

	private void buildWeapon() {
		if (playerAttack.getWeapon() != null) {
			weapon = new ItemSlot(560, 158, playerAttack.getWeapon());
		}
	}

	@Override
	public void render() {
		super.render();
		if (boots != null) {
			boots.render(ImageProcessor.stat);
		}
		if (legs != null) {
			legs.render(ImageProcessor.stat);
		}
		if (chest != null) {
			chest.render(ImageProcessor.stat);
		}
		if (helmet != null) {
			helmet.render(ImageProcessor.stat);
		}
		if (weapon != null) {
			weapon.render(ImageProcessor.stat);
		}

		inventory.render(ImageProcessor.stat);
	}

	@Override
	public void update() {
		super.update();

		if (boots != null) {
			boots.updateImage(playerAttack.getBoots().getInventoryImage());
			boots.updateStats(new int[] { playerAttack.getBoots().getDefence() });
		} else {
			buildBoots();
		}
		if (legs != null) {
			legs.updateImage(playerAttack.getLegs().getInventoryImage());
			legs.updateStats(new int[] { playerAttack.getLegs().getDefence() });
		} else {
			buildLegs();
		}
		if (chest != null) {
			chest.updateImage(playerAttack.getChest().getInventoryImage());
			chest.updateStats(new int[] { playerAttack.getChest().getDefence() });
		} else {
			buildChest();
		}
		if (helmet != null) {
			helmet.updateImage(playerAttack.getHelmet().getInventoryImage());
			helmet.updateStats(new int[] { playerAttack.getHelmet().getDefence() });
		} else {
			buildHelmet();
		}
		if (weapon != null) {
			weapon.updateImage(playerAttack.getWeapon().getInventoryImage());
			int[] stats = new int[] { playerAttack.getWeapon().getDamage(), playerAttack.getWeapon().getRange(),
					playerAttack.getWeapon().getFireRate(), playerAttack.getWeapon().getManaCost() };
			weapon.updateStats(stats);
		} else {
			buildWeapon();
		}
		inventory.update();
	}
}
