package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonList;
import gui.TypeButton;
import main.ShootEmUp;

public abstract class PauseMenu extends GuiMenu {
	
	ButtonList tabs;
	ButtonList nativeButtons;
	
	public PauseMenu(Image MenuImage){
		super(MenuImage);
		
		tabs = new ButtonList(922, 0, Art.skillsButton.getHeight()/2, 0);
        tabs.addButton(TypeButton.INVENTORY);
        tabs.addButton(TypeButton.SKILLS);
        tabs.addButton(TypeButton.UPGRADES);
        tabs.addButton(TypeButton.MAP);
        tabs.addButton(TypeButton.SAVE);
        
        
        nativeButtons = new ButtonList(30, ShootEmUp.height - 94, Art.exitButton.getHeight()/2, 20);
        nativeButtons.addButton(TypeButton.RESUME);
        nativeButtons.addButton(TypeButton.MAIN_MENU);
	}
	
	public void update(){
		super.update();
		tabs.update();
		nativeButtons.update();
	}
	
	public void render(){
		super.render();
		tabs.render();
		nativeButtons.render();
	}
}
