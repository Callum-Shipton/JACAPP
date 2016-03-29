package gui.menus;

import gui.ButtonList;
import gui.TypeButton;
import main.ShootEmUp;
import display.Art;
import display.Image;

public class OptionsMenu extends GuiMenu{
	
	ButtonList buttonList;
	
    public OptionsMenu(Image menuImage) {
        super(menuImage);
        
        buttonList = new ButtonList((ShootEmUp.width / 2) - (Art.controlsButton.getWidth() / 2), 150, Art.controlsButton.getHeight()/2, 20);
        
        buttonList.addButton(TypeButton.CONTROLS);
        buttonList.addButton(TypeButton.SOUND);
        buttonList.addButton(TypeButton.BACK);
    }
    
    public void update(){
    	super.update();
    	buttonList.render();
    }
    
    public void render(){
    	super.render();
    	buttonList.render();
    }
}
