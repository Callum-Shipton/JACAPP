package gui.menus;

import gui.Button;
import gui.ButtonType;

import java.util.ArrayList;
import java.util.Iterator;

import object.InventoryItem;
import main.ShootEmUp;
import components.ComponentType;
import components.inventory.PlayerInventory;
import display.Art;
import display.Image;

public class InventoryMenu extends GuiMenu {
	
	public static boolean saved;

	private ArrayList<Button> itemButtons;
	
	private int row = 0;
	private int column = 0;
	
	private int x;
	private int y;

    public InventoryMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.RESUME, Art.backButton, 30, 30, 128,24));
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, 30, 64, 128,24));
        addButton(new Button(ButtonType.MAGIC, Art.magicButton, 922, 102, 101, 102));
        addButton(new Button(ButtonType.SKILLS, Art.skillButton, 922, 204, 101, 102));
        addButton(new Button(ButtonType.MAP, Art.mapButton, 922, 306, 101, 102));
        addButton(new Button(ButtonType.SAVE, Art.saveButton, 922, 408, 101, 102));
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
			itemButtons.add(addButton(new Button(ButtonType.OTHER, item.getInventoryImage(), x + (item.getInventoryImage().getWidth() * row), y + (item.getInventoryImage().getHeight() * column), item.getInventoryImage().getWidth(), item.getInventoryImage().getHeight() / 2)));
			row++;
			if(row > 10){
				row = 0;
				column++;
			}
		}
    }

    @Override
    public void render() {
        super.render();
        
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

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}
