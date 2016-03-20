package gui.menus;

import gui.Button;
import gui.ButtonType;

import java.util.ArrayList;
import java.util.Iterator;

import object.InventoryItem;
import main.ShootEmUp;
import components.ComponentType;
import components.inventory.PlayerInventory;
import display.Image;

public class InventoryMenu extends PauseMenu {
	
	private ArrayList<Button> itemButtons;
	
	private int row = 0;
	private int column = 0;
	
	private int x;
	private int y;

    public InventoryMenu(Image menuImage) {
        super(menuImage);
        itemButtons = new ArrayList<Button>();
        /*
        boots = addButton(new Button(null, 30, 30, 128,24));
        legs = addButton(new Button(null, 30, 30, 128,24));
        chest = addButton(new Button(null, 30, 30, 128,24));
        helmet = addButton(new Button(null, 30, 30, 128,24));
        */
        
        x = 20;
        y = 100;
        
        Iterator<InventoryItem> items = ((PlayerInventory) (ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).getInventory().iterator();
		while(items.hasNext()){
			InventoryItem item = items.next();
			itemButtons.add(addButton(new Button(ButtonType.OTHER, item.getInventoryImage(), x + (item.getInventoryImage().getWidth() * row), y + (item.getInventoryImage().getHeight() * column))));
			row++;
			if(row > 10){
				row = 0;
				column++;
			}
		}
    }

    public void update() {
    	super.update();
    	Iterator<Button> Buttons = itemButtons.iterator();
    	Button itemButton;
    	int position = 0;
		while(Buttons.hasNext()){
			itemButton = Buttons.next();
			if(itemButton.hasClicked()){
				((PlayerInventory)(ShootEmUp.currentLevel.getPlayer().getComponent(ComponentType.INVENTORY))).equipItem(position);
				itemButton.postAction();
				removeButton(itemButton);
				Buttons.remove();
				
			}

			position++;
		}
    }
}
