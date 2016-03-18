package gui.menus;

import input.Keyboard;
import gui.Button;
import gui.ButtonHandler;
import gui.ButtonType;
import main.ShootEmUp;

import org.lwjgl.glfw.GLFW;

import display.Art;
import display.Image;

public class GameOverMenu extends GuiMenu{

	static int selectedItem = 0;
	public static boolean saved;

    public GameOverMenu(Image menuImage) {
        super(menuImage);
        ShootEmUp.mainMenu = true;
        selectedItem = 0;
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, (ShootEmUp.width / 2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.exitButton.getHeight() / 2), 128,24));
    }

    @Override
    public void render() {
        super.render();
    }

    public void update() {
    	super.update();
    	
    	if(Keyboard.getKey(GLFW.GLFW_KEY_ENTER) == 1 && selectedItem == 0){
    		ButtonHandler.selectButton(ButtonType.MAIN_MENU);
        	Keyboard.setKey(GLFW.GLFW_KEY_ENTER);
    	}
    }
}
