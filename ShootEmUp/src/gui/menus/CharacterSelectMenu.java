package gui.menus;

import input.Keyboard;
import gui.Button;
import gui.ButtonHandler;
import gui.ButtonType;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class CharacterSelectMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;

    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        addButton(new Button(ButtonType.WARRIOR, Art.warriorButton, (ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2), 128,24));
        addButton(new Button(ButtonType.ARCHER, Art.archerButton, (ShootEmUp.width / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.archerButton.getHeight(), 128,24));
        addButton(new Button(ButtonType.MAGE, Art.mageButton, (ShootEmUp.width / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2), 128,24));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.backButton.getHeight(), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		ButtonHandler.selectButton(ButtonType.WARRIOR);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		ButtonHandler.selectButton(ButtonType.ARCHER);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 2){
    		ButtonHandler.selectButton(ButtonType.MAGE);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 3){
    		ButtonHandler.selectButton(ButtonType.BACK);
    	}
    	
        else if (Keyboard.getKey(GLFW.GLFW_KEY_DOWN) == 1) {
            selectedItem++;
            if (selectedItem > 3) {
                selectedItem = 0;
            }
        }
        else if (Keyboard.getKey(GLFW.GLFW_KEY_UP) == 1) {
            selectedItem--;
            if (selectedItem < 0) {
                selectedItem = 3;
            }
        }
    }
}
