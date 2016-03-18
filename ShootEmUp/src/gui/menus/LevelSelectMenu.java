package gui.menus;

import main.ShootEmUp;
import input.Keyboard;
import gui.Button;
import gui.ButtonHandler;
import gui.ButtonType;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class LevelSelectMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;

    public LevelSelectMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        addButton(new Button(ButtonType.LEVEL1, Art.level1Button, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - Art.level1Button.getHeight(), 128,24));
        addButton(new Button(ButtonType.LEVEL2, Art.level2Button, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) - (Art.level2Button.getHeight() / 4), 128,24));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.level1Button.getWidth() / 2), (ShootEmUp.height / 2) + Art.level1Button.getHeight(), 128,24));
    }

    @Override
    public void render() {
        super.render();
        
    }

    public void update() {
    	super.update();
    	
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		ButtonHandler.selectButton(ButtonType.LEVEL1);
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		ButtonHandler.selectButton(ButtonType.LEVEL2);
    		Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	} else if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 1){
    		ButtonHandler.selectButton(ButtonType.BACK);
    		Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
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
