package GUI.Menus;

import java.util.ArrayList;
import java.util.Iterator;

import Components.ComponentType;
import Components.Inventory.PlayerInventory;
import Display.Art;
import Display.Image;
import GUI.Button;
import Main.ShootEmUp;
import Object.InventoryItem;

public class InventoryMenu extends GuiMenu {
	
	public static boolean saved;
	private Button back;
	private Button exit;
	private Button skillButton;
	private Button magicButton;
	private Button mapButton;
	private Button saveButton;
	
	/*
	private Button boots;
	private Button legs;
	private Button chest;
	private Button helmet;
	*/
	
	private ArrayList<Button> itemButtons;
	
	private int row = 0;
	private int column = 0;
	
	private int x;
	private int y;

    public InventoryMenu(Image menuImage) {
        super(menuImage);
        back = addButton(new Button(Art.backButton, 30, 30, 128,24));
        exit = addButton(new Button(Art.exitButton, 30, 64, 128,24));
        magicButton = addButton(new Button(Art.magicButton, 922, 102, 101, 102));
        skillButton = addButton(new Button(Art.skillButton, 922, 204, 101, 102));
        mapButton = addButton(new Button(Art.mapButton, 922, 306, 101, 102));
        saveButton = addButton(new Button(Art.saveButton, 922, 408, 101, 102));
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
			itemButtons.add(addButton(new Button(item.getInventoryImage(), x + (item.getInventoryImage().getWidth() * row), y + (item.getInventoryImage().getHeight() * column), item.getInventoryImage().getWidth(), item.getInventoryImage().getHeight() / 2)));
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
    	if(magicButton.hasClicked()){
    	    addMenu(new MagicMenu(Art.magicScreen));
    	    magicButton.postAction();
    	}
    	if(skillButton.hasClicked()){
    	    addMenu(new SkillMenu(Art.skillScreen));
    	    skillButton.postAction();
    	}
    	if(mapButton.hasClicked()){
    	    addMenu(new MapMenu(Art.mapScreen));
    	    mapButton.postAction();
    	}
    	if(saveButton.hasClicked()){
    	    addMenu(new SaveMenu(Art.saveScreen));
    	    saveButton.postAction();
    	}
    	if(back.hasClicked()){
    		ShootEmUp.menuStack.clear();
        	ShootEmUp.paused = false;
        	back.postAction();
    	}
    	if(exit.hasClicked()){
    		ShootEmUp.menuStack.clear();
    		addMenu(new MainMenu(Art.mainMenuScreen));
        	exit.postAction();
    	}
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}
