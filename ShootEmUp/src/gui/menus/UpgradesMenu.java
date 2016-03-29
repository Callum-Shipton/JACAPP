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
	
    public UpgradesMenu(Image menuImage) {
        super(menuImage);
        ButtonList buttonList = new ButtonList(30, 30, Art.inventoryButton.getHeight()/2, 20);
        buttonList.addButton(TypeButton.INVENTORY_UPGRADE);
        buttonList.addButton(TypeButton.POTIONS_UPGRADE);
        menuItems.add(buttonList);
        coins = new Counter(30.0f, 98.0f, Art.coin, true, ((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins(), 1.0f);
        menuItems.add(new Counter(160, 35, Art.coin, false, 5, 1.0f));
        menuItems.add(new Counter(160, 69, Art.coin, false, 5, 1.0f));
    }
    
    public void render(){
    	super.render();
    	coins.render(Art.stat);
    }
}
