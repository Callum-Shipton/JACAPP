package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import gui.Counter;
import main.ShootEmUp;

import components.TypeComponent;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class UpgradesMenu extends PauseMenu {

	Counter coins;
	Counter inventoryPrice;
	Counter potionsPrice;
	
    public UpgradesMenu(Image menuImage) {
        super(menuImage);
        ButtonList buttonList = new ButtonList(30, 30, Art.inventoryButton.getHeight()/2, 20);
        buttonList.addButton(TypeButton.INVENTORY_UPGRADE);
        buttonList.addButton(TypeButton.POTIONS_UPGRADE);
        menuItems.add(buttonList);
		coins = new Counter(30.0f, 98.0f, Art.coin, true, ((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins(), 1.0f);
		inventoryPrice = new Counter(160, 35, Art.numbers, false, 5, 0.5f);
		potionsPrice = new Counter(160, 69, Art.numbers, false, 5, 0.5f);
    }
    
    public void render(){
    	super.render();
    	coins.render(Art.stat);
    	inventoryPrice.render(Art.stat);
    	potionsPrice.render(Art.stat);
    }
}
