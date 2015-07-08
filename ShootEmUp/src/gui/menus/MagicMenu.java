package gui.menus;

import main.ShootEmUp;
import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class MagicMenu extends GuiMenu {
	
	public static boolean saved;

    public MagicMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.RESUME, Art.backButton, 30, 30, 128,24));
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, 30, 64, 128,24));
        addButton(new Button(ButtonType.INVENTORY, Art.invButton, 922, 0, 101, 102));
        addButton(new Button(ButtonType.SKILLS, Art.skillButton, 922, 204, 101, 102));
        addButton(new Button(ButtonType.MAP, Art.mapButton, 922, 306, 101, 102));
        addButton(new Button(ButtonType.SAVE, Art.saveButton, 922, 408, 101, 102));
        addButton(new Button(ButtonType.INVENTORY_UPGRADE, Art.inventoryButton,30, 98, 128, 24));
		addButton(new Button(ButtonType.POTIONS_UPGRADE, Art.potionsButton,30, 132, 128, 24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	
    }

    public void addMenu(GuiMenu menu) {
		ShootEmUp.menuStack.add(menu);
	}
}
