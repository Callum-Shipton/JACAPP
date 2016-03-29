package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class MainMenu extends GuiMenu {
	
	ButtonList buttonList;
	
    public MainMenu(Image menuImage) {
        super(menuImage);
        buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.newGameButton.getWidth() / 2), 150, Art.newGameButton.getHeight()/2, 20);
        ShootEmUp.currentLevel = null;
        buttonList.addButton(TypeButton.NEW_GAME);
        buttonList.addButton(TypeButton.LOAD_GAME);
        buttonList.addButton(TypeButton.OPTIONS);
        buttonList.addButton(TypeButton.EXIT);
    }
    
    public void update(){
    	super.update();
    	buttonList.update();
    }
    
    public void render(){
    	super.render();
    	buttonList.render();
    }
}
