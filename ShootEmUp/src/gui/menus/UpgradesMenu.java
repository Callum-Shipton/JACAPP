package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import gui.Counter;
import gui.CounterButton;
import main.ShootEmUp;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Art;
import display.Image;

public class UpgradesMenu extends PauseMenu {

	Counter coins;
	
    public UpgradesMenu(Image menuImage) {
        super(menuImage);
        ButtonList buttonList = new ButtonList(30, 30, Art.inventoryButton.getHeight()/2, 20);
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.INVENTORY_UPGRADE, Art.coin, 5, 1f));
        buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.POTIONS_UPGRADE, Art.coin, 5, 1f));
        menuItems.add(buttonList);
        coins = new Counter(30.0f, 103.0f, Art.coin, true, ((BaseInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins(), 1.0f);
    }
    
    public void update(){
    	super.update();
    	coins.update(((BaseInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins());
    }
    
    public void render(){
    	super.render();
    	coins.render(Art.stat);
    }
}
